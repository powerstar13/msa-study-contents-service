package com.webtoon.contents.application.dto;

import com.webtoon.contents.domain.Evaluation;
import com.webtoon.contents.domain.EvaluationType;
import com.webtoon.contents.domain.PricingType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class ContentsCommand {

    @Getter
    @Builder
    public static class EvaluationRegister {

        private String memberToken; // 회원 대체 식별키
        private String contentsToken; // 작품 대체 식별키
        private EvaluationType evaluationType; // 평가 유형
        private String comment; // 댓글

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

    @Getter
    @Builder
    public static class ExchangedMemberIdForEvaluationRegister {

        private long memberId; // 회원 고유번호
        private String contentsToken; // 작품 대체 식별키
        private EvaluationType evaluationType; // 평가 유형
        private String comment; // 댓글

        @Override
        public String toString() {
            return "{"
                + "\"memberId\":" + memberId
                + ", \"contentsToken\":\"" + contentsToken + "\""
                + ", \"evaluationType\":\"" + evaluationType + "\""
                + ", \"comment\":\"" + comment + "\""
                + "}";
        }
    }

    @Getter
    @Builder
    public static class ExchangedContentsIdForEvaluationRegister {

        private long memberId; // 회원 고유번호
        private long contentsId; // 작품 고유번호
        private EvaluationType evaluationType; // 평가 유형
        private String comment; // 댓글

        public Evaluation toEntity() {

            return Evaluation.builder()
                .evaluationToken(UUID.randomUUID().toString())
                .memberId(this.memberId)
                .contentsId(this.contentsId)
                .evaluationType(this.evaluationType)
                .comment(this.comment)
                .build();
        }

        @Override
        public String toString() {
            return "{"
                + "\"memberId\":" + memberId
                + ", \"contentsId\":" + contentsId
                + ", \"evaluationType\":\"" + evaluationType + "\""
                + ", \"comment\":\"" + comment + "\""
                + "}";
        }
    }

    @Getter
    @Builder
    public static class PricingModify {

        private String contentsToken; // 작품 대체 식별키
        private PricingType pricingType; // 가격 유형
        private Integer coin; // 금액

        @Override
        public String toString() {
            return "{"
                + "\"contentsToken\":\"" + contentsToken + "\""
                + ", \"pricingType\":\"" + pricingType + "\""
                + ", \"coin\":" + coin
                + "}";
        }
    }
}
