package com.lezhin.member.domain.service;

import com.lezhin.member.domain.dto.MemberDTO;
import reactor.core.publisher.Mono;

public interface MemberService {

    Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken);

    Mono<Void> memberDelete(long memberId);
}
