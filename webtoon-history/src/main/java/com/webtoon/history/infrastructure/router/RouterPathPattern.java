package com.webtoon.history.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    HISTORY_ROOT("/history", "/history/**"),
    CONTENTS_HISTORY_PAGE("/contents/page", "/history/contents/page"),
    SEARCH_HISTORY_PAGE("/search/page", "/history/search/page"),

    DELETE_ROOT("/delete", "/delete/**"),
    DELETE_HISTORY_BY_MEMBER("/history-by-member/{memberToken}", "/delete/history-by-member/{memberToken}");

    private final String path;
    private final String fullPath;
}
