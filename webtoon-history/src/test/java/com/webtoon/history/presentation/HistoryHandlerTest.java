package com.webtoon.history.presentation;

import com.webtoon.history.applicaiton.HistoryFacade;
import com.webtoon.history.applicaiton.dto.HistoryCommand;
import com.webtoon.history.domain.AdultOnly;
import com.webtoon.history.domain.service.dto.HistoryDTO;
import com.webtoon.history.infrastructure.router.RouterPathPattern;
import com.webtoon.history.presentation.request.ContentsHistoryPageRequest;
import com.webtoon.history.presentation.request.ContentsHistoryRequestMapper;
import com.webtoon.history.presentation.request.SearchHistoryPageRequest;
import com.webtoon.history.presentation.request.SearchHistoryRequestMapper;
import com.webtoon.history.presentation.response.ContentsHistoryPageResponse;
import com.webtoon.history.presentation.response.HistoryResponseMapper;
import com.webtoon.history.presentation.response.SearchHistoryPageResponse;
import com.webtoon.history.presentation.shared.WebFluxSharedHandlerTest;
import com.webtoon.history.presentation.shared.response.SuccessResponse;
import com.webtoon.history.infrastructure.factory.HistoryTestFactory;
import com.webtoon.history.infrastructure.restdocs.RestdocsDocumentFormat;
import com.webtoon.history.infrastructure.restdocs.RestdocsDocumentUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(HistoryHandler.class)
class HistoryHandlerTest extends WebFluxSharedHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private HistoryFacade historyFacade;
    @MockBean
    private ContentsHistoryRequestMapper contentsHistoryRequestMapper;
    @MockBean
    private SearchHistoryRequestMapper searchHistoryRequestMapper;
    @MockBean
    private HistoryResponseMapper historyResponseMapper;

    @DisplayName("작품별 조회 이력 페이지")
    @Test
    void contentsHistoryPage() {
        // given
        given(contentsHistoryRequestMapper.of(anyMap())).willReturn(HistoryTestFactory.contentsHistoryPageRequest());
        given(contentsHistoryRequestMapper.of(any(ContentsHistoryPageRequest.class))).willReturn(HistoryTestFactory.contentsHistoryPageCommand());
        given(historyFacade.contentsHistoryPage(any(HistoryCommand.ContentsHistoryPage.class))).willReturn(HistoryTestFactory.contentsHistoryPageDTOMono());
        given(historyResponseMapper.of(any(HistoryDTO.ContentsHistoryPage.class))).willReturn(HistoryTestFactory.contentsHistoryPageResponse());

        // when
        final String URI = RouterPathPattern.CONTENTS_HISTORY_PAGE.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(uriBuilder ->
                uriBuilder.path(URI)
                    .queryParam("page", 1)
                    .queryParam("size", 10)
                    .queryParam("contentsToken", UUID.randomUUID().toString())
                    .build()
            )
            .header(AUTHORIZATION, "accessToken")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(WebTestClientRestDocumentation.document(URI,
                RestdocsDocumentUtil.requestPrettyPrint(),
                RestdocsDocumentUtil.responsePrettyPrint(),
                requestParameters(
                    parameterWithName("page").description("요청 페이지").optional(),
                    parameterWithName("size").description("한 페이지 당 보여질 개수").optional(),
                    parameterWithName("contentsToken").description("작품 대체 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                    fieldWithPath("pageInfo.currentSize").type(JsonFieldType.NUMBER).description("현재 페이지의 데이터 수"),
                    fieldWithPath("pageInfo.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                    fieldWithPath("pageInfo.totalCount").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                    fieldWithPath("pageInfo.totalPage").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                    fieldWithPath("historyList[]").type(JsonFieldType.ARRAY).description("이력 목록").optional(),
                    fieldWithPath("historyList[].historyToken").type(JsonFieldType.STRING).description("이력 대체 식별키"),
                    fieldWithPath("historyList[].memberType").type(JsonFieldType.STRING).description("회원 유형").attributes(RestdocsDocumentFormat.memberTypeFormat()),
                    fieldWithPath("historyList[].memberEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("historyList[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("historyList[].memberGender").type(JsonFieldType.STRING).description("회원 성별").attributes(RestdocsDocumentFormat.memberGenderFormat()),
                    fieldWithPath("historyList[].createdAt").type(JsonFieldType.STRING).description("생성일").attributes(RestdocsDocumentFormat.yyyyMMddHHmmssFormat())
                )
            ));

        FluxExchangeResult<ContentsHistoryPageResponse> flux = result.returnResult(ContentsHistoryPageResponse.class);

        // then
        verify(contentsHistoryRequestMapper).of(anyMap());
        verify(contentsHistoryRequestMapper).of(any(ContentsHistoryPageRequest.class));
        verify(historyFacade).contentsHistoryPage(any(HistoryCommand.ContentsHistoryPage.class));
        verify(historyResponseMapper).of(any(HistoryDTO.ContentsHistoryPage.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertNotNull(response.getHistoryList());
            }))
            .verifyComplete();
    }

    @DisplayName("사용자 조회 이력 페이지")
    @Test
    void searchHistoryPage() {
        // given
        given(searchHistoryRequestMapper.of(anyMap())).willReturn(HistoryTestFactory.searchHistoryPageRequest());
        given(searchHistoryRequestMapper.of(any(SearchHistoryPageRequest.class))).willReturn(HistoryTestFactory.searchHistoryPageCommand());
        given(historyFacade.searchHistoryPage(any(HistoryCommand.SearchHistoryPage.class))).willReturn(HistoryTestFactory.searchHistoryPageDTOMono());
        given(historyResponseMapper.of(any(HistoryDTO.SearchHistoryPage.class))).willReturn(HistoryTestFactory.searchHistoryPageResponse());

        // when
        final String URI = RouterPathPattern.SEARCH_HISTORY_PAGE.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(uriBuilder ->
                uriBuilder.path(URI)
                    .queryParam("page", 1)
                    .queryParam("size", 10)
                    .queryParam("weekInterval", 1)
                    .queryParam("adultOnly", AdultOnly.Y)
                    .queryParam("historyCount", 3)
                    .build()
            )
            .header(AUTHORIZATION, "accessToken")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(WebTestClientRestDocumentation.document(URI,
                RestdocsDocumentUtil.requestPrettyPrint(),
                RestdocsDocumentUtil.responsePrettyPrint(),
                requestParameters(
                    parameterWithName("page").description("요청 페이지").optional(),
                    parameterWithName("size").description("한 페이지 당 보여질 개수").optional(),
                    parameterWithName("weekInterval").description("최근으로부터 주 간격").optional(),
                    parameterWithName("adultOnly").description("성인물 여부").attributes(RestdocsDocumentFormat.adultOnlyFormat()).optional(),
                    parameterWithName("historyCount").description("조회 수").optional()
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                    fieldWithPath("pageInfo.currentSize").type(JsonFieldType.NUMBER).description("현재 페이지의 데이터 수"),
                    fieldWithPath("pageInfo.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                    fieldWithPath("pageInfo.totalCount").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                    fieldWithPath("pageInfo.totalPage").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                    fieldWithPath("memberList[]").type(JsonFieldType.ARRAY).description("회원 목록").optional(),
                    fieldWithPath("memberList[].memberType").type(JsonFieldType.STRING).description("회원 유형").attributes(RestdocsDocumentFormat.memberTypeFormat()),
                    fieldWithPath("memberList[].memberEmail").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("memberList[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("memberList[].memberGender").type(JsonFieldType.STRING).description("회원 성별").attributes(RestdocsDocumentFormat.memberGenderFormat())
                )
            ));

        FluxExchangeResult<SearchHistoryPageResponse> flux = result.returnResult(SearchHistoryPageResponse.class);

        // then
        verify(searchHistoryRequestMapper).of(anyMap());
        verify(searchHistoryRequestMapper).of(any(SearchHistoryPageRequest.class));
        verify(historyFacade).searchHistoryPage(any(HistoryCommand.SearchHistoryPage.class));
        verify(historyResponseMapper).of(any(HistoryDTO.SearchHistoryPage.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertNotNull(response.getMemberList());
            }))
            .verifyComplete();
    }

    @DisplayName("이력 삭제")
    @Test
    void historyDeleteByMember() {
        // given
        given(historyFacade.historyDeleteByMember(anyString())).willReturn(Mono.empty());

        // when
        final String URI = RouterPathPattern.DELETE_HISTORY_BY_MEMBER.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .delete()
            .uri(URI, UUID.randomUUID().toString())
            .header(AUTHORIZATION, "accessToken")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(WebTestClientRestDocumentation.document(URI,
                RestdocsDocumentUtil.requestPrettyPrint(),
                RestdocsDocumentUtil.responsePrettyPrint(),
                pathParameters(
                    parameterWithName("memberToken").description("회원 대체 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지")
                )
            ));

        FluxExchangeResult<SuccessResponse> flux = result.returnResult(SuccessResponse.class);

        // then
        verify(historyFacade).historyDeleteByMember(anyString());

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }
}