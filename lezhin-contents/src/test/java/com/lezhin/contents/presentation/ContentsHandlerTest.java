package com.lezhin.contents.presentation;

import com.lezhin.contents.application.ContentsFacade;
import com.lezhin.contents.application.dto.ContentsCommand;
import com.lezhin.contents.domain.service.dto.ContentsDTO;
import com.lezhin.contents.infrastructure.router.RouterPathPattern;
import com.lezhin.contents.presentation.request.ContentsRequestMapper;
import com.lezhin.contents.presentation.request.EvaluationRegisterRequest;
import com.lezhin.contents.presentation.response.ContentsResponseMapper;
import com.lezhin.contents.presentation.response.EvaluationRegisterResponse;
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

import static com.lezhin.contents.infrastructure.factory.ContentsTestFactory.*;
import static com.lezhin.contents.infrastructure.restdocs.RestdocsDocumentFormat.commentFormat;
import static com.lezhin.contents.infrastructure.restdocs.RestdocsDocumentFormat.evaluationTypeFormat;
import static com.lezhin.contents.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static com.lezhin.contents.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
                    fieldWithPath("contentsToken").type(JsonFieldType.STRING).description("회원 대체 식별키"),
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
}