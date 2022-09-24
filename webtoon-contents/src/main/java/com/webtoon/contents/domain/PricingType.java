package com.webtoon.contents.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PricingType {
    // 가격 유형
    FREE("무료"),
    PAY("유료");

    private final String description;
}
