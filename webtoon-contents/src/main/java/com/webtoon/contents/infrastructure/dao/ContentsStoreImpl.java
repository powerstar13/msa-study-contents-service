package com.webtoon.contents.infrastructure.dao;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.domain.Contents;
import com.webtoon.contents.domain.Evaluation;
import com.webtoon.contents.domain.EvaluationType;
import com.webtoon.contents.domain.service.ContentsStore;
import com.webtoon.contents.infrastructure.exception.status.ExceptionMessage;
import com.webtoon.contents.infrastructure.exception.status.RegisterFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentsStoreImpl implements ContentsStore {

    private final ContentsRepository contentsRepository;
    private final EvaluationRepository evaluationRepository;

    /**
     * 평가 등록
     * @param contents: 작품 레퍼러스
     * @param command: 등록할 평가 정보
     * @return Evaluation: 평가 레퍼런스
     */
    @Override
    public Mono<Evaluation> evaluationRegister(Contents contents, ContentsCommand.ExchangedContentsIdForEvaluationRegister command) {

        return evaluationRepository.save(command.toEntity()) // 평가 등록
            .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailEvaluation.getMessage())))
            .flatMap(evaluation -> {
                if (command.getEvaluationType().equals(EvaluationType.LIKE)) contents.likeUp(); // 좋아요 추가
                else contents.dislikeUp(); // 싫어요 추가

                return contentsRepository.save(contents)
                    .map(savedContents -> evaluation);
            });
    }

    /**
     * 가격 변경
     * @param contents: 작품 레퍼런스
     * @param command: 변경할 가격 정보
     */
    @Override
    public Mono<Void> pricingModify(Contents contents, ContentsCommand.PricingModify command) {

        contents.pricingModify(command.getPricingType(), command.getCoin());

        return contentsRepository.save(contents)
            .then();
    }

    /**
     * 평가 삭제
     * @param contents: 작품 레퍼런스
     * @param evaluation: 평가 레퍼런스
     */
    @Override
    public Mono<Void> evaluationDelete(Contents contents, Evaluation evaluation) {

        // 좋아요/싫어요 해제
        if (evaluation.getEvaluationType().equals(EvaluationType.LIKE)) contents.likeDown();
        else contents.dislikeDown();

        return contentsRepository.save(contents) // 작품 정보 업데이트 처리
            .zipWith(evaluationRepository.delete(evaluation)) // 평가 정보 삭제 처리
            .then();
    }
}
