package com.webtoon.history.infrastructure.dao;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.History;
import com.webtoon.history.domain.service.HistoryReader;
import com.webtoon.history.domain.service.dto.ContentsHistoryDTOMapper;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HistoryReaderImpl implements HistoryReader {

    private final HistoryRepository historyRepository;
    private final ContentsHistoryDTOMapper contentsHistoryDTOMapper;

    /**
     * 작품별 조회 이력 페이지
     * @param command: 조회할 정보
     * @return ContentsHistoryPage: 이력 페이지
     */
    @Override
    public Mono<HistoryDTO.ContentsHistoryPage> findAllContentsHistoryPage(HistoryCommand.ExchangedContentsHistoryPage command) {

        PageRequest pageRequest = PageRequest.of(command.getPageForPageable(), command.getSize());

        return historyRepository.findAllByContentsId(command.getContentsId(), pageRequest)
            .map(contentsHistoryDTOMapper::of)
            .collectList()
            .zipWith(historyRepository.countByContentsId(command.getContentsId()))
            .map(objects -> {
                Page<HistoryDTO.ContentsHistoryMemberInfo> contentsHistoryPage = new PageImpl<>(objects.getT1(), pageRequest, objects.getT2());

                HistoryDTO.pageInfo pageInfo = HistoryDTO.pageInfo.builder() // 페이지 정보 구성
                    .currentSize(contentsHistoryPage.getNumberOfElements())
                    .currentPage(command.getPage())
                    .totalCount(contentsHistoryPage.getTotalElements())
                    .totalPage(contentsHistoryPage.getTotalPages())
                    .build();

                return contentsHistoryDTOMapper.of(pageInfo, contentsHistoryPage.getContent());
            });
    }

    /**
     * 사용자 조회 이력 페이지
     * @param command: 조회할 정보
     * @return SearchHistoryPage: 이력 페이지
     */
    @Override
    public Mono<HistoryDTO.SearchHistoryPage> findAllSearchHistoryPage(HistoryCommand.SearchHistoryPage command) {
        return historyRepository.getSearchHistoryPage(command);
    }

    /**
     * 회원이 조회한 이력 목록 조회
     * @param memberId: 회원 고유번호
     * @return Evaluation: 이력 목록
     */
    @Override
    public Flux<History> findHistoryListByMemberId(long memberId) {
        return historyRepository.findAllByMemberId(memberId); // 이력 정보 조회 처리
    }
}
