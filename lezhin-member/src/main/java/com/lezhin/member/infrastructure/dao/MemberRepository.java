package com.lezhin.member.infrastructure.dao;

import com.lezhin.member.domain.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

    Mono<Member> findByMemberToken(String memberToken);
}
