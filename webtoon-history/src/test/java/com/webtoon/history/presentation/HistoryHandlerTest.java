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

    @DisplayName("????????? ?????? ?????? ?????????")
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
                    parameterWithName("page").description("?????? ?????????").optional(),
                    parameterWithName("size").description("??? ????????? ??? ????????? ??????").optional(),
                    parameterWithName("contentsToken").description("?????? ?????? ?????????")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ??????"),
                    fieldWithPath("pageInfo.currentSize").type(JsonFieldType.NUMBER).description("?????? ???????????? ????????? ???"),
                    fieldWithPath("pageInfo.currentPage").type(JsonFieldType.NUMBER).description("?????? ????????? ??????"),
                    fieldWithPath("pageInfo.totalCount").type(JsonFieldType.NUMBER).description("?????? ????????? ???"),
                    fieldWithPath("pageInfo.totalPage").type(JsonFieldType.NUMBER).description("??? ????????? ???"),
                    fieldWithPath("historyList[]").type(JsonFieldType.ARRAY).description("?????? ??????").optional(),
                    fieldWithPath("historyList[].historyToken").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                    fieldWithPath("historyList[].memberType").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.memberTypeFormat()),
                    fieldWithPath("historyList[].memberEmail").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("historyList[].memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("historyList[].memberGender").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.memberGenderFormat()),
                    fieldWithPath("historyList[].createdAt").type(JsonFieldType.STRING).description("?????????").attributes(RestdocsDocumentFormat.yyyyMMddHHmmssFormat())
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

    @DisplayName("????????? ?????? ?????? ?????????")
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
                    parameterWithName("page").description("?????? ?????????").optional(),
                    parameterWithName("size").description("??? ????????? ??? ????????? ??????").optional(),
                    parameterWithName("weekInterval").description("?????????????????? ??? ??????").optional(),
                    parameterWithName("adultOnly").description("????????? ??????").attributes(RestdocsDocumentFormat.adultOnlyFormat()).optional(),
                    parameterWithName("historyCount").description("?????? ???").optional()
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ??????"),
                    fieldWithPath("pageInfo.currentSize").type(JsonFieldType.NUMBER).description("?????? ???????????? ????????? ???"),
                    fieldWithPath("pageInfo.currentPage").type(JsonFieldType.NUMBER).description("?????? ????????? ??????"),
                    fieldWithPath("pageInfo.totalCount").type(JsonFieldType.NUMBER).description("?????? ????????? ???"),
                    fieldWithPath("pageInfo.totalPage").type(JsonFieldType.NUMBER).description("??? ????????? ???"),
                    fieldWithPath("memberList[]").type(JsonFieldType.ARRAY).description("?????? ??????").optional(),
                    fieldWithPath("memberList[].memberType").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.memberTypeFormat()),
                    fieldWithPath("memberList[].memberEmail").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("memberList[].memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("memberList[].memberGender").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.memberGenderFormat())
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

    @DisplayName("?????? ??????")
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
                    parameterWithName("memberToken").description("?????? ?????? ?????????")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????")
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