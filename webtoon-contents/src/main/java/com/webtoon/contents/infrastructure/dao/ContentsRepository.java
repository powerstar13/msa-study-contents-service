package com.webtoon.contents.infrastructure.dao;

import com.webtoon.contents.domain.Contents;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ContentsRepository extends ReactiveCrudRepository<Contents, Long> {

    Mono<Contents> findByContentsToken(String contentsToken);

    Flux<Contents> findTop3ByLikeCountGreaterThanOrderByLikeCountDesc(long likeCount);
    Flux<Contents> findTop3ByDislikeCountGreaterThanOrderByDislikeCountDesc(long dislikeCount);

    Mono<Contents> findByContentsId(long contentsId);
}
