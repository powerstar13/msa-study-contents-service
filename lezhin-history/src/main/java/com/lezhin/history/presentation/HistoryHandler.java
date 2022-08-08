package com.lezhin.history.presentation;

import com.lezhin.history.applicaiton.HistoryFacade;
import com.lezhin.history.infrastructure.exception.status.BadRequestException;
import com.lezhin.history.infrastructure.exception.status.ExceptionMessage;
import com.lezhin.history.presentation.request.ContentsHistoryPageRequest;
import com.lezhin.history.presentation.request.ContentsHistoryRequestMapper;
import com.lezhin.history.presentation.request.SearchHistoryPageRequest;
import com.lezhin.history.presentation.request.SearchHistoryRequestMapper;
import com.lezhin.history.presentation.response.ContentsHistoryPageResponse;
import com.lezhin.history.presentation.response.HistoryResponseMapper;
import com.lezhin.history.presentation.response.SearchHistoryPageResponse;
import com.lezhin.history.presentation.shared.response.SuccessResponse;
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
public class HistoryHandler {

    private final HistoryFacade historyFacade;
    private final ContentsHistoryRequestMapper contentsHistoryRequestMapper;
    private final SearchHistoryRequestMapper searchHistoryRequestMapper;
    private final HistoryResponseMapper historyResponseMapper;

    /**
     * 작품별 조회 이력 페이지
     * @param serverRequest: 조회할 정보
     * @return ServerResponse: 이력 페이지
     */
    public Mono<ServerResponse> contentsHistoryPage(ServerRequest serverRequest) {

        ContentsHistoryPageRequest request = contentsHistoryRequestMapper.of(serverRequest.queryParams().toSingleValueMap());
        request.verify(); // Request 유효성 검사

        Mono<ContentsHistoryPageResponse> response = historyFacade.contentsHistoryPage(contentsHistoryRequestMapper.of(request))
            .flatMap(contentsHistoryPage -> Mono.just(historyResponseMapper.of(contentsHistoryPage)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, ContentsHistoryPageResponse.class);
    }

    /**
     * 사용자 조회 이력 페이지
     * @return ServerResponse: 이력 페이지
     */
    public Mono<ServerResponse> searchHistoryPage(ServerRequest serverRequest) {

        SearchHistoryPageRequest request = searchHistoryRequestMapper.of(serverRequest.queryParams().toSingleValueMap());
        request.verify(); // Request 유효성 검사

        Mono<SearchHistoryPageResponse> response = historyFacade.searchHistoryPage(searchHistoryRequestMapper.of(request))
            .flatMap(searchHistoryPage -> Mono.just(historyResponseMapper.of(searchHistoryPage)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, SearchHistoryPageResponse.class);
    }

    /**
     * 이력 삭제
     * @param serverRequest: 회원 대체 식별키
     * @return ServerResponse: 처리 완료
     */
    public Mono<ServerResponse> historyDeleteByMember(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        return historyFacade.historyDeleteByMember(memberToken)
            .then(
                ok().contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new SuccessResponse()), SuccessResponse.class)
            );
    }
}
