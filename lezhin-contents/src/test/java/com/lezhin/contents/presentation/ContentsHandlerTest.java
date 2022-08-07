package com.lezhin.contents.presentation;

import com.lezhin.contents.application.ContentsFacade;
import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.infrastructure.router.RouterPathPattern;
import com.lezhin.contents.presentation.request.ContentsRequestMapper;
import com.lezhin.contents.presentation.request.EvaluationRegisterRequest;
import com.lezhin.contents.presentation.response.ContentsResponseMapper;
import com.lezhin.contents.presentation.response.EvaluationRegisterResponse;
import com.lezhin.contents.presentation.response.EvaluationTop3ContentsResponse;
import com.lezhin.contents.presentation.response.ExchangeContentsTokenResponse;
import com.lezhin.contents.presentation.shared.WebFluxSharedHandlerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.*;
import static com.lezhin.contents.infrastructure.restdocs.RestdocsDocumentFormat.*;
import static com.lezhin.contents.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static com.lezhin.contents.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @DisplayName("평가 등록")
    @Test
    void evaluationRegister() {
        // given
        given(contentsRequestMapper.of(any(EvaluationRegisterRequest.class))).willReturn(evaluationRegisterCommand());
        given(contentsFacade.evaluationRegister(any(ContentsCommand.EvaluationRegister.class))).willReturn(evaluationTokenInfoDTOMono());
        given(contentsResponseMapper.of(any(ContentsDTO.EvaluationTokenInfo.class))).willReturn(evaluationRegisterResponse());

        // when
        final String URI = RouterPathPattern.CONTENTS_EVALUATION_REGISTER.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .post()
            .uri(URI)
            .header(AUTHORIZATION, "accessToken")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(evaluationRegisterRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 대체 식별키"),
                    fieldWithPath("contentsToken").type(JsonFieldType.STRING).description("작품 대체 식별키"),
                    fieldWithPath("evaluationType").type(JsonFieldType.STRING).description("평가 유형").attributes(evaluationTypeFormat()),
                    fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글").attributes(commentFormat()).optional()
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("evaluationToken").type(JsonFieldType.STRING).description("평가 대체 식별키")
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

    @DisplayName("아요/싫어요 Top3 작품 조회")
    @Test
    void evaluationTop3Contents() {
        // given
        given(contentsFacade.evaluationTop3Contents()).willReturn(evaluationTop3ContentsDTOMono());
        given(contentsResponseMapper.of(any(ContentsDTO.EvaluationTop3Contents.class))).willReturn(evaluationTop3ContentsResponse());

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
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestParameters(
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("likeTop3Contents[]").type(JsonFieldType.ARRAY).description("좋아요 Top3 작품 목록"),
                    fieldWithPath("likeTop3Contents[].contentsToken").type(JsonFieldType.STRING).description("작품 대체 식별키"),
                    fieldWithPath("likeTop3Contents[].contentsName").type(JsonFieldType.STRING).description("작품명"),
                    fieldWithPath("likeTop3Contents[].author").type(JsonFieldType.STRING).description("작가"),
                    fieldWithPath("likeTop3Contents[].pricingType").type(JsonFieldType.STRING).description("가격 유형").attributes(pricingTypeFormat()),
                    fieldWithPath("likeTop3Contents[].coin").type(JsonFieldType.NUMBER).description("금액"),
                    fieldWithPath("likeTop3Contents[].adultOnly").type(JsonFieldType.STRING).description("성인물 여부").attributes(adultOnlyFormat()),
                    fieldWithPath("likeTop3Contents[].openAt").type(JsonFieldType.STRING).description("서비스 제공일").attributes(yyyyMMddFormat()),
                    fieldWithPath("dislikeTop3Contents[]").type(JsonFieldType.ARRAY).description("싫어요 Top3 작품 목록"),
                    fieldWithPath("dislikeTop3Contents[].contentsToken").type(JsonFieldType.STRING).description("작품 대체 식별키"),
                    fieldWithPath("dislikeTop3Contents[].contentsName").type(JsonFieldType.STRING).description("작품명"),
                    fieldWithPath("dislikeTop3Contents[].author").type(JsonFieldType.STRING).description("작가"),
                    fieldWithPath("dislikeTop3Contents[].pricingType").type(JsonFieldType.STRING).description("가격 유형").attributes(pricingTypeFormat()),
                    fieldWithPath("dislikeTop3Contents[].coin").type(JsonFieldType.NUMBER).description("금액"),
                    fieldWithPath("dislikeTop3Contents[].adultOnly").type(JsonFieldType.STRING).description("성인물 여부").attributes(adultOnlyFormat()),
                    fieldWithPath("dislikeTop3Contents[].openAt").type(JsonFieldType.STRING).description("서비스 제공일").attributes(yyyyMMddFormat())
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

    @DisplayName("작품 고유번호 가져오기")
    @Test
    void exchangeContentsToken() {
        // given
        given(contentsFacade.exchangeContentsToken(any(String.class))).willReturn(contentsIdInfoMono());
        given(contentsResponseMapper.of(any(ContentsDTO.ContentsIdInfo.class))).willReturn(exchangeContentsTokenResponse());

        // when
        final String URI = RouterPathPattern.EXCHANGE_CONTENTS_TOKEN.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI, UUID.randomUUID().toString())
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                pathParameters(
                    parameterWithName("contentsToken").description("작품 대체 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("contentsId").type(JsonFieldType.NUMBER).description("작품 고유번호")
                )
            ));

        FluxExchangeResult<ExchangeContentsTokenResponse> flux = result.returnResult(ExchangeContentsTokenResponse.class);

        // then
        verify(contentsFacade).exchangeContentsToken(any(String.class));
        verify(contentsResponseMapper).of(any(ContentsDTO.ContentsIdInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertTrue(response.getContentsId() > 0);
            }))
            .verifyComplete();
    }
}