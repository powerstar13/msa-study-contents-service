package com.lezhin.contents.presentation;

import com.lezhin.contents.application.ContentsFacade;
import com.lezhin.contents.infrastructure.exception.status.BadRequestException;
import com.lezhin.contents.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.contents.presentation.request.ContentsRequestMapper;
import com.lezhin.contents.presentation.request.EvaluationRegisterRequest;
import com.lezhin.contents.presentation.request.PricingModifyRequest;
import com.lezhin.contents.presentation.response.ContentsResponseMapper;
import com.lezhin.contents.presentation.response.EvaluationRegisterResponse;
import com.lezhin.contents.presentation.response.EvaluationTop3ContentsResponse;
import com.lezhin.contents.presentation.response.ExchangeContentsTokenResponse;
import com.lezhin.contents.presentation.shared.response.SuccessResponse;
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
public class ContentsHandler {

    private final ContentsFacade contentsFacade;
    private final ContentsRequestMapper contentsRequestMapper;
    private final ContentsResponseMapper contentsResponseMapper;

    /**
     * 평가 등록
     * @param serverRequest: 등록할 평가 정보
     * @return ServerResponse: 평가 대체 식별키 정보
     */
    public Mono<ServerResponse> evaluationRegister(ServerRequest serverRequest) {

        Mono<EvaluationRegisterResponse> response = serverRequest.bodyToMono(EvaluationRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return contentsFacade.evaluationRegister(contentsRequestMapper.of(request));
            })
            .flatMap(evaluationTokenInfo -> Mono.just(contentsResponseMapper.of(evaluationTokenInfo)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, EvaluationRegisterResponse.class);
    }

    /**
     * 좋아요/싫어요 Top3 작품 조회
     * @return ServerResponse: 좋아요/싫어요 Top3 작품 정보
     */
    public Mono<ServerResponse> evaluationTop3Contents(ServerRequest serverRequest) {

        Mono<EvaluationTop3ContentsResponse> response = contentsFacade.evaluationTop3Contents()
            .flatMap(evaluationTop3Contents -> Mono.just(contentsResponseMapper.of(evaluationTop3Contents)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, EvaluationTop3ContentsResponse.class);
    }

    /**
     * 작품 고유번호 가져오기
     * @param serverRequest: 작품 대체 식별키
     * @return ExchangeContentsTokenResponse: 작품 고유번호
     */
    public Mono<ServerResponse> exchangeContentsToken(ServerRequest serverRequest) {

        String contentsToken = serverRequest.pathVariable("contentsToken"); // 작품 대체 식별키 추출
        if (StringUtils.isBlank(contentsToken)) throw new BadRequestException(ExceptionMessage.IsRequiredContentsToken.getMessage());

        Mono<ExchangeContentsTokenResponse> response = contentsFacade.exchangeContentsToken(contentsToken)
            .flatMap(contentsIdInfo -> Mono.just(contentsResponseMapper.of(contentsIdInfo)));

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, ExchangeContentsTokenResponse.class);
    }

    /**
     * 가격 변경
     * @param serverRequest: 변경할 가격 정보
     * @return ServerResponse: 처리 완료
     */
    public Mono<ServerResponse> pricingModify(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(PricingModifyRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return contentsFacade.pricingModify(contentsRequestMapper.of(request))
                    .then(
                        ok().contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(new SuccessResponse()), SuccessResponse.class)
                    );
            });
    }

    /**
     * 평가 삭제
     * @param serverRequest: 회원 대체 식별키
     * @return ServerResponse: 처리 완료
     */
    public Mono<ServerResponse> evaluationDeleteByMember(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        return contentsFacade.evaluationDeleteByMember(memberToken)
            .then(
                ok().contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new SuccessResponse()), SuccessResponse.class)
            );
    }
}
