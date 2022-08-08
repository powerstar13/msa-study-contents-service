package com.lezhin.history.domain.service;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.lezhin.history.infrastructure.factory.HistoryTestFactory.*;
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

        given(historyReader.findAllContentsHistoryPage(any(HistoryCommand.ExchangedContentsHistoryPage.class))).willReturn(contentsHistoryPageDTOMono());

        Mono<HistoryDTO.ContentsHistoryPage> result = historyService.contentsHistoryPage(exchangedContentsHistoryPageCommand());

        verify(historyReader).findAllContentsHistoryPage(any(HistoryCommand.ExchangedContentsHistoryPage.class));

        StepVerifier.create(result.log())
            .assertNext(contentsHistoryPage -> assertNotNull(contentsHistoryPage.getHistoryList()))
            .verifyComplete();
    }

    @DisplayName("사용자 조회 이력 페이지")
    @Test
    void test() {

        given(historyReader.findAllSearchHistoryPage(any(HistoryCommand.SearchHistoryPage.class))).willReturn(searchHistoryPageDTOMono());

        Mono<HistoryDTO.SearchHistoryPage> result = historyService.searchHistoryPage(searchHistoryPageCommand());

        verify(historyReader).findAllSearchHistoryPage(any(HistoryCommand.SearchHistoryPage.class));

        StepVerifier.create(result.log())
            .assertNext(searchHistoryPage -> assertNotNull(searchHistoryPage.getMemberList()))
            .verifyComplete();
    }

    @DisplayName("이력 삭제 처리")
    @Test
    void historyDeleteByMember() {

        given(historyReader.findHistoryListByMemberId(anyLong())).willReturn(historyFlux());
        given(historyStore.historyListDelete(anyList())).willReturn(Mono.empty());

        Mono<Void> result = historyService.historyDeleteByMember(RandomUtils.nextLong());

        verify(historyReader).findHistoryListByMemberId(anyLong());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}