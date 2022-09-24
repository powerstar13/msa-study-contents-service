package com.webtoon.member.infrastructure.webClient.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    private int rt;
    private String rtMsg;

    @Override
    public String toString() {
        return "{"
            + "\"rt\":" + rt
            + ", \"rtMsg\":\"" + rtMsg + "\""
            + "}";
    }
}
