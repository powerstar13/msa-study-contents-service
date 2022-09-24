package com.webtoon.history.domain.service;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryReader historyReader;
    private final HistoryStore historyStore;

    /**
     * 작품별 조회 이력 페이지
     * @param command: 조회할 정보
     * @return HistoryPage: 이력 페이지
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<HistoryDTO.ContentsHistoryPage> contentsHistoryPage(HistoryCommand.ExchangedContentsHistoryPage command) {
        return historyReader.findAllContentsHistoryPage(command); // 작품별 조회 이력 페이지 조회
    }

    /**
     * 사용자 조회 이력 페이지
     * @param command: 조회할 정보
     * @return HistoryPage: 이력 페이지
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<HistoryDTO.SearchHistoryPage> searchHistoryPage(HistoryCommand.SearchHistoryPage command) {
        return historyReader.findAllSearchHistoryPage(command); // 사용자 조회 이력 페이지 조회
    }

    /**
     * 이력 삭제 처리
     * @param memberId: 회원 고유번호
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> historyDeleteByMember(long memberId) {

        return historyReader.findHistoryListByMemberId(memberId) // 1. 이력 목록 조회
            .collectList()
            .flatMap(historyStore::historyListDelete) // 2. 이력 목록 삭제
            .then();
    }
}
