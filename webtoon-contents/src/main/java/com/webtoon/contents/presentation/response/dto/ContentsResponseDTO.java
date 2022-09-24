package com.webtoon.contents.presentation.response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webtoon.contents.domain.AdultOnly;
import com.webtoon.contents.domain.PricingType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class ContentsResponseDTO {

    @Getter
    @Builder
    public static class ContentsInfo {

        private String contentsToken; // 작품 대체 식별키
        private String contentsName; // 작품명
        private String author; // 작가
        private PricingType pricingType; // 가격 유형
        private int coin; // 금액
        private AdultOnly adultOnly; // 성인물 여부
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate openAt; // 서비스 제공일

        @Override
        public String toString() {
            return "{"
                + "\"contentsToken\":\"" + contentsToken + "\""
                + ", \"contentsName\":\"" + contentsName + "\""
                + ", \"author\":\"" + author + "\""
                + ", \"pricingType\":\"" + pricingType + "\""
                + ", \"coin\":" + coin
                + ", \"adultOnly\":\"" + adultOnly + "\""
                + ", \"openAt\":" + openAt
                + "}";
        }
    }
}
