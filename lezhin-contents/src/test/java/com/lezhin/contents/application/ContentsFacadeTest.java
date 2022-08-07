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

import java.util.UUID;

import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.*;
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
        ContentsCommand.EvaluationRegister command = evaluationRegisterCommand();

        given(memberWebClientService.exchangeMemberToken(anyString())).willReturn(exchangeMemberTokenResponseMono());
        given(contentsCommandMapper.of(anyLong(), any(ContentsCommand.EvaluationRegister.class))).willReturn(exchangedMemberIdForEvaluationRegisterCommand());
        given(contentsService.evaluationRegister(any(ContentsCommand.ExchangedMemberIdForEvaluationRegister.class))).willReturn(evaluationTokenInfoDTOMono());

        Mono<ContentsDTO.EvaluationTokenInfo> result = contentsFacade.evaluationRegister(command);

        verify(memberWebClientService).exchangeMemberToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(evaluationTokenInfo -> assertNotNull(evaluationTokenInfo.getEvaluationToken()))
            .verifyComplete();
    }

    @DisplayName("좋아요/싫어요 Top3 작품 조회")
    @Test
    void evaluationTop3Contents() {

        given(contentsService.evaluationTop3Contents()).willReturn(evaluationTop3ContentsDTOMono());

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

        given(contentsService.exchangeContentsToken(any(String.class))).willReturn(contentsIdInfoMono());

        Mono<ContentsDTO.ContentsIdInfo> result = contentsFacade.exchangeContentsToken(UUID.randomUUID().toString());

        verify(contentsService).exchangeContentsToken(any(String.class));

        StepVerifier.create(result.log())
            .assertNext(contentsIdInfo -> assertTrue(contentsIdInfo.getContentsId() > 0))
            .verifyComplete();
    }
}