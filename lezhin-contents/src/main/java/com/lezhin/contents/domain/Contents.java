package com.lezhin.contents.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lezhin.contents.domain.shared.CommonDateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contents")
public class Contents extends CommonDateEntity { // 작품

    @Id
    @Column(value = "contentsId")
    private long contentsId; // 작품 고유번호

    @Column(value = "contentsToken")
    private String contentsToken; // 작품 대체 식별키

    @Column(value = "contentsName")
    private String contentsName; // 작품명

    @Column(value = "author")
    private String author; // 작가

    @Column(value = "pricingType")
    private PricingType pricingType; // 가격 유형

    @Column(value = "coin")
    private int coin; // 금액

    @Column(value = "adultOnly")
    private AdultOnly adultOnly; // 성인물 여부

    @Column(value = "openAt")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate openAt; // 서비스 제공일

    @Column(value = "likeCount")
    private long likeCount; // 좋아요 개수

    @Column(value = "dislikeCount")
    private long dislikeCount; // 싫어요 개수

    /**
     * 좋아요 추가
     */
    public void likeUp() {
        this.likeCount += 1;
    }

    /**
     * 싫어요 추가
     */
    public void dislikeUp() {
        this.dislikeCount += 1;
    }

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"contentsId\":" + contentsId
            + ", \"contentsToken\":\"" + contentsToken + "\""
            + ", \"contentsName\":\"" + contentsName + "\""
            + ", \"author\":\"" + author + "\""
            + ", \"pricingType\":\"" + pricingType + "\""
            + ", \"coin\":" + coin
            + ", \"adultOnly\":\"" + adultOnly + "\""
            + ", \"openAt\":" + openAt
            + ", \"likeCount\":" + likeCount
            + ", \"dislikeCount\":" + dislikeCount
            + "}";
    }
}
