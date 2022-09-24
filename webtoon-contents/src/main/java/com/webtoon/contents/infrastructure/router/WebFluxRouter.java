package com.webtoon.contents.infrastructure.router;

import com.webtoon.contents.presentation.ContentsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@EnableWebFlux
public class WebFluxRouter implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(3600);
    }

    @Bean
    public RouterFunction<ServerResponse> routerBuilder(ContentsHandler contentsHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.CONTENTS_ROOT.getPath(), builder1 ->
                builder1.nest(accept(MediaType.APPLICATION_JSON), builder2 ->
                    builder2
                        .POST(RouterPathPattern.CONTENTS_EVALUATION_REGISTER.getPath(), contentsHandler::evaluationRegister) // 평가 등록
                        .PUT(RouterPathPattern.PRICING_MODIFY.getPath(), contentsHandler::pricingModify) // 가격 변경
                )
            )
            .path(RouterPathPattern.CONTENTS_ROOT.getPath(), builder ->
                builder
                    .GET(RouterPathPattern.EVALUATION_TOP3_CONTENTS.getPath(), contentsHandler::evaluationTop3Contents) // 좋아요/싫어요 Top3 작품 조회
            )
            .path(RouterPathPattern.EXCHANGE_ROOT.getPath(), builder ->
                builder
                    .GET(RouterPathPattern.EXCHANGE_CONTENTS_TOKEN.getPath(), contentsHandler::exchangeContentsToken) // 작품 고유번호 가져오기
            )
            .path(RouterPathPattern.DELETE_ROOT.getPath(), builder1 ->
                builder1.nest(accept(MediaType.APPLICATION_JSON), builder2 ->
                    builder2
                        .DELETE(RouterPathPattern.DELETE_EVALUATION_BY_MEMBER.getPath(), contentsHandler::evaluationDeleteByMember) // 평가 삭제
                )
            )
            .build();
    }
}
