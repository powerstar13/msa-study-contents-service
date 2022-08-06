package com.lezhin.contents.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest("BadRequestException", "Request를 전달해주세요."),
    IsRequiredMemberToken("BadRequestException", "회원 대체 식별키를 전달해주세요."),
    IsRequiredContentsToken("BadRequestException", "작품 대체 식별키를 전달해주세요."),
    IsRequiredContentsType("BadRequestException", "평가 유형을 전달해주세요."),

    UnavailableCommentPattern("BadRequestException", "특수문자는 입력할 수 없습니다."),

    AlreadyDataEvaluation("AlreadyDataException", "이미 평가를 하셨습니다."),

    RegisterFailEvaluation("RegisterFailException", "평가 등록에 실패했습니다. 관리자에게 문의 바랍니다."),

    NotFoundContents("NotFoundDataException", "조회된 작품 정보가 없습니다."),
    ServerError("RuntimeException", "서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다.");

    private final String type;
    private final String message;
}
