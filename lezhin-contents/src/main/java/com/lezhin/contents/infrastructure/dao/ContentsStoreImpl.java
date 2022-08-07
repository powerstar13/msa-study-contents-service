package com.lezhin.contents.infrastructure.dao;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Contents;
import com.lezhin.contents.domain.Evaluation;
import com.lezhin.contents.domain.EvaluationType;
import com.lezhin.contents.domain.service.ContentsStore;
import com.lezhin.contents.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.contents.infrastructure.exception.status.RegisterFailException;
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
                    .then(Mono.just(evaluation));
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
}
