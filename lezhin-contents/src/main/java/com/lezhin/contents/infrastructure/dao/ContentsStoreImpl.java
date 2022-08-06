package com.lezhin.contents.infrastructure.dao;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Evaluation;
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

    private final EvaluationRepository evaluationRepository;

    /**
     * 평가 등록
     * @param command: 등록할 평가 정보
     * @return Evaluation: 평가 레퍼런스
     */
    @Override
    public Mono<Evaluation> evaluationRegister(ContentsCommand.ExchangedContentsIdForEvaluationRegister command) {

        return evaluationRepository.save(command.toEntity()) // 평가 등록
            .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailEvaluation.getMessage())));
    }
}