package com.lezhin.history.domain.service;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryReader historyReader;

    /**
     * 작품별 조회 이력 페이지
     * @param command: 조회할 정보
     * @return HistoryPage: 이력 페이지
     */
    @Override
    public Mono<HistoryDTO.ContentsHistoryPage> contentsHistoryPage(HistoryCommand.ExchangedContentsHistoryPage command) {
        return historyReader.findAllContentsHistoryPage(command); // 작품별 조회 이력 페이지 조회
    }

    /**
     * 사용자 조회 이력 페이지
     * @param command: 조회할 정보
     * @return HistoryPage: 이력 페이지
     */
    @Override
    public Mono<HistoryDTO.SearchHistoryPage> searchHistoryPage(HistoryCommand.SearchHistoryPage command) {
        return historyReader.findAllSearchHistoryPage(command); // 사용자 조회 이력 페이지 조회
    }
}
