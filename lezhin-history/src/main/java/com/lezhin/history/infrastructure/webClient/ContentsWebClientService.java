package com.lezhin.history.infrastructure.webClient;

import com.lezhin.history.infrastructure.exception.status.BadRequestException;
import com.lezhin.history.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.history.infrastructure.webClient.response.ExchangeContentsTokenResponse;
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
     * 작품 고유번호 가져오기
     * @param contentsToken : 작품 대체 식별키
     * @return ExchangeContentsTokenResponse : 작품 고유번호
     */
    public Mono<ExchangeContentsTokenResponse> exchangeContentsToken(String contentsToken) {

        return webClient.get()
            .uri("/exchange/contents-token/" + contentsToken)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError,
                response -> Mono.error(new RuntimeException(ExceptionMessage.ServerError.getMessage()))
            )
            .onStatus(HttpStatus::is4xxClientError,
                response -> response.bodyToMono(String.class)
                    .flatMap(message -> Mono.error(new BadRequestException(message)))
            )
            .bodyToMono(ExchangeContentsTokenResponse.class);
    }
}
