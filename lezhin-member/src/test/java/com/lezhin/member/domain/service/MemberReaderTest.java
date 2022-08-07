package com.lezhin.member.domain.service;

import com.lezhin.member.domain.Member;
import com.lezhin.member.infrastructure.dao.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.lezhin.member.infrastructure.factory.MemberTestFactory.memberMono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MemberReaderTest {

    @Autowired
    private MemberReader memberReader;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("회원 대체 식별키로 회원 정보 조회")
    @Test
    void findByMemberToken() {

        given(memberRepository.findByMemberToken(any(String.class))).willReturn(memberMono());

        Mono<Member> result = memberReader.findByMemberToken(UUID.randomUUID().toString());

        verify(memberRepository).findByMemberToken(any(String.class));

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }
}