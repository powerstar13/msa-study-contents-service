package com.lezhin.contents.presentation;

import com.lezhin.contents.application.ContentsFacade;
import com.lezhin.contents.infrastructure.exception.status.BadRequestException;
import com.lezhin.contents.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.contents.presentation.request.ContentsRequestMapper;
import com.lezhin.contents.presentation.request.EvaluationRegisterRequest;
import com.lezhin.contents.presentation.response.ContentsResponseMapper;
import com.lezhin.contents.presentation.response.EvaluationRegisterResponse;
import lombok.RequiredArgsConstructor;
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
}
