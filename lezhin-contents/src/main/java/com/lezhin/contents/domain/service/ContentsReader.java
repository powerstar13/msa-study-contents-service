package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.Contents;
import reactor.core.publisher.Mono;

public interface ContentsReader {

    Mono<Contents> findByContentsToken(String contentsToken);

    Mono<Void> evaluationExistCheck(ContentsCommand.ExchangedContentsIdForEvaluationRegister command);
}
