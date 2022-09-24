package com.webtoon.history.applicaiton;

import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.applicaiton.dto.HistoryCommandMapper;
import com.webtoon.history.domain.service.HistoryService;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import com.webtoon.history.infrastructure.webClient.ContentsWebClientService;
import com.webtoon.history.infrastructure.webClient.MemberWebClientService;
import com.webtoon.history.infrastructure.factory.HistoryTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class HistoryFacadeTest {

    @Autowired
    private HistoryFacade historyFacade;

    @MockBean
    private ContentsWebClientService contentsWebClientService;
    @MockBean
    private MemberWebClientService memberWebClientService;
    @MockBean
    private HistoryCommandMapper historyCommandMapper;
    @MockBean
    private HistoryService historyService;

    @DisplayName("작품별 조회 이력 페이지")
    @Test
    void contentsHistoryPage() {

        given(contentsWebClientService.exchangeContentsToken(anyString())).willReturn(HistoryTestFactory.exchangeContentsTokenResponseMono());
        given(historyCommandMapper.of(anyLong(), any(HistoryCommand.ContentsHistoryPage.class))).willReturn(HistoryTestFactory.exchangedContentsHistoryPageCommand());
        given(historyService.contentsHistoryPage(any(HistoryCommand.ExchangedContentsHistoryPage.class))).willReturn(HistoryTestFactory.contentsHistoryPageDTOMono());

        Mono<HistoryDTO.ContentsHistoryPage> result = historyFacade.contentsHistoryPage(HistoryTestFactory.contentsHistoryPageCommand());

        verify(contentsWebClientService).exchangeContentsToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(contentsHistoryPage -> assertNotNull(contentsHistoryPage.getHistoryList()))
            .verifyComplete();
    }

    @DisplayName("이력 삭제")
    @Test
    void historyDeleteByMember() {

        given(memberWebClientService.exchangeMemberToken(anyString())).willReturn(HistoryTestFactory.exchangeMemberTokenResponseMono());
        given(historyService.historyDeleteByMember(anyLong())).willReturn(Mono.empty());

        Mono<Void> result = historyFacade.historyDeleteByMember(UUID.randomUUID().toString());

        verify(memberWebClientService).exchangeMemberToken(anyString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}