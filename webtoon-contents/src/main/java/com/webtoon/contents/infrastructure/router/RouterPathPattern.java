package com.webtoon.contents.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    CONTENTS_ROOT("/contents", "/contents/**"),
    CONTENTS_EVALUATION_REGISTER("/evaluation-register", "/contents/evaluation-register"),
    EVALUATION_TOP3_CONTENTS("/evaluation-top3", "/contents/evaluation-top3"),
    PRICING_MODIFY("/pricing-modify", "/contents/pricing-modify"),

    EXCHANGE_ROOT("/exchange", "/exchange/**"),
    EXCHANGE_CONTENTS_TOKEN("/contents-token/{contentsToken}", "/exchange/contents-token/{contentsToken}"),

    DELETE_ROOT("/delete", "/delete/**"),
    DELETE_EVALUATION_BY_MEMBER("/evaluation-by-member/{memberToken}", "/delete/evaluation-by-member/{memberToken}");

    private final String path;
    private final String fullPath;
}
