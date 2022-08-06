package com.lezhin.contents.infrastructure.exception.status;

import com.lezhin.contents.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RegisterFailException extends GlobalException {

    public RegisterFailException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public RegisterFailException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public RegisterFailException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
