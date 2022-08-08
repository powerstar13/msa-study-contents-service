package com.lezhin.history.infrastructure.webClient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebClientPathPattern {
    // Member Domain
    EXCHANGE_MEMBER_TOKEN("/exchange/member-token/{memberToken}"),
    // Contents Domain
    EXCHANGE_CONTENTS_TOKEN("/exchange/contents-token/{contentsToken}");

    private final String fullPath;
}
