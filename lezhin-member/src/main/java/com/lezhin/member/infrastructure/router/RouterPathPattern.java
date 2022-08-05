package com.lezhin.member.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    EXCHANGE_ROOT("/exchange", "/exchange/**"),
    EXCHANGE_MEMBER_TOKEN("/member-token/{memberToken}", "/exchange/member-token/{memberToken}");

    private final String path;
    private final String fullPath;
}
