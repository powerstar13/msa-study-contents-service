package com.lezhin.history.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    HISTORY_ROOT("/history", "/history/**"),
    CONTENTS_HISTORY_PAGE("/contents/page", "/history/contents/page");

    private final String path;
    private final String fullPath;
}
