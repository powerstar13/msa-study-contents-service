package com.webtoon.contents.domain.service;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.application.dto.ContentsCommandMapper;
import com.webtoon.contents.domain.Contents;
import com.webtoon.contents.domain.Evaluation;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import com.webtoon.contents.infrastructure.factory.ContentsTestFactory;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
        ContentsCommand.ExchangedMemberIdForEvaluationRegister command = ContentsTestFactory.exchangedMemberIdForEvaluationRegisterCommand();

        given(contentsReader.findByContentsToken(anyString())).willReturn(ContentsTestFactory.contentsMono());
        given(contentsCommandMapper.of(anyLong(), any(ContentsCommand.ExchangedMemberIdForEvaluationRegister.class))).willReturn(ContentsTestFactory.exchangedContentsIdForEvaluationRegisterCommand());
        given(contentsReader.evaluationExistCheck(any(ContentsCommand.ExchangedContentsIdForEvaluationRegister.class))).willReturn(Mono.empty());
        given(contentsStore.evaluationRegister(any(Contents.class), any(ContentsCommand.ExchangedContentsIdForEvaluationRegister.class))).willReturn(ContentsTestFactory.evaluationMono());

        Mono<ContentsDTO.EvaluationTokenInfo> result = contentsService.evaluationRegister(command);

        StepVerifier.create(result.log())
            .assertNext(evaluationTokenInfo -> assertNotNull(evaluationTokenInfo.getEvaluationToken()))
            .verifyComplete();
    }

    @DisplayName("좋아요/싫어요 Top3 작품 조회 처리")
    @Test
    void evaluationTop3Contents() {

        given(contentsReader.evaluationTop3Contents()).willReturn(ContentsTestFactory.evaluationTop3ContentsDTOMono());

        Mono<ContentsDTO.EvaluationTop3Contents> result = contentsService.evaluationTop3Contents();

        StepVerifier.create(result.log())
            .assertNext(evaluationTop3Contents -> assertAll(() -> {
                assertEquals(3, evaluationTop3Contents.getLikeTop3Contents().size());
                assertEquals(3, evaluationTop3Contents.getDislikeTop3Contents().size());
            }))
            .verifyComplete();
    }

    @DisplayName("작품 고유번호 가져오기")
    @Test
    void exchangeContentsToken() {

        given(contentsReader.findByContentsToken(anyString())).willReturn(ContentsTestFactory.contentsMono());

        Mono<ContentsDTO.ContentsIdInfo> result = contentsService.exchangeContentsToken(UUID.randomUUID().toString());

        StepVerifier.create(result.log())
            .assertNext(contentsIdInfo -> assertTrue(contentsIdInfo.getContentsId() > 0))
            .verifyComplete();
    }

    @DisplayName("가격 변경 처리")
    @Test
    void pricingModify() {
        ContentsCommand.PricingModify command = ContentsTestFactory.pricingModifyCommand();

        given(contentsReader.findByContentsToken(anyString())).willReturn(ContentsTestFactory.contentsMono());
        given(contentsStore.pricingModify(any(Contents.class), any(ContentsCommand.PricingModify.class))).willReturn(Mono.empty());

        Mono<Void> result = contentsService.pricingModify(command);

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("평가 삭제 처리")
    @Test
    void evaluationDeleteByMember() {

        given(contentsReader.findEvaluationListByMemberId(anyLong())).willReturn(ContentsTestFactory.evaluationFlux());
        given(contentsReader.findContentsByContentsId(anyLong())).willReturn(ContentsTestFactory.contentsMono());
        given(contentsStore.evaluationDelete(any(Contents.class), any(Evaluation.class))).willReturn(Mono.empty());

        Mono<Void> result = contentsService.evaluationDeleteByMember(RandomUtils.nextLong());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}