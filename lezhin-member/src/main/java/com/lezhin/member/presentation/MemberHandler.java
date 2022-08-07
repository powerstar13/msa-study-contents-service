package com.lezhin.member.presentation;

import com.lezhin.member.applicaiton.MemberFacade;
import com.lezhin.member.infrastructure.exception.status.BadRequestException;
import com.lezhin.member.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.member.presentation.response.ExchangeMemberTokenResponse;
import com.lezhin.member.presentation.response.MemberResponseMapper;
import com.lezhin.member.presentation.shared.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class MemberHandler {

    private final MemberFacade memberFacade;
    private final MemberResponseMapper memberResponseMapper;

    /**
     * 회원 고유번호 가져오기
     * @param serverRequest: 회원 대체 식별키
     * @return ExchangeMemberTokenResponse: 회원 고유번호
     */
    public Mono<ServerResponse> exchangeMemberToken(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        Mono<ExchangeMemberTokenResponse> response = memberFacade.exchangeMemberToken(memberToken)
            .flatMap(memberIdInfo -> Mono.just(memberResponseMapper.of(memberIdInfo)));

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, ExchangeMemberTokenResponse.class);
    }

    /**
     * 회원 삭제
     * @param serverRequest: 삭제할 회원 대체 식별키
     * @return ServerResponse: 처리 완료
     */
    public Mono<ServerResponse> memberDelete(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        return memberFacade.memberDelete(memberToken)
            .then(
                ok().contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new SuccessResponse()), SuccessResponse.class)
            );
    }
}
