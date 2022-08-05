package com.lezhin.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberGender {
    // 회원 성별
    M("남성"),
    W("여성");

    private final String description;
}
