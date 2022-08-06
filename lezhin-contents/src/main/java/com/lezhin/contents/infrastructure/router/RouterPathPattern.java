package com.lezhin.contents.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    CONTENTS_ROOT("/contents", "/contents/**"),
    CONTENTS_EVALUATION_REGISTER("/evaluation-register", "/contents/evaluation-register");

    private final String path;
    private final String fullPath;
}
