package com.webtoon.history.infrastructure.webClient.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeContentsTokenResponse extends CommonResponse {

    private long contentsId;

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"contentsId\":" + contentsId
            + "}";
    }
}
