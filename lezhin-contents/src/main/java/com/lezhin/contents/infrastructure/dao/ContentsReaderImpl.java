package com.lezhin.contents.infrastructure.dao;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Contents;
import com.lezhin.contents.domain.service.ContentsReader;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.domain.service.dto.ContentsDTOMapper;
import com.lezhin.contents.infrastructure.exception.status.AlreadyDataException;
import com.lezhin.contents.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.contents.infrastructure.exception.status.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ContentsReaderImpl implements ContentsReader {

    private final ContentsRepository contentsRepository;
    private final EvaluationRepository evaluationRepository;
    private final ContentsDTOMapper contentsDTOMapper;

    /**
     * 작품 정보 조회
     * @param contentsToken: 작품 대체 식별키
     * @return Contents: 작품 레퍼런스
     */
    @Override
    public Mono<Contents> findByContentsToken(String contentsToken) {

        return contentsRepository.findByContentsToken(contentsToken) // 작품 정보 조회
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundContents.getMessage())));
    }

    /**
     * 이미 등록된 평가가 있는지 확인
     * @param command: 등록할 평가 정보
     */
    @Override
    public Mono<Void> evaluationExistCheck(ContentsCommand.ExchangedContentsIdForEvaluationRegister command) {

        return evaluationRepository.findByMemberIdAndContentsId(command.getMemberId(), command.getContentsId())
            .hasElement()
            .flatMap(aBoolean ->
                aBoolean ?
                    Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataEvaluation.getMessage()))
                    : Mono.empty()
            );
    }

    /**
     * 좋아요/싫어요 Top3 작품 조회
     * @return EvaluationTop3Contents: 좋아요/싫어요 Top3 작품 정보
     */
    @Override
    public Mono<ContentsDTO.EvaluationTop3Contents> evaluationTop3Contents() {

        return contentsRepository.findTop3ByLikeCountGreaterThanOrderByLikeCountDesc(0).collectList() // 좋아요 Top3 작품 목록
            .zipWith(contentsRepository.findTop3ByDislikeCountGreaterThanOrderByDislikeCountDesc(0).collectList()) // 싫어요 Top3 작품 목록
            .flatMap(objects -> Mono.just(contentsDTOMapper.of(objects.getT1(), objects.getT2())));
    }
}
