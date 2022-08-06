package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Contents;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.domain.service.dto.ContentsDTOMapper;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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
    @MockBean
    private ContentsDTOMapper contentsDTOMapper;

    @DisplayName("작품 정보 조회")
    @Test
    void findByContentsToken() {

        given(contentsRepository.findByContentsToken(anyString())).willReturn(contentsMono());

        Mono<Contents> result = contentsReader.findByContentsToken(UUID.randomUUID().toString());

        verify(contentsRepository).findByContentsToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("이미 등록된 평가가 있는지 확인")
    @Test
    void evaluationExistCheck() {
        ContentsCommand.ExchangedContentsIdForEvaluationRegister command = exchangedContentsIdForEvaluationRegisterCommand();

        given(evaluationRepository.findByMemberIdAndContentsId(anyLong(), anyLong())).willReturn(Mono.empty());

        Mono<Void> result = contentsReader.evaluationExistCheck(command);

        verify(evaluationRepository).findByMemberIdAndContentsId(anyLong(), anyLong());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("이미 등록된 평가가 있는지 확인 > 이미 존재함")
    @Test
    void evaluationExistCheckException() {
        ContentsCommand.ExchangedContentsIdForEvaluationRegister command = exchangedContentsIdForEvaluationRegisterCommand();

        given(evaluationRepository.findByMemberIdAndContentsId(anyLong(), anyLong())).willReturn(evaluationMono());

        Mono<Void> result = contentsReader.evaluationExistCheck(command);

        verify(evaluationRepository).findByMemberIdAndContentsId(anyLong(), anyLong());

        StepVerifier.create(result.log())
            .expectError(AlreadyDataException.class)
            .verify();
    }

    @DisplayName("좋아요/싫어요 Top3 작품 조회")
    @Test
    void evaluationTop3Contents() {

        given(contentsRepository.findTop3ByLikeCountGreaterThanOrderByLikeCountDesc(anyLong())).willReturn(likeTop3ContentsFlux());
        given(contentsRepository.findTop3ByDislikeCountGreaterThanOrderByDislikeCountDesc(anyLong())).willReturn(dislikeTop3ContentsFlux());
        given(contentsDTOMapper.of(anyList(), anyList())).willReturn(evaluationTop3ContentsDTO());

        Mono<ContentsDTO.EvaluationTop3Contents> result = contentsReader.evaluationTop3Contents();

        verify(contentsRepository).findTop3ByLikeCountGreaterThanOrderByLikeCountDesc(anyLong());
        verify(contentsRepository).findTop3ByDislikeCountGreaterThanOrderByDislikeCountDesc(anyLong());

        StepVerifier.create(result.log())
            .assertNext(evaluationTop3Contents -> assertAll(() -> {
                assertEquals(3, evaluationTop3Contents.getLikeTop3Contents().size());
                assertEquals(3, evaluationTop3Contents.getDislikeTop3Contents().size());
            }))
            .verifyComplete();
    }
}