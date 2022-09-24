package com.webtoon.member.domain.service;

import com.webtoon.member.domain.Member;
import reactor.core.publisher.Mono;

public interface MemberStore {

    Mono<Void> memberDelete(Member member);
}
