package com.lezhin.member.infrastructure.dao;

import com.lezhin.member.domain.Member;
import com.lezhin.member.domain.service.MemberStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {

    private final MemberRepository memberRepository;

    /**
     * 회원 삭제 처리
     * @param member: 회원 레퍼런스
     */
    @Override
    public Mono<Void> memberDelete(Member member) {
        return memberRepository.delete(member);
    }
}
