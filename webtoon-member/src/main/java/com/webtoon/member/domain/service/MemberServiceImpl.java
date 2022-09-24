package com.webtoon.member.domain.service;

import com.webtoon.member.domain.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;

    /**
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {

        return memberReader.findByMemberToken(memberToken) // 회원 정보 조회
            .map(member -> new MemberDTO.MemberIdInfo(member.getMemberId()));
    }

    /**
     * 회원 삭제 처리
     * @param memberToken: 회원 대체 식별키
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> memberDelete(String memberToken) {

        return memberReader.findByMemberToken(memberToken) // 1. 회원 정보 조회
            .flatMap(memberStore::memberDelete); // 2. 회원 삭제
    }
}
