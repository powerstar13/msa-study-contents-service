package com.webtoon.member.infrastructure.factory;

import com.webtoon.member.domain.Member;
import com.webtoon.member.domain.MemberGender;
import com.webtoon.member.domain.MemberType;
import com.webtoon.member.domain.dto.MemberDTO;
import com.webtoon.member.infrastructure.webClient.response.CommonResponse;
import com.webtoon.member.presentation.response.ExchangeMemberTokenResponse;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public class MemberTestFactory {

    private static final MemberType memberType = MemberType.NORMAL;
    private static final String memberEmail = "test@gmail.com";
    private static final String memberName = "이름";
    private static final MemberGender memberGender = MemberGender.M;
    private static final String memberToken = UUID.randomUUID().toString();

    /**
     * 회원 정보
     */
    public static Member member() {

        return Member.builder()
            .memberId(RandomUtils.nextLong())
            .memberToken(memberToken)
            .memberType(memberType)
            .memberEmail(memberEmail)
            .memberName(memberName)
            .memberGender(memberGender)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Mono<Member> memberMono() {
        return Mono.just(member());
    }

    /**
     * 회원 고유번호 정보
     */
    public static MemberDTO.MemberIdInfo memberIdInfo() {

        return MemberDTO.MemberIdInfo.builder()
            .memberId(RandomUtils.nextLong())
            .build();
    }

    public static Mono<MemberDTO.MemberIdInfo> memberIdInfoMono() {
        return Mono.just(memberIdInfo());
    }

    public static ExchangeMemberTokenResponse exchangeMemberTokenResponse() {

        return ExchangeMemberTokenResponse.builder()
            .memberId(RandomUtils.nextLong())
            .build();
    }

    /**
     * 통신 결과
     */
    public static CommonResponse commonResponse() {

        return CommonResponse.builder()
            .rt(HttpStatus.OK.value())
            .rtMsg(HttpStatus.OK.getReasonPhrase())
            .build();
    }

    public static Mono<CommonResponse> commonResponseMono() {
        return Mono.just(commonResponse());
    }
}
