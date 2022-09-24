package com.webtoon.history.applicaiton;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.applicaiton.dto.HistoryCommandMapper;
import com.webtoon.history.domain.service.HistoryService;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import com.webtoon.history.infrastructure.exception.status.BadRequestException;
import com.webtoon.history.infrastructure.webClient.ContentsWebClientService;
import com.webtoon.history.infrastructure.webClient.MemberWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HistoryFacade {

    private final ContentsWebClientService contentsWebClientService;
    private final MemberWebClientService memberWebClientService;
    private final HistoryCommandMapper historyCommandMapper;
    private final HistoryService historyService;

    /**
     * 작품별 조회 이력 페이지
     * @param command: 조회할 정보
     * @return ContentsHistoryPage: 이력 페이지
     */
    public Mono<HistoryDTO.ContentsHistoryPage> contentsHistoryPage(HistoryCommand.ContentsHistoryPage command) {

        // 1. 작품 고유번호 가져오기
        return contentsWebClientService.exchangeContentsToken(command.getContentsToken())
            .flatMap(exchangeContentsTokenResponse -> {
                if (exchangeContentsTokenResponse.getRt() != 200) return Mono.error(new BadRequestException(exchangeContentsTokenResponse.getRtMsg()));

                HistoryCommand.ExchangedContentsHistoryPage exchangedContentsHistoryPage = historyCommandMapper.of(exchangeContentsTokenResponse.getContentsId(), command);

                return historyService.contentsHistoryPage(exchangedContentsHistoryPage); // 2. 작품별 조회 이력 페이지 조회 처리
            });
    }

    /**
     * 사용자 이력 페이지
     * @param command: 조회할 정보
     * @return SearchHistoryPage: 이력 페이지
     */
    public Mono<HistoryDTO.SearchHistoryPage> searchHistoryPage(HistoryCommand.SearchHistoryPage command) {
        return historyService.searchHistoryPage(command); // 사용자 이력 페이지 조회 처리
    }

    /**
     * 이력 삭제
     * @param memberToken: 회원 대체 식별키
     */
    public Mono<Void> historyDeleteByMember(String memberToken) {

        return memberWebClientService.exchangeMemberToken(memberToken) // 1. 회원 대체 식별키로 회원 고유번호 가져오기
            .flatMap(exchangeMemberTokenResponse -> {
                if (exchangeMemberTokenResponse.getRt() != 200) return Mono.error(new BadRequestException(exchangeMemberTokenResponse.getRtMsg()));

                return historyService.historyDeleteByMember(exchangeMemberTokenResponse.getMemberId()); // 2. 이력 삭제 처리
            });
    }
}
