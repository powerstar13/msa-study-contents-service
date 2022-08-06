package com.lezhin.member.domain.service;

import com.lezhin.member.domain.dto.MemberDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.lezhin.member.infrastructure.factory.MemberTestFactory.memberIdInfoMono;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberReader memberReader;

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {

        given(memberReader.exchangeMemberToken(any(String.class))).willReturn(memberIdInfoMono());

        Mono<MemberDTO.MemberIdInfo> result = memberService.exchangeMemberToken(UUID.randomUUID().toString());

        verify(memberReader).exchangeMemberToken(any(String.class));

        StepVerifier.create(result.log())
            .assertNext(memberIdInfo -> assertTrue(memberIdInfo.getMemberId() > 0))
            .verifyComplete();
    }
}