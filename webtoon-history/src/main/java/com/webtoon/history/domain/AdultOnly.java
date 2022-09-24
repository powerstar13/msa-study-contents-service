package com.webtoon.history.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdultOnly {
    // 성인물 여부
    Y("예"),
    N("아니요");

    private final String description;
}
