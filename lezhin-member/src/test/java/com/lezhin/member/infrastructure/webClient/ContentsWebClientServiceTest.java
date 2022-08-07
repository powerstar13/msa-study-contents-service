package com.lezhin.member.infrastructure.webClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lezhin.member.infrastructure.webClient.response.CommonResponse;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.UUID;

import static com.lezhin.member.infrastructure.factory.MemberTestFactory.commonResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ContentsWebClientServiceTest {

    private static ContentsWebClientService contentsWebClientService;

    private static ObjectMapper objectMapper;

    public static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        contentsWebClientService = new ContentsWebClientService(mockWebServer.url("test").toString());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("평가 삭제")
    @Test
    void evaluationDeleteByMember() throws JsonProcessingException {

        mockWebServer.enqueue(new MockResponse()
            .setBody(objectMapper.writeValueAsString(commonResponse()))
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        Mono<CommonResponse> result = contentsWebClientService.evaluationDeleteByMember(UUID.randomUUID().toString());

        StepVerifier.create(result.log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }
}