package com.webtoon.member.presentation;

import com.webtoon.member.applicaiton.MemberFacade;
import com.webtoon.member.infrastructure.exception.status.BadRequestException;
import com.webtoon.member.infrastructure.exception.status.ExceptionMessage;
import com.webtoon.member.presentation.response.MemberResponseMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.webtoon.member.presentation.shared.response.ServerResponseFactory.successBodyValue;
import static com.webtoon.member.presentation.shared.response.ServerResponseFactory.successOnly;

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

        return memberFacade.exchangeMemberToken(memberToken)
            .flatMap(response -> successBodyValue(memberResponseMapper.of(response)));
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
            .then(successOnly());
    }
}
