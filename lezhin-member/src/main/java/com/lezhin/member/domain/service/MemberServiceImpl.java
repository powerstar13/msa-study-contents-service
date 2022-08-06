package com.lezhin.member.domain.service;

import com.lezhin.member.domain.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;

    /**
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    @Override
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {

        return memberReader.exchangeMemberToken(memberToken);
    }
}
