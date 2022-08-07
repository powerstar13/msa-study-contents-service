package com.lezhin.history.applicaiton;

import com.lezhin.history.applicaiton.dto.HistoryCommand;
import com.lezhin.history.applicaiton.dto.HistoryCommandMapper;
import com.lezhin.history.domain.service.HistoryService;
import com.lezhin.history.domain.service.dto.HistoryDTO;
import com.lezhin.history.infrastructure.webClient.ContentsWebClientService;
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
class HistoryFacadeTest {

    @Autowired
    private HistoryFacade historyFacade;

    @MockBean
    private ContentsWebClientService contentsWebClientService;
    @MockBean
    private HistoryCommandMapper historyCommandMapper;
    @MockBean
    private HistoryService historyService;

    @DisplayName("작품별 조회 이력 페이지")
    @Test
    void contentsHistoryPage() {

        given(contentsWebClientService.exchangeContentsToken(anyString())).willReturn(exchangeContentsTokenResponseMono());
        given(historyCommandMapper.of(anyLong(), any(HistoryCommand.ContentsHistoryPage.class))).willReturn(exchangedContentsHistoryPageCommand());
        given(historyService.contentsHistoryPage(any(HistoryCommand.ExchangedContentsHistoryPage.class))).willReturn(contentsHistoryPageDTOMono());

        Mono<HistoryDTO.ContentsHistoryPage> result = historyFacade.contentsHistoryPage(contentsHistoryPageCommand());

        verify(contentsWebClientService).exchangeContentsToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(contentsHistoryPage -> assertNotNull(contentsHistoryPage.getHistoryList()))
            .verifyComplete();
    }
}