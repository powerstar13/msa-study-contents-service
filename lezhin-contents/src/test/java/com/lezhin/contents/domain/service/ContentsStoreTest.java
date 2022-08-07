package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Contents;
import com.lezhin.contents.domain.Evaluation;
import com.lezhin.contents.infrastructure.dao.ContentsRepository;
import com.lezhin.contents.infrastructure.dao.EvaluationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ContentsStoreTest {

    @Autowired
    private ContentsStore contentsStore;

    @MockBean
    private ContentsRepository contentsRepository;
    @MockBean
    private EvaluationRepository evaluationRepository;

    @DisplayName("평가 등록")
    @Test
    void evaluationRegister() {
        ContentsCommand.ExchangedContentsIdForEvaluationRegister command = exchangedContentsIdForEvaluationRegisterCommand();

        given(evaluationRepository.save(any(Evaluation.class))).willReturn(evaluationMono());
        given(contentsRepository.save(any(Contents.class))).willReturn(contentsMono());

        Mono<Evaluation> result = contentsStore.evaluationRegister(contents(0, 0), command);

        verify(evaluationRepository).save(any(Evaluation.class));

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("가격 변경")
    @Test
    void pricingModify() {
        ContentsCommand.PricingModify command = pricingModifyCommand();

        given(contentsRepository.save(any(Contents.class))).willReturn(contentsMono());

        Mono<Void> result = contentsStore.pricingModify(contents(0, 0), command);

        verify(contentsRepository).save(any(Contents.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}