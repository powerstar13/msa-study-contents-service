package com.lezhin.member.applicaiton;

import com.lezhin.member.domain.dto.MemberDTO;
import com.lezhin.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    
    private final MemberService memberService;

    /**
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {
        return memberService.exchangeMemberToken(memberToken); // 회원 고유번호 가져오기 처리
    }
}
