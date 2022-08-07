package com.lezhin.member.domain.service;

import com.lezhin.member.domain.Member;
import reactor.core.publisher.Mono;

public interface MemberReader {

    Mono<Member> findByMemberToken(String memberToken);

    Mono<Member> findByMemberId(long memberId);
}
