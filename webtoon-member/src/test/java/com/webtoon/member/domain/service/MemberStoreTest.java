package com.webtoon.member.domain.service;

import com.webtoon.member.domain.Member;
import com.webtoon.member.infrastructure.dao.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.webtoon.member.infrastructure.factory.MemberTestFactory.member;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MemberStoreTest {

    @Autowired
    private MemberStore memberStore;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("회원 정보 삭제")
    @Test
    void memberDelete() {

        given(memberRepository.delete(any(Member.class))).willReturn(Mono.empty());

        Mono<Void> result = memberStore.memberDelete(member());

        verify(memberRepository).delete(any(Member.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}