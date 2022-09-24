package com.webtoon.history.presentation.request;

import com.webtoon.history.domain.AdultOnly;
import com.webtoon.history.presentation.shared.request.PageRequestDTO;
import com.webtoon.history.presentation.shared.request.RequestVerify;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryPageRequest extends PageRequestDTO implements RequestVerify {

    private Integer weekInterval; // 주 간격
    private AdultOnly adultOnly; // 성인물 여부
    private Integer historyCount; // 이력 수

    @Override
    public void verify() {}

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"weekInterval\":" + weekInterval
            + ", \"adultOnly\":\"" + adultOnly + "\""
            + ", \"historyCount\":" + historyCount
            + "}";
    }
}
