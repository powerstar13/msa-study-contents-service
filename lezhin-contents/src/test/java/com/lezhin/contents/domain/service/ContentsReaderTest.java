package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Contents;
import com.lezhin.contents.infrastructure.dao.ContentsRepository;
import com.lezhin.contents.infrastructure.dao.EvaluationRepository;
import com.lezhin.contents.infrastructure.exception.status.AlreadyDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ContentsReaderTest {

    @Autowired
    private ContentsReader contentsReader;

    @MockBean
    private ContentsRepository contentsRepository;
    @MockBean
    private EvaluationRepository evaluationRepository;

    @DisplayName("작품 정보 조회")
    @Test
    void findByContentsToken() {

        given(contentsRepository.findByContentsToken(anyString())).willReturn(contentsMono());

        Mono<Contents> contentsMono = contentsReader.findByContentsToken(UUID.randomUUID().toString());

        verify(contentsRepository).findByContentsToken(anyString());

        StepVerifier.create(contentsMono.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("이미 등록된 평가가 있는지 확인")
    @Test
    void evaluationExistCheck() {
        ContentsCommand.ExchangedContentsIdForEvaluationRegister command = exchangedContentsIdForEvaluationRegisterCommand();

        given(evaluationRepository.findByMemberIdAndContentsId(anyLong(), anyLong())).willReturn(Mono.empty());

        Mono<Void> voidMono = contentsReader.evaluationExistCheck(command);

        verify(evaluationRepository).findByMemberIdAndContentsId(anyLong(), anyLong());

        StepVerifier.create(voidMono.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("이미 등록된 평가가 있는지 확인 > 이미 존재함")
    @Test
    void evaluationExistCheckException() {
        ContentsCommand.ExchangedContentsIdForEvaluationRegister command = exchangedContentsIdForEvaluationRegisterCommand();

        given(evaluationRepository.findByMemberIdAndContentsId(anyLong(), anyLong())).willReturn(evaluationMono());

        Mono<Void> voidMono = contentsReader.evaluationExistCheck(command);

        verify(evaluationRepository).findByMemberIdAndContentsId(anyLong(), anyLong());

        StepVerifier.create(voidMono.log())
            .expectError(AlreadyDataException.class)
            .verify();
    }
}