package com.webtoon.contents.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webtoon.contents.domain.AdultOnly;
import com.webtoon.contents.domain.PricingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ContentsDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EvaluationTokenInfo {

        private String evaluationToken; // 평가 대체 식별키

        @Override
        public String toString() {
            return "{"
                + "\"evaluationToken\":\"" + evaluationToken + "\""
                + "}";
        }
    }

    @Getter
    @Builder
    public static class EvaluationTop3Contents {

        private List<ContentsInfo> likeTop3Contents; // 좋아요 Top3 작품 목록
        private List<ContentsInfo> dislikeTop3Contents; // 싫어요 Top3 작품 목록

        @Override
        public String toString() {
            return "{"
                + "\"likeTop3Contents\":" + likeTop3Contents
                + ", \"dislikeTop3Contents\":" + dislikeTop3Contents
                + "}";
        }
    }

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

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ContentsIdInfo {

        private long contentsId; // 작품 고유번호

        @Override
        public String toString() {
            return "{"
                + "\"contentsId\":" + contentsId
                + "}";
        }
    }
}
