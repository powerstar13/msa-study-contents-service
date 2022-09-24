package com.webtoon.contents.presentation.response;

import com.webtoon.contents.presentation.response.dto.ContentsResponseDTO;
import com.webtoon.contents.presentation.shared.response.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationTop3ContentsResponse extends SuccessResponse {

    private List<ContentsResponseDTO.ContentsInfo> likeTop3Contents; // 좋아요 Top3 작품 목록
    private List<ContentsResponseDTO.ContentsInfo> dislikeTop3Contents; // 싫어요 Top3 작품 목록

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"likeTop3Contents\":" + likeTop3Contents
            + ", \"dislikeTop3Contents\":" + dislikeTop3Contents
            + "}";
    }
}
