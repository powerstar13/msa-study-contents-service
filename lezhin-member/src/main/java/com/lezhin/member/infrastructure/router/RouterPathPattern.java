package com.lezhin.member.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    MEMBER_ROOT("/member", "/member/**"),
    MEMBER_DELETE("/delete/{memberToken}", "/member/delete/{memberToken}"),

    EXCHANGE_ROOT("/exchange", "/exchange/**"),
    EXCHANGE_MEMBER_TOKEN("/member-token/{memberToken}", "/exchange/member-token/{memberToken}");

    private final String path;
    private final String fullPath;
}
