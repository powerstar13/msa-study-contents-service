package com.lezhin.contents.presentation.response;

import com.lezhin.contents.presentation.shared.response.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeContentsTokenResponse extends SuccessResponse {

    private long contentsId; // 작품 고유번호

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"contentsId\":" + contentsId
            + "}";
    }
}
