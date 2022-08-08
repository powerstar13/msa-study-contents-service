package com.lezhin.history.infrastructure.dao;

import com.lezhin.history.domain.History;
import com.lezhin.history.domain.service.HistoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoryStoreImpl implements HistoryStore {

    private final HistoryRepository historyRepository;

    /**
     * 이력 목록 삭제
     * @param historyList: 이력 목록
     */
    @Override
    public Mono<Void> historyListDelete(List<History> historyList) {
        return historyRepository.deleteAll(historyList); // 이력 목록 삭제 처리
    }
}
