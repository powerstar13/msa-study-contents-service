package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.application.dto.ContentsCommandMapper;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
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
class ContentsServiceTest {

    @Autowired
    private ContentsService contentsService;

    @MockBean
    private ContentsReader contentsReader;
    @MockBean
    private ContentsStore contentsStore;
    @MockBean
    private ContentsCommandMapper contentsCommandMapper;

    @DisplayName("평가 등록 처리")
    @Test
    void evaluationRegister() {
        ContentsCommand.ExchangedMemberIdForEvaluationRegister command = exchangedMemberIdForEvaluationRegisterCommand();

        given(contentsReader.findByContentsToken(anyString())).willReturn(contentsMono());
        given(contentsCommandMapper.of(anyLong(), any(ContentsCommand.ExchangedMemberIdForEvaluationRegister.class))).willReturn(exchangedContentsIdForEvaluationRegisterCommand());
        given(contentsReader.evaluationExistCheck(any(ContentsCommand.ExchangedContentsIdForEvaluationRegister.class))).willReturn(Mono.empty());
        given(contentsStore.evaluationRegister(any(ContentsCommand.ExchangedContentsIdForEvaluationRegister.class))).willReturn(evaluationMono());

        Mono<ContentsDTO.EvaluationTokenInfo> evaluationTokenInfoMono = contentsService.evaluationRegister(command);

        verify(contentsReader).findByContentsToken(anyString());

        StepVerifier.create(evaluationTokenInfoMono.log())
            .assertNext(evaluationTokenInfo -> assertNotNull(evaluationTokenInfo.getEvaluationToken()))
            .verifyComplete();
    }
}