package com.webtoon.history.presentation;

import com.webtoon.history.applicaiton.HistoryFacade;
import com.webtoon.history.infrastructure.exception.status.BadRequestException;
import com.webtoon.history.infrastructure.exception.status.ExceptionMessage;
import com.webtoon.history.presentation.request.ContentsHistoryPageRequest;
import com.webtoon.history.presentation.request.ContentsHistoryRequestMapper;
import com.webtoon.history.presentation.request.SearchHistoryPageRequest;
import com.webtoon.history.presentation.request.SearchHistoryRequestMapper;
import com.webtoon.history.presentation.response.HistoryResponseMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.webtoon.history.presentation.shared.response.ServerResponseFactory.successBodyValue;
import static com.webtoon.history.presentation.shared.response.ServerResponseFactory.successOnly;

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

        return historyFacade.contentsHistoryPage(contentsHistoryRequestMapper.of(request))
            .flatMap(response -> successBodyValue(historyResponseMapper.of(response)));
    }

    /**
     * 사용자 조회 이력 페이지
     * @return ServerResponse: 이력 페이지
     */
    public Mono<ServerResponse> searchHistoryPage(ServerRequest serverRequest) {

        SearchHistoryPageRequest request = searchHistoryRequestMapper.of(serverRequest.queryParams().toSingleValueMap());
        request.verify(); // Request 유효성 검사

        return historyFacade.searchHistoryPage(searchHistoryRequestMapper.of(request))
            .flatMap(response -> successBodyValue(historyResponseMapper.of(response)));
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
            .then(successOnly());
    }
}
