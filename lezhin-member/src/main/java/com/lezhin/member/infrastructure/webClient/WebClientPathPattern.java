package com.lezhin.member.infrastructure.webClient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebClientPathPattern {
    // Contents Domain
    DELETE_EVALUATION_BY_MEMBER("/delete/evaluation-by-member/{memberToken}"),
    // History Domain
    DELETE_HISTORY_BY_MEMBER("/delete/history-by-member/{memberToken}");

    private final String fullPath;
}
