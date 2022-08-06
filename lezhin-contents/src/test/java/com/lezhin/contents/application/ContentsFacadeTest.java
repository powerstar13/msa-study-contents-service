package com.lezhin.contents.application;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.application.dto.ContentsCommandMapper;
import com.lezhin.contents.domain.service.ContentsService;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.infrastructure.webClient.MemberWebClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ContentsFacadeTest {

    @Autowired
    private ContentsFacade contentsFacade;

    @MockBean
    private MemberWebClientService memberWebClientService;
    @MockBean
    private ContentsCommandMapper contentsCommandMapper;
    @MockBean
    private ContentsService contentsService;

    @DisplayName("평가 등록")
    @Test
    void evaluationRegister() {
        ContentsCommand.EvaluationRegister command = evaluationRegisterCommand();

        given(memberWebClientService.exchangeMemberToken(anyString())).willReturn(exchangeMemberTokenResponseMono());
        given(contentsCommandMapper.of(anyLong(), any(ContentsCommand.EvaluationRegister.class))).willReturn(exchangedMemberIdForEvaluationRegisterCommand());
        given(contentsService.evaluationRegister(any(ContentsCommand.ExchangedMemberIdForEvaluationRegister.class))).willReturn(evaluationTokenInfoDTOMono());

        Mono<ContentsDTO.EvaluationTokenInfo> evaluationTokenInfoMono = contentsFacade.evaluationRegister(command);

        verify(memberWebClientService).exchangeMemberToken(anyString());

        StepVerifier.create(evaluationTokenInfoMono.log())
            .assertNext(evaluationTokenInfo -> assertNotNull(evaluationTokenInfo.getEvaluationToken()))
            .verifyComplete();
    }
}