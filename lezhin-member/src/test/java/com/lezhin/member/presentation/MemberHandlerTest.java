package com.lezhin.member.presentation;

import com.lezhin.member.applicaiton.MemberFacade;
import com.lezhin.member.domain.dto.MemberDTO;
import com.lezhin.member.infrastructure.router.RouterPathPattern;
import com.lezhin.member.presentation.response.ExchangeMemberTokenResponse;
import com.lezhin.member.presentation.response.MemberResponseMapper;
import com.lezhin.member.presentation.shared.WebFluxSharedHandlerTest;
import com.lezhin.member.presentation.shared.response.SuccessResponse;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.lezhin.member.infrastructure.factory.MemberTestFactory.exchangeMemberTokenResponse;
import static com.lezhin.member.infrastructure.factory.MemberTestFactory.memberIdInfoMono;
import static com.lezhin.member.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static com.lezhin.member.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest(MemberHandler.class)
class MemberHandlerTest extends WebFluxSharedHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private MemberFacade memberFacade;
    @MockBean
    private MemberResponseMapper memberResponseMapper;

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {
        // given
        given(memberFacade.exchangeMemberToken(any(String.class))).willReturn(memberIdInfoMono());
        given(memberResponseMapper.of(any(MemberDTO.MemberIdInfo.class))).willReturn(exchangeMemberTokenResponse());

        // when
        final String URI = RouterPathPattern.EXCHANGE_MEMBER_TOKEN.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI, UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                pathParameters(
                    parameterWithName("memberToken").description("회원 대체 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호")
                )
            ));

        FluxExchangeResult<ExchangeMemberTokenResponse> flux = result.returnResult(ExchangeMemberTokenResponse.class);

        // then
        verify(memberFacade).exchangeMemberToken(any(String.class));
        verify(memberResponseMapper).of(any(MemberDTO.MemberIdInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertTrue(response.getMemberId() > 0);
            }))
            .verifyComplete();
    }

    @DisplayName("회원 삭제")
    @Test
    void memberDelete() {
        // given
        given(memberFacade.memberDelete(any(String.class))).willReturn(Mono.empty());

        // when
        final String URI = RouterPathPattern.MEMBER_DELETE.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .delete()
            .uri(URI, UUID.randomUUID().toString())
            .header(AUTHORIZATION, "accessToken")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
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
        verify(memberFacade).memberDelete(any(String.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }
}