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
public class HistoryWebClientService {

    private final WebClient webClient;

    public HistoryWebClientService(@Value("${msa.client-url.history}") String historyUrl) {
        this.webClient = WebClient.builder().baseUrl(historyUrl).build();
    }

    /**
     * 이력 삭제
     * @param memberToken : 회원 대체 식별키
     * @return CommonResponse : 처리 결과
     */
    public Mono<CommonResponse> historyDeleteByMember(String memberToken) {

        return webClient.get()
            .uri("/delete/history-by-member/" + memberToken)
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
