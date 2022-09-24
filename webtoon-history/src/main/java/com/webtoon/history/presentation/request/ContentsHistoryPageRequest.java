package com.webtoon.history.presentation.request;

import com.webtoon.history.infrastructure.exception.status.BadRequestException;
import com.webtoon.history.infrastructure.exception.status.ExceptionMessage;
import com.webtoon.history.presentation.shared.request.PageRequestDTO;
import com.webtoon.history.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ContentsHistoryPageRequest extends PageRequestDTO implements RequestVerify {

    private String contentsToken; // 작품 대체 식별키

    @Override
    public void verify() {
        if (StringUtils.isBlank(contentsToken)) throw new BadRequestException(ExceptionMessage.IsRequiredContentsToken.getMessage());
    }

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"contentsToken\":\"" + contentsToken + "\""
            + "}";
    }
}
