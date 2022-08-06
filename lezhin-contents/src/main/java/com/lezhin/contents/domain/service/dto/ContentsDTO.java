package com.lezhin.contents.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ContentsDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EvaluationTokenInfo {

        private String evaluationToken; // 평가 대체 식별키

        @Override
        public String toString() {
            return "{"
                + "\"evaluationToken\":\"" + evaluationToken + "\""
                + "}";
        }
    }
}
