package com.webtoon.history.infrastructure.dao;

import com.webtoon.history.domain.History;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface HistoryRepository extends ReactiveCrudRepository<History, Long>, HistoryCustomRepository {

    Flux<History> findAllByContentsId(long contentsId, Pageable pageable);

    Mono<Long> countByContentsId(long contentsId);

    Flux<History> findAllByMemberId(long memberId);
}
