package com.webtoon.member.domain.service;

import com.webtoon.member.domain.Member;
import reactor.core.publisher.Mono;

public interface MemberReader {

    Mono<Member> findByMemberToken(String memberToken);
}
