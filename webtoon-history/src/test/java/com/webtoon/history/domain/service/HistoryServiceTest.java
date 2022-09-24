package com.webtoon.history.domain.service;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import com.webtoon.history.infrastructure.factory.HistoryTestFactory;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class HistoryServiceTest {

    @Autowired
    private HistoryService historyService;

    @MockBean
    private HistoryReader historyReader;
    @MockBean
    private HistoryStore historyStore;

    @DisplayName("작품별 조회 이력 페이지")
    @Test
    void contentsHistoryPage() {

        given(historyReader.findAllContentsHistoryPage(any(HistoryCommand.ExchangedContentsHistoryPage.class))).willReturn(HistoryTestFactory.contentsHistoryPageDTOMono());

        Mono<HistoryDTO.ContentsHistoryPage> result = historyService.contentsHistoryPage(HistoryTestFactory.exchangedContentsHistoryPageCommand());

        StepVerifier.create(result.log())
            .assertNext(contentsHistoryPage -> assertNotNull(contentsHistoryPage.getHistoryList()))
            .verifyComplete();
    }

    @DisplayName("사용자 조회 이력 페이지")
    @Test
    void test() {

        given(historyReader.findAllSearchHistoryPage(any(HistoryCommand.SearchHistoryPage.class))).willReturn(HistoryTestFactory.searchHistoryPageDTOMono());

        Mono<HistoryDTO.SearchHistoryPage> result = historyService.searchHistoryPage(HistoryTestFactory.searchHistoryPageCommand());

        StepVerifier.create(result.log())
            .assertNext(searchHistoryPage -> assertNotNull(searchHistoryPage.getMemberList()))
            .verifyComplete();
    }

    @DisplayName("이력 삭제 처리")
    @Test
    void historyDeleteByMember() {

        given(historyReader.findHistoryListByMemberId(anyLong())).willReturn(HistoryTestFactory.historyFlux());
        given(historyStore.historyListDelete(anyList())).willReturn(Mono.empty());

        Mono<Void> result = historyService.historyDeleteByMember(RandomUtils.nextLong());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}