package com.webtoon.member.domain.service;

import com.webtoon.member.domain.dto.MemberDTO;
import reactor.core.publisher.Mono;

public interface MemberService {

    Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken);

    Mono<Void> memberDelete(String memberToken);
}
