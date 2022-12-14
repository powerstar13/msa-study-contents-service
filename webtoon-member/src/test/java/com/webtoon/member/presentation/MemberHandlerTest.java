package com.webtoon.member.presentation;

import com.webtoon.member.applicaiton.MemberFacade;
import com.webtoon.member.domain.dto.MemberDTO;
import com.webtoon.member.infrastructure.router.RouterPathPattern;
import com.webtoon.member.presentation.response.ExchangeMemberTokenResponse;
import com.webtoon.member.presentation.response.MemberResponseMapper;
import com.webtoon.member.presentation.shared.WebFluxSharedHandlerTest;
import com.webtoon.member.presentation.shared.response.SuccessResponse;
import org.junit.jupiter.api.Assertions;
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

import static com.webtoon.member.infrastructure.factory.MemberTestFactory.exchangeMemberTokenResponse;
import static com.webtoon.member.infrastructure.factory.MemberTestFactory.memberIdInfoMono;
import static com.webtoon.member.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static com.webtoon.member.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;
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

    @DisplayName("?????? ???????????? ????????????")
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
                    parameterWithName("memberToken").description("?????? ?????? ?????????")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ????????????")
                )
            ));

        FluxExchangeResult<ExchangeMemberTokenResponse> flux = result.returnResult(ExchangeMemberTokenResponse.class);

        // then
        verify(memberFacade).exchangeMemberToken(any(String.class));
        verify(memberResponseMapper).of(any(MemberDTO.MemberIdInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                Assertions.assertEquals(HttpStatus.OK.value(), response.getRt());
                assertTrue(response.getMemberId() > 0);
            }))
            .verifyComplete();
    }

    @DisplayName("?????? ??????")
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
                    parameterWithName("memberToken").description("?????? ?????? ?????????")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("?????? ?????????")
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