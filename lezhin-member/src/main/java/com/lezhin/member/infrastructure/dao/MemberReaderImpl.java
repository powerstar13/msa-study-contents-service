package com.lezhin.member.infrastructure.dao;

import com.lezhin.member.domain.dto.MemberDTO;
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
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    @Override
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {

        return memberRepository.findByMemberToken(memberToken)
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundMember.getMessage())))
            .flatMap(member -> Mono.just(new MemberDTO.MemberIdInfo(member.getMemberId())));
    }
}
