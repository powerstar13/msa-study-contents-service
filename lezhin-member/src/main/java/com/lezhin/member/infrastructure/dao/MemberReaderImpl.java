package com.lezhin.member.infrastructure.dao;

import com.lezhin.member.domain.Member;
import com.lezhin.member.domain.service.MemberReader;
import com.lezhin.member.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.member.infrastructure.exception.status.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;

    /**
     * 회원 대체 식별키로 회원 정보 조회
     * @param memberToken: 회원 대체 식별키
     * @return Member: 회원 레퍼런스
     */
    @Override
    public Mono<Member> findByMemberToken(String memberToken) {
        return memberRepository.findByMemberToken(memberToken)
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundMember.getMessage())));
    }

    /**
     * 회원 고유번호로 회원 정보 조회
     * @param memberId: 회원 고유번호
     * @return Member: 회원 레퍼런스
     */
    @Override
    public Mono<Member> findByMemberId(long memberId) {
        return memberRepository.findByMemberId(memberId)
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundMember.getMessage())));
    }
}
