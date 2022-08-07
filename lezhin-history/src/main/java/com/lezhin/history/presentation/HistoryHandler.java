package com.lezhin.history.presentation;

import com.lezhin.history.applicaiton.HistoryFacade;
import com.lezhin.history.presentation.request.ContentsHistoryPageRequest;
import com.lezhin.history.presentation.request.HistoryRequestMapper;
import com.lezhin.history.presentation.response.ContentsHistoryPageResponse;
import com.lezhin.history.presentation.response.HistoryResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class HistoryHandler {

    private final HistoryFacade historyFacade;
    private final HistoryRequestMapper historyRequestMapper;
    private final HistoryResponseMapper historyResponseMapper;

    /**
     * 작품별 조회 이력 페이지
     * @param serverRequest: 조회할 정보
     * @return ServerResponse: 이력 페이지
     */
    public Mono<ServerResponse> contentsHistoryPage(ServerRequest serverRequest) {

        ContentsHistoryPageRequest request = historyRequestMapper.of(serverRequest.queryParams().toSingleValueMap());
        request.verify(); // Request 유효성 검사

        Mono<ContentsHistoryPageResponse> response = historyFacade.contentsHistoryPage(historyRequestMapper.of(request))
            .flatMap(contentsInfo -> Mono.just(historyResponseMapper.of(contentsInfo)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, ContentsHistoryPageResponse.class);
    }
}
