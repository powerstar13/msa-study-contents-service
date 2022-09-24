package com.webtoon.contents.domain.service;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import reactor.core.publisher.Mono;

public interface ContentsService {

    Mono<ContentsDTO.EvaluationTokenInfo> evaluationRegister(ContentsCommand.ExchangedMemberIdForEvaluationRegister command);

    Mono<ContentsDTO.EvaluationTop3Contents> evaluationTop3Contents();

    Mono<ContentsDTO.ContentsIdInfo> exchangeContentsToken(String contentsToken);

    Mono<Void> pricingModify(ContentsCommand.PricingModify command);

    Mono<Void> evaluationDeleteByMember(long memberId);
}
