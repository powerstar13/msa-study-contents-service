package com.webtoon.contents.presentation.request;

import com.webtoon.contents.domain.EvaluationType;
import com.webtoon.contents.infrastructure.exception.status.BadRequestException;
import com.webtoon.contents.infrastructure.exception.status.ExceptionMessage;
import com.webtoon.contents.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationRegisterRequest implements RequestVerify {

    private String memberToken; // 회원 대체 식별키
    private String contentsToken; // 작품 대체 식별키
    private EvaluationType evaluationType; // 평가 유형
    private String comment; // 댓글

    @Override
    public void verify() {
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());
        if (StringUtils.isBlank(contentsToken)) throw new BadRequestException(ExceptionMessage.IsRequiredContentsToken.getMessage());
        if (evaluationType == null) throw new BadRequestException(ExceptionMessage.IsRequiredContentsType.getMessage());
        if (StringUtils.isNotBlank(comment) && !StringUtils.isAlphanumeric(comment)) throw new BadRequestException(ExceptionMessage.UnavailableCommentPattern.getMessage());
    }

    @Override
    public String toString() {
        return "{"
            + "\"memberToken\":\"" + memberToken + "\""
            + ", \"contentsToken\":\"" + contentsToken + "\""
            + ", \"evaluationType\":\"" + evaluationType + "\""
            + ", \"comment\":\"" + comment + "\""
            + "}";
    }
}
