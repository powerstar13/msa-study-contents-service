package com.lezhin.member.applicaiton;

import com.lezhin.member.domain.dto.MemberDTO;
import com.lezhin.member.domain.service.MemberService;
import com.lezhin.member.infrastructure.webClient.ContentsWebClientService;
import com.lezhin.member.infrastructure.webClient.HistoryWebClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.lezhin.member.infrastructure.factory.MemberTestFactory.commonResponseMono;
import static com.lezhin.member.infrastructure.factory.MemberTestFactory.memberIdInfoMono;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MemberFacadeTest {

    @Autowired
    private MemberFacade memberFacade;

    @MockBean
    private MemberService memberService;
    @MockBean
    private ContentsWebClientService contentsWebClientService;
    @MockBean
    private HistoryWebClientService historyWebClientService;

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {

        given(memberService.exchangeMemberToken(any(String.class))).willReturn(memberIdInfoMono());

        Mono<MemberDTO.MemberIdInfo> result = memberFacade.exchangeMemberToken(UUID.randomUUID().toString());

        verify(memberService).exchangeMemberToken(any(String.class));

        StepVerifier.create(result.log())
            .assertNext(memberIdInfo -> assertTrue(memberIdInfo.getMemberId() > 0))
            .verifyComplete();
    }

    @DisplayName("회원 삭제")
    @Test
    void memberDelete() {

        given(memberService.exchangeMemberToken(any(String.class))).willReturn(memberIdInfoMono());
        given(contentsWebClientService.evaluationDeleteByMemberDelete(anyLong())).willReturn(commonResponseMono());
        given(historyWebClientService.historyDeleteByMemberDelete(anyLong())).willReturn(commonResponseMono());
        given(memberService.memberDelete(anyLong())).willReturn(Mono.empty());

        Mono<Void> result = memberFacade.memberDelete(UUID.randomUUID().toString());

        verify(memberService).exchangeMemberToken(any(String.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}