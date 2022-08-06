package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Evaluation;
import com.lezhin.contents.infrastructure.dao.EvaluationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.evaluationMono;
import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.exchangedContentsIdForEvaluationRegisterCommand;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ContentsStoreTest {

    @Autowired
    private ContentsStore contentsStore;

    @MockBean
    private EvaluationRepository evaluationRepository;

    @DisplayName("평가 등록")
    @Test
    void evaluationRegister() {
        ContentsCommand.ExchangedContentsIdForEvaluationRegister command = exchangedContentsIdForEvaluationRegisterCommand();

        given(evaluationRepository.save(any(Evaluation.class))).willReturn(evaluationMono());

        Mono<Evaluation> evaluationMono = contentsStore.evaluationRegister(command);

        verify(evaluationRepository).save(any(Evaluation.class));

        StepVerifier.create(evaluationMono.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }
}