package com.lezhin.history.infrastructure.dao;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.domain.AdultOnly;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import com.lezhin.history.domain.service.dto.SearchHistoryDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HistoryCustomRepositoryImpl implements HistoryCustomRepository {

    private final R2dbcEntityTemplate template;
    private final SearchHistoryDTOMapper searchHistoryDTOMapper;

    /**
     * 사용자 조회 이력 페이지 조회
     * @param command: 조회할 정보
     * @return SearchHistoryPage: 이력 페이지
     */
    @Override
    public Mono<HistoryDTO.SearchHistoryPage> getSearchHistoryPage(HistoryCommand.SearchHistoryPage command) {

        int offset = command.getPageForPageable() * command.getSize();
        int limit = command.getSize();
        PageRequest pageRequest = PageRequest.of(command.getPageForPageable(), limit);

        String from = " FROM `history`";
        String groupBy = " GROUP BY memberId, memberType, memberEmail, memberName, memberGender";

        return template.getDatabaseClient()
            .sql(
                "SELECT memberId, memberType, memberEmail, memberName, memberGender" +
                    from +
                    whereSearchHistoryPage(command.getAdultOnly(), command.getWeekInterval()) +
                    groupBy +
                    havingSearchHistoryPage(command.getHistoryCount()) +
                    " LIMIT " + limit +
                    " OFFSET " + offset
            )
            .fetch()
            .all()
            .flatMap(history -> Mono.just(searchHistoryDTOMapper.of(history)))
            .collectList()
            .zipWith(template.getDatabaseClient()
                .sql(
                    "SELECT COUNT(memberId) AS size" +
                    from +
                    whereSearchHistoryPage(command.getAdultOnly(), command.getWeekInterval()) +
                    groupBy +
                    havingSearchHistoryPage(command.getHistoryCount())
                )
                .fetch()
                .first()
            )
            .flatMap(objects -> {
                long totalCount = Long.parseLong(String.valueOf(objects.getT2().get("size")));
                Page<HistoryDTO.SearchHistoryMemberInfo> historyPage = new PageImpl<>(objects.getT1(), pageRequest, totalCount);

                HistoryDTO.pageInfo pageInfo = HistoryDTO.pageInfo.builder() // 페이지 정보 구성
                    .currentSize(historyPage.getNumberOfElements())
                    .currentPage(command.getPage())
                    .totalCount(historyPage.getTotalElements())
                    .totalPage(historyPage.getTotalPages())
                    .build();

                return Mono.just(searchHistoryDTOMapper.of(pageInfo, historyPage.getContent()));
            });
    }

    /**
     * 사용자 조회 이력 페이지 조회 WHERE 절 구성
     * @param adultOnly: 성인물 여부
     * @param weekInterval: 주 간격
     */
    private String whereSearchHistoryPage(AdultOnly adultOnly, Integer weekInterval) {

        String result = " WHERE memberId IS NOT NULL";

        if (adultOnly != null) result += " AND adultOnly = '" + adultOnly.name() + "'";
        if (weekInterval != null) result += " AND createdAt BETWEEN TIMESTAMPADD(WEEK, " + (weekInterval * -1) + ", NOW()) AND NOW()";

        return result;
    }

    /**
     * 사용자 조회 이력 페이지 조회 HAVING 절 구성
     * @param historyCount: 조회 수
     */
    private String havingSearchHistoryPage(Integer historyCount) {

        return historyCount != null ?
            " HAVING COUNT(memberId) >= " + historyCount
            : "";
    }
}
