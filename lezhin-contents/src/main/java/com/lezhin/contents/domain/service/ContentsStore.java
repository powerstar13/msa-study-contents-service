package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Evaluation;
import reactor.core.publisher.Mono;

public interface ContentsStore {

    Mono<Evaluation> evaluationRegister(ContentsCommand.ExchangedContentsIdForEvaluationRegister command);
}
