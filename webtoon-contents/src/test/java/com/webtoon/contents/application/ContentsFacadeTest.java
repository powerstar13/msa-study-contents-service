package com.webtoon.contents.application;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.application.dto.ContentsCommandMapper;
import com.webtoon.contents.domain.service.ContentsService;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import com.webtoon.contents.infrastructure.webClient.MemberWebClientService;
import com.webtoon.contents.infrastructure.factory.ContentsTestFactory;
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
        ContentsCommand.EvaluationRegister command = ContentsTestFactory.evaluationRegisterCommand();

        given(memberWebClientService.exchangeMemberToken(anyString())).willReturn(ContentsTestFactory.exchangeMemberTokenResponseMono());
        given(contentsCommandMapper.of(anyLong(), any(ContentsCommand.EvaluationRegister.class))).willReturn(ContentsTestFactory.exchangedMemberIdForEvaluationRegisterCommand());
        given(contentsService.evaluationRegister(any(ContentsCommand.ExchangedMemberIdForEvaluationRegister.class))).willReturn(ContentsTestFactory.evaluationTokenInfoDTOMono());

        Mono<ContentsDTO.EvaluationTokenInfo> result = contentsFacade.evaluationRegister(command);

        verify(memberWebClientService).exchangeMemberToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(evaluationTokenInfo -> assertNotNull(evaluationTokenInfo.getEvaluationToken()))
            .verifyComplete();
    }

    @DisplayName("좋아요/싫어요 Top3 작품 조회")
    @Test
    void evaluationTop3Contents() {

        given(contentsService.evaluationTop3Contents()).willReturn(ContentsTestFactory.evaluationTop3ContentsDTOMono());

        Mono<ContentsDTO.EvaluationTop3Contents> result = contentsFacade.evaluationTop3Contents();

        verify(contentsService).evaluationTop3Contents();

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

        given(contentsService.exchangeContentsToken(anyString())).willReturn(ContentsTestFactory.contentsIdInfoMono());

        Mono<ContentsDTO.ContentsIdInfo> result = contentsFacade.exchangeContentsToken(UUID.randomUUID().toString());

        verify(contentsService).exchangeContentsToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(contentsIdInfo -> assertTrue(contentsIdInfo.getContentsId() > 0))
            .verifyComplete();
    }

    @DisplayName("가격 변경")
    @Test
    void pricingModify() {
        ContentsCommand.PricingModify command = ContentsTestFactory.pricingModifyCommand();

        given(contentsService.pricingModify(any(ContentsCommand.PricingModify.class))).willReturn(Mono.empty());

        Mono<Void> result = contentsFacade.pricingModify(command);

        verify(contentsService).pricingModify(any(ContentsCommand.PricingModify.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("평가 삭제")
    @Test
    void evaluationDeleteByMember() {

        given(memberWebClientService.exchangeMemberToken(anyString())).willReturn(ContentsTestFactory.exchangeMemberTokenResponseMono());
        given(contentsService.evaluationDeleteByMember(anyLong())).willReturn(Mono.empty());

        Mono<Void> result = contentsFacade.evaluationDeleteByMember(UUID.randomUUID().toString());

        verify(memberWebClientService).exchangeMemberToken(anyString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}