package com.lezhin.contents.infrastructure.router;

import com.lezhin.contents.presentation.ContentsHandler;
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
    public RouterFunction<ServerResponse> contentsRouterBuilder(ContentsHandler contentsHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.CONTENTS_ROOT.getPath(), memberBuilder ->
                memberBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .POST(RouterPathPattern.CONTENTS_EVALUATION_REGISTER.getPath(), contentsHandler::evaluationRegister) // 평가 등록
                )
            )
            .path(RouterPathPattern.CONTENTS_ROOT.getPath(), memberBuilder ->
                memberBuilder
                    .GET(RouterPathPattern.EVALUATION_TOP3_CONTENTS.getPath(), contentsHandler::evaluationTop3Contents) // 좋아요/싫어요 Top3 작품 조회
            )
            .build();
    }
}
