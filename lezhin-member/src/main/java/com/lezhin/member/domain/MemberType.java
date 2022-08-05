package com.lezhin.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
    // 회원 유형
    ADMIN("관리자"),
    NORMAL("일반"),
    ADULT("성인");

    private final String description;
}
