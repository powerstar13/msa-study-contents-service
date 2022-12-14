package com.webtoon.history.domain.service;

import com.webtoon.history.infrastructure.dao.HistoryRepository;
import com.webtoon.history.infrastructure.factory.HistoryTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class HistoryStoreTest {

    @Autowired
    private HistoryStore historyStore;

    @MockBean
    private HistoryRepository historyRepository;

    @DisplayName("이력 목록 삭제")
    @Test
    void historyListDelete() {

        given(historyRepository.deleteAll(anyList())).willReturn(Mono.empty());

        Mono<Void> result = historyStore.historyListDelete(HistoryTestFactory.historyList());

        verify(historyRepository).deleteAll(anyList());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}