package com.webtoon.contents.domain.service;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.domain.Contents;
import com.webtoon.contents.domain.Evaluation;
import reactor.core.publisher.Mono;

public interface ContentsStore {

    Mono<Evaluation> evaluationRegister(Contents contents, ContentsCommand.ExchangedContentsIdForEvaluationRegister command);

    Mono<Void> pricingModify(Contents contents, ContentsCommand.PricingModify command);

    Mono<Void> evaluationDelete(Contents contents, Evaluation evaluation);
}
