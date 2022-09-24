package com.webtoon.contents.domain.service;

import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.domain.Contents;
import com.webtoon.contents.domain.Evaluation;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContentsReader {

    Mono<Contents> findByContentsToken(String contentsToken);

    Mono<Void> evaluationExistCheck(ContentsCommand.ExchangedContentsIdForEvaluationRegister command);

    Mono<ContentsDTO.EvaluationTop3Contents> evaluationTop3Contents();

    Flux<Evaluation> findEvaluationListByMemberId(long memberId);

    Mono<Contents> findContentsByContentsId(long contentsId);
}
