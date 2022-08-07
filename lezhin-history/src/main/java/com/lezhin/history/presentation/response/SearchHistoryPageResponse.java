package com.lezhin.history.presentation.response;

import com.lezhin.history.presentation.response.dto.HistoryResponseDTO;
import com.lezhin.history.presentation.shared.response.PageResponseDTO;
import com.lezhin.history.presentation.shared.response.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryPageResponse extends SuccessResponse {

    private PageResponseDTO pageInfo; // 페이지 정보
    private List<HistoryResponseDTO.SearchHistoryMemberInfo> memberList; // 데이터 목록

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"pageInfo\":" + pageInfo
            + ", \"memberList\":" + memberList
            + "}";
    }
}
