package com.webtoon.member.infrastructure.router;

import com.webtoon.member.presentation.MemberHandler;
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
    public RouterFunction<ServerResponse> routerBuilder(MemberHandler memberHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.EXCHANGE_ROOT.getPath(), builder ->
                builder
                    .GET(RouterPathPattern.EXCHANGE_MEMBER_TOKEN.getPath(), memberHandler::exchangeMemberToken) // 회원 고유번호 가져오기
            )
            .path(RouterPathPattern.MEMBER_ROOT.getPath(), builder1 ->
                builder1.nest(accept(MediaType.APPLICATION_JSON), builder2 ->
                    builder2
                        .DELETE(RouterPathPattern.MEMBER_DELETE.getPath(), memberHandler::memberDelete) // 회원 삭제
                )
            )
            .build();
    }
}
