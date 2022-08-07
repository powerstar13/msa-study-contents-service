package com.lezhin.member.domain.service;

import com.lezhin.member.domain.Member;
import reactor.core.publisher.Mono;

public interface MemberStore {

    Mono<Void> memberDelete(Member member);
}
