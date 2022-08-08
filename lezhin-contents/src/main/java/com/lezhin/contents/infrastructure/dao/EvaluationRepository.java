package com.lezhin.contents.infrastructure.dao;

import com.lezhin.contents.domain.Evaluation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EvaluationRepository extends ReactiveCrudRepository<Evaluation, Long> {

    Mono<Evaluation> findByMemberIdAndContentsId(long memberId, long contentsId);

    Flux<Evaluation> findAllByMemberId(long memberId);
}
