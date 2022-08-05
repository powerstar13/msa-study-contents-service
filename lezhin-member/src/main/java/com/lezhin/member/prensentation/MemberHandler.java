package com.lezhin.member.prensentation;

import com.lezhin.member.applicaiton.MemberFacade;
import com.lezhin.member.infrastructure.exception.status.BadRequestException;
import com.lezhin.member.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.member.prensentation.response.ExchangeMemberTokenResponse;
import com.lezhin.member.prensentation.response.MemberResponseMapper;
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
}
