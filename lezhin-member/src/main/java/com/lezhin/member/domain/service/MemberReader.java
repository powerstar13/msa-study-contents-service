package com.lezhin.member.domain.service;

import com.lezhin.member.domain.dto.MemberDTO;
import reactor.core.publisher.Mono;

public interface MemberReader {

    Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken);
}
