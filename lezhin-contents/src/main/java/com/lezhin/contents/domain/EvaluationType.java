package com.lezhin.contents.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EvaluationType {
    // 평가 유형
    LIKE("좋아요"),
    DISLIKE("싫어요");

    private final String description;
}
