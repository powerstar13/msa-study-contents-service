package com.webtoon.contents.infrastructure.dao;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.domain.Contents;
import com.webtoon.contents.domain.Evaluation;
import com.webtoon.contents.domain.service.ContentsReader;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import com.webtoon.contents.domain.service.dto.ContentsDTOMapper;
import com.webtoon.contents.infrastructure.exception.status.AlreadyDataException;
import com.webtoon.contents.infrastructure.exception.status.ExceptionMessage;
import com.webtoon.contents.infrastructure.exception.status.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
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
            .map(objects -> contentsDTOMapper.of(objects.getT1(), objects.getT2()));
    }

    /**
     * 회원이 평가한 목록 조회
     * @param memberId: 회원 고유번호
     * @return Evaluation: 평가된 목록
     */
    @Override
    public Flux<Evaluation> findEvaluationListByMemberId(long memberId) {
        return evaluationRepository.findAllByMemberId(memberId); // 1. 평가 정보 조회
    }

    /**
     * 작품 정보 조회
     * @param contentsId: 작품 고유번호
     * @return Contents: 작품 레퍼런스
     */
    @Override
    public Mono<Contents> findContentsByContentsId(long contentsId) {
        return contentsRepository.findByContentsId(contentsId)
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundContents.getMessage())));
    }
}
