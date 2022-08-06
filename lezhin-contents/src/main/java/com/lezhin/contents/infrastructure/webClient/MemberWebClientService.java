package com.lezhin.contents.infrastructure.webClient;

import com.lezhin.contents.infrastructure.exception.status.BadRequestException;
import com.lezhin.contents.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.contents.infrastructure.webClient.response.ExchangeMemberTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MemberWebClientService {

    private final WebClient webClient;

    public MemberWebClientService(@Value("${msa.client-url.member}") String memberUrl) {
        this.webClient = WebClient.builder().baseUrl(memberUrl).build();
    }

    /**
     * 회원 고유번호 가져오기
     * @param memberToken : 회원 대체 식별키
     * @return GetMemberIdResponse : 회원 고유번호
     */
    public Mono<ExchangeMemberTokenResponse> exchangeMemberToken(String memberToken) {

        return webClient.get()
            .uri("/exchange/member-token/" + memberToken)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError,
                response -> Mono.error(new RuntimeException(ExceptionMessage.ServerError.getMessage()))
            )
            .onStatus(HttpStatus::is4xxClientError,
                response -> response.bodyToMono(String.class)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(ExchangeMemberTokenResponse.class);
    }
}
