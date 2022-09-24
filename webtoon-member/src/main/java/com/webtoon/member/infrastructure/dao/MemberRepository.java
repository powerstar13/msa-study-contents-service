package com.webtoon.member.infrastructure.dao;

import com.webtoon.member.domain.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

    Mono<Member> findByMemberToken(String memberToken);

    Mono<Member> findByMemberId(long memberId);
}
