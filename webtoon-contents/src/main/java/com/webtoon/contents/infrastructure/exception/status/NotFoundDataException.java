package com.webtoon.contents.infrastructure.exception.status;

import com.webtoon.contents.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class NotFoundDataException extends GlobalException {

    public NotFoundDataException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundDataException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public NotFoundDataException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
