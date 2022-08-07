package com.lezhin.member.infrastructure.webClient;

import com.lezhin.member.infrastructure.exception.status.BadRequestException;
import com.lezhin.member.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.member.infrastructure.webClient.response.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ContentsWebClientService {

    private final WebClient webClient;

    public ContentsWebClientService(@Value("${msa.client-url.contents}") String contentsUrl) {
        this.webClient = WebClient.builder().baseUrl(contentsUrl).build();
    }

    /**
     * 평가 삭제
     * @param memberId : 회원 대체 식별키
     * @return CommonResponse : 처리 결과
     */
    public Mono<CommonResponse> evaluationDeleteByMemberDelete(long memberId) {

        return webClient.get()
            .uri("/evaluation-delete/by-member-delete/" + memberId)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError,
                response -> Mono.error(new RuntimeException(ExceptionMessage.ServerError.getMessage()))
            )
            .onStatus(HttpStatus::is4xxClientError,
                response -> response.bodyToMono(String.class)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(CommonResponse.class);
    }
}
