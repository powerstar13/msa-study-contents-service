package com.webtoon.member.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredMemberToken("BadRequestException", "회원 대체 식별키를 전달해 주세요."),

    NotFoundMember("NotFoundDataException", "조회된 회원 정보가 없습니다."),

    ServerError("RuntimeException", "서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다.");

    private final String type;
    private final String message;
}
