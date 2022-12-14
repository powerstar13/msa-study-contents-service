package com.webtoon.contents.presentation;

import com.webtoon.contents.application.ContentsFacade;
import com.webtoon.contents.application.dto.ContentsCommand;
import com.webtoon.contents.domain.service.dto.ContentsDTO;
import com.webtoon.contents.infrastructure.router.RouterPathPattern;
import com.webtoon.contents.presentation.request.ContentsRequestMapper;
import com.webtoon.contents.presentation.request.EvaluationRegisterRequest;
import com.webtoon.contents.presentation.request.PricingModifyRequest;
import com.webtoon.contents.presentation.response.ContentsResponseMapper;
import com.webtoon.contents.presentation.response.EvaluationRegisterResponse;
import com.webtoon.contents.presentation.response.EvaluationTop3ContentsResponse;
import com.webtoon.contents.presentation.response.ExchangeContentsTokenResponse;
import com.webtoon.contents.presentation.shared.WebFluxSharedHandlerTest;
import com.webtoon.contents.presentation.shared.response.SuccessResponse;
import com.webtoon.contents.infrastructure.factory.ContentsTestFactory;
import com.webtoon.contents.infrastructure.restdocs.RestdocsDocumentFormat;
import com.webtoon.contents.infrastructure.restdocs.RestdocsDocumentUtil;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(ContentsHandler.class)
class ContentsHandlerTest extends WebFluxSharedHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ContentsFacade contentsFacade;
    @MockBean
    private ContentsRequestMapper contentsRequestMapper;
    @MockBean
    private ContentsResponseMapper contentsResponseMapper;

    @DisplayName("?????? ??????")
    @Test
    void evaluationRegister() {
        // given
        given(contentsRequestMapper.of(any(EvaluationRegisterRequest.class))).willReturn(ContentsTestFactory.evaluationRegisterCommand());
        given(contentsFacade.evaluationRegister(any(ContentsCommand.EvaluationRegister.class))).willReturn(ContentsTestFactory.evaluationTokenInfoDTOMono());
        given(contentsResponseMapper.of(any(ContentsDTO.EvaluationTokenInfo.class))).willReturn(ContentsTestFactory.evaluationRegisterResponse());

        // when
        final String URI = RouterPathPattern.CONTENTS_EVALUATION_REGISTER.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .post()
            .uri(URI)
            .header(AUTHORIZATION, "accessToken")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ContentsTestFactory.evaluationRegisterRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(WebTestClientRestDocumentation.document(URI,
                RestdocsDocumentUtil.requestPrettyPrint(),
                RestdocsDocumentUtil.responsePrettyPrint(),
                requestFields(
                    fieldWithPath("memberToken").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                    fieldWithPath("contentsToken").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                    fieldWithPath("evaluationType").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.evaluationTypeFormat()),
                    fieldWithPath("comment").type(JsonFieldType.STRING).description("??????").attributes(RestdocsDocumentFormat.commentFormat()).optional()
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("evaluationToken").type(JsonFieldType.STRING).description("?????? ?????? ?????????")
                )
            ));

        FluxExchangeResult<EvaluationRegisterResponse> flux = result.returnResult(EvaluationRegisterResponse.class);

        // then
        verify(contentsRequestMapper).of(any(EvaluationRegisterRequest.class));
        verify(contentsFacade).evaluationRegister(any(ContentsCommand.EvaluationRegister.class));
        verify(contentsResponseMapper).of(any(ContentsDTO.EvaluationTokenInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.CREATED.value(), response.getRt());
                assertInstanceOf(String.class, response.getEvaluationToken());
            }))
            .verifyComplete();
    }

    @DisplayName("??????/????????? Top3 ?????? ??????")
    @Test
    void evaluationTop3Contents() {
        // given
        given(contentsFacade.evaluationTop3Contents()).willReturn(ContentsTestFactory.evaluationTop3ContentsDTOMono());
        given(contentsResponseMapper.of(any(ContentsDTO.EvaluationTop3Contents.class))).willReturn(ContentsTestFactory.evaluationTop3ContentsResponse());

        // when
        final String URI = RouterPathPattern.EVALUATION_TOP3_CONTENTS.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI)
            .header(AUTHORIZATION, "accessToken")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(WebTestClientRestDocumentation.document(URI,
                RestdocsDocumentUtil.requestPrettyPrint(),
                RestdocsDocumentUtil.responsePrettyPrint(),
                requestParameters(
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("likeTop3Contents[]").type(JsonFieldType.ARRAY).description("????????? Top3 ?????? ??????"),
                    fieldWithPath("likeTop3Contents[].contentsToken").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                    fieldWithPath("likeTop3Contents[].contentsName").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("likeTop3Contents[].author").type(JsonFieldType.STRING).description("??????"),
                    fieldWithPath("likeTop3Contents[].pricingType").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.pricingTypeFormat()),
                    fieldWithPath("likeTop3Contents[].coin").type(JsonFieldType.NUMBER).description("??????"),
                    fieldWithPath("likeTop3Contents[].adultOnly").type(JsonFieldType.STRING).description("????????? ??????").attributes(RestdocsDocumentFormat.adultOnlyFormat()),
                    fieldWithPath("likeTop3Contents[].openAt").type(JsonFieldType.STRING).description("????????? ?????????").attributes(RestdocsDocumentFormat.yyyyMMddFormat()),
                    fieldWithPath("dislikeTop3Contents[]").type(JsonFieldType.ARRAY).description("????????? Top3 ?????? ??????"),
                    fieldWithPath("dislikeTop3Contents[].contentsToken").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                    fieldWithPath("dislikeTop3Contents[].contentsName").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("dislikeTop3Contents[].author").type(JsonFieldType.STRING).description("??????"),
                    fieldWithPath("dislikeTop3Contents[].pricingType").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.pricingTypeFormat()),
                    fieldWithPath("dislikeTop3Contents[].coin").type(JsonFieldType.NUMBER).description("??????"),
                    fieldWithPath("dislikeTop3Contents[].adultOnly").type(JsonFieldType.STRING).description("????????? ??????").attributes(RestdocsDocumentFormat.adultOnlyFormat()),
                    fieldWithPath("dislikeTop3Contents[].openAt").type(JsonFieldType.STRING).description("????????? ?????????").attributes(RestdocsDocumentFormat.yyyyMMddFormat())
                )
            ));

        FluxExchangeResult<EvaluationTop3ContentsResponse> flux = result.returnResult(EvaluationTop3ContentsResponse.class);

        // then
        verify(contentsFacade).evaluationTop3Contents();
        verify(contentsResponseMapper).of(any(ContentsDTO.EvaluationTop3Contents.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertEquals(3, response.getLikeTop3Contents().size());
                assertEquals(3, response.getDislikeTop3Contents().size());
            }))
            .verifyComplete();
    }

    @DisplayName("?????? ???????????? ????????????")
    @Test
    void exchangeContentsToken() {
        // given
        given(contentsFacade.exchangeContentsToken(anyString())).willReturn(ContentsTestFactory.contentsIdInfoMono());
        given(contentsResponseMapper.of(any(ContentsDTO.ContentsIdInfo.class))).willReturn(ContentsTestFactory.exchangeContentsTokenResponse());

        // when
        final String URI = RouterPathPattern.EXCHANGE_CONTENTS_TOKEN.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI, UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(WebTestClientRestDocumentation.document(URI,
                RestdocsDocumentUtil.requestPrettyPrint(),
                RestdocsDocumentUtil.responsePrettyPrint(),
                pathParameters(
                    parameterWithName("contentsToken").description("?????? ?????? ?????????")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("contentsId").type(JsonFieldType.NUMBER).description("?????? ????????????")
                )
            ));

        FluxExchangeResult<ExchangeContentsTokenResponse> flux = result.returnResult(ExchangeContentsTokenResponse.class);

        // then
        verify(contentsFacade).exchangeContentsToken(anyString());
        verify(contentsResponseMapper).of(any(ContentsDTO.ContentsIdInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertTrue(response.getContentsId() > 0);
            }))
            .verifyComplete();
    }

    @DisplayName("?????? ??????")
    @Test
    void pricingModify() {
        // given
        given(contentsRequestMapper.of(any(PricingModifyRequest.class))).willReturn(ContentsTestFactory.pricingModifyCommand());
        given(contentsFacade.pricingModify(any(ContentsCommand.PricingModify.class))).willReturn(Mono.empty());

        // when
        final String URI = RouterPathPattern.PRICING_MODIFY.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .put()
            .uri(URI)
            .header(AUTHORIZATION, "accessToken")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(ContentsTestFactory.pricingModifyRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(WebTestClientRestDocumentation.document(URI,
                RestdocsDocumentUtil.requestPrettyPrint(),
                RestdocsDocumentUtil.responsePrettyPrint(),
                requestFields(
                    fieldWithPath("contentsToken").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                    fieldWithPath("pricingType").type(JsonFieldType.STRING).description("?????? ??????").attributes(RestdocsDocumentFormat.pricingTypeFormat()).optional(),
                    fieldWithPath("coin").type(JsonFieldType.NUMBER).description("??????").attributes(RestdocsDocumentFormat.coinFormat()).optional()
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????")
                )
            ));

        FluxExchangeResult<SuccessResponse> flux = result.returnResult(SuccessResponse.class);

        // then
        verify(contentsRequestMapper).of(any(PricingModifyRequest.class));
        verify(contentsFacade).pricingModify(any(ContentsCommand.PricingModify.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }

    @DisplayName("?????? ??????")
    @Test
    void evaluationDeleteByMember() {
        // given
        given(contentsFacade.evaluationDeleteByMember(anyString())).willReturn(Mono.empty());

        // when
        final String URI = RouterPathPattern.DELETE_EVALUATION_BY_MEMBER.getFullPath();
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
        verify(contentsFacade).evaluationDeleteByMember(anyString());

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }
}