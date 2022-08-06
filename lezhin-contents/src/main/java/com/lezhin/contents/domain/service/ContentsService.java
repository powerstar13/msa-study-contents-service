package com.lezhin.contents.domain.service;

import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import reactor.core.publisher.Mono;

public interface ContentsService {

    Mono<ContentsDTO.EvaluationTokenInfo> evaluationRegister(ContentsCommand.ExchangedMemberIdForEvaluationRegister command);
}