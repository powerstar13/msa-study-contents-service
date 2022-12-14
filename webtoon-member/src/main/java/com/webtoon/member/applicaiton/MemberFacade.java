package com.webtoon.member.applicaiton;

import com.webtoon.member.domain.dto.MemberDTO;
import com.webtoon.member.domain.service.MemberService;
import com.webtoon.member.infrastructure.exception.status.BadRequestException;
import com.webtoon.member.infrastructure.webClient.ContentsWebClientService;
import com.webtoon.member.infrastructure.webClient.HistoryWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;
    private final ContentsWebClientService contentsWebClientService;
    private final HistoryWebClientService historyWebClientService;

    /**
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {
        return memberService.exchangeMemberToken(memberToken); // 회원 고유번호 가져오기 처리
    }

    /**
     * 회원 삭제
     * @param memberToken: 삭제할 회원 대체 식별키
     */
    public Mono<Void> memberDelete(String memberToken) {

        return contentsWebClientService.evaluationDeleteByMember(memberToken) // 1. 평가 삭제 처리
            .zipWith(historyWebClientService.historyDeleteByMember(memberToken)) // 2. 이력 삭제 처리
            .flatMap(objects -> {
                if (objects.getT1().getRt() != 200) return Mono.error(new BadRequestException(objects.getT1().getRtMsg()));
                if (objects.getT2().getRt() != 200) return Mono.error(new BadRequestException(objects.getT2().getRtMsg()));

                return memberService.memberDelete(memberToken); // 3. 회원 삭제 처리
            });
    }
}
