package com.lezhin.history.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredContentsToken("BadRequestException", "작품 대체 식별키를 전달해주세요."),
    IsRequiredMemberToken("BadRequestException", "회원 대체 식별키를 전달해주세요."),

    ServerError("RuntimeException", "서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다.");

    private final String type;
    private final String message;
}
