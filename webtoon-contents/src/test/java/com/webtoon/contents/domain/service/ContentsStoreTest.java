package com.webtoon.contents.domain.service;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.domain.Contents;
import com.webtoon.contents.domain.Evaluation;
import com.webtoon.contents.domain.EvaluationType;
import com.webtoon.contents.infrastructure.dao.ContentsRepository;
import com.webtoon.contents.infrastructure.dao.EvaluationRepository;
import com.webtoon.contents.infrastructure.factory.ContentsTestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
        ContentsCommand.ExchangedContentsIdForEvaluationRegister command = ContentsTestFactory.exchangedContentsIdForEvaluationRegisterCommand();

        given(evaluationRepository.save(any(Evaluation.class))).willReturn(ContentsTestFactory.evaluationMono());
        given(contentsRepository.save(any(Contents.class))).willReturn(ContentsTestFactory.contentsMono());

        Mono<Evaluation> result = contentsStore.evaluationRegister(ContentsTestFactory.contents(0, 0), command);

        verify(evaluationRepository).save(any(Evaluation.class));

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("가격 변경")
    @Test
    void pricingModify() {
        ContentsCommand.PricingModify command = ContentsTestFactory.pricingModifyCommand();

        given(contentsRepository.save(any(Contents.class))).willReturn(ContentsTestFactory.contentsMono());

        Mono<Void> result = contentsStore.pricingModify(ContentsTestFactory.contents(0, 0), command);

        verify(contentsRepository).save(any(Contents.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("평가 삭제")
    @Test
    void evaluationDelete() {

        given(contentsRepository.save(any(Contents.class))).willReturn(ContentsTestFactory.contentsMono());
        given(evaluationRepository.delete(any(Evaluation.class))).willReturn(Mono.empty());

        Mono<Void> result = contentsStore.evaluationDelete(ContentsTestFactory.contents(1, 0), ContentsTestFactory.evaluation(EvaluationType.LIKE));

        verify(contentsRepository).save(any(Contents.class));
        verify(evaluationRepository).delete(any(Evaluation.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}