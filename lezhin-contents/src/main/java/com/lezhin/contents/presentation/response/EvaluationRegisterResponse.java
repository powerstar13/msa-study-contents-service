package com.lezhin.contents.presentation.response;

import com.lezhin.contents.presentation.shared.response.CreatedSuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationRegisterResponse extends CreatedSuccessResponse {

    private String evaluationToken; // 평가 대체 식별키

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"evaluationToken\":\"" + evaluationToken + "\""
            + "}";
    }
}
