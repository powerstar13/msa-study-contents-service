package com.webtoon.history.infrastructure.router;

import com.webtoon.history.presentation.HistoryHandler;
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
    public RouterFunction<ServerResponse> routerBuilder(HistoryHandler historyHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.HISTORY_ROOT.getPath(), builder ->
                builder
                    .GET(RouterPathPattern.CONTENTS_HISTORY_PAGE.getPath(), historyHandler::contentsHistoryPage) // 작품별 조회 이력 페이지
                    .GET(RouterPathPattern.SEARCH_HISTORY_PAGE.getPath(), historyHandler::searchHistoryPage) // 사용자 조회 이력 페이지
            )
            .path(RouterPathPattern.DELETE_ROOT.getPath(), builder1 ->
                builder1.nest(accept(MediaType.APPLICATION_JSON), builder2 ->
                    builder2
                        .DELETE(RouterPathPattern.DELETE_HISTORY_BY_MEMBER.getPath(), historyHandler::historyDeleteByMember) // 이력 삭제
                )
            )
            .build();
    }
}
