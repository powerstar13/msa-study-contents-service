package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Contents;
import com.lezhin.contents.domain.Evaluation;
import reactor.core.publisher.Mono;

public interface ContentsStore {

    Mono<Evaluation> evaluationRegister(Contents contents, ContentsCommand.ExchangedContentsIdForEvaluationRegister command);

    Mono<Void> pricingModify(Contents contents, ContentsCommand.PricingModify command);
}
