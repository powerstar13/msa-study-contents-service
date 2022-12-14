package com.webtoon.history.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webtoon.history.domain.MemberGender;
import com.webtoon.history.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class HistoryDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentsHistoryMemberInfo {

        private String historyToken; // 이력 대체 식별키
        private MemberType memberType; // 회원 유형
        private String memberEmail; // 회원 이메일
        private String memberName; // 회원 이름
        private MemberGender memberGender; // 회원 성별
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING)
        private LocalDateTime createdAt; // 생성일

        @Override
        public String toString() {
            return "{"
                + "\"historyToken\":\"" + historyToken + "\""
                + ", \"memberType\":\"" + memberType + "\""
                + ", \"memberEmail\":\"" + memberEmail + "\""
                + ", \"memberName\":\"" + memberName + "\""
                + ", \"memberGender\":\"" + memberGender + "\""
                + ", \"createdAt\":\"" + createdAt + "\""
                + "}";
        }
    }

    @Getter
    @Builder
    public static class ContentsHistoryPage {

        private pageInfo pageInfo; // 페이지 정보
        private List<ContentsHistoryMemberInfo> historyList; // 이력 목록

        @Override
        public String toString() {
            return "{"
                + "\"pageInfo\":" + pageInfo
                + ", \"historyList\":" + historyList
                + "}";
        }
    }

    @Getter
    @Builder
    public static class SearchHistoryMemberInfo {

        private MemberType memberType; // 회원 유형
        private String memberEmail; // 회원 이메일
        private String memberName; // 회원 이름
        private MemberGender memberGender; // 회원 성별

        @Override
        public String toString() {
            return "{"
                + "\"memberType\":\"" + memberType + "\""
                + ", \"memberEmail\":\"" + memberEmail + "\""
                + ", \"memberName\":\"" + memberName + "\""
                + ", \"memberGender\":\"" + memberGender + "\""
                + "}";
        }
    }

    @Getter
    @Builder
    public static class SearchHistoryPage {

        private pageInfo pageInfo; // 페이지 정보
        private List<SearchHistoryMemberInfo> memberList; // 회원 목록

        @Override
        public String toString() {
            return "{"
                + "\"pageInfo\":" + pageInfo
                + ", \"memberList\":" + memberList
                + "}";
        }
    }

    @Getter
    @Builder
    public static class pageInfo {

        private int currentSize; // 현재 페이지의 데이터 수
        private int currentPage; // 현재 페이지 번호
        private long totalCount; // 전체 데이터 수
        private int totalPage; // 총 페이지 수

        @Override
        public String toString() {
            return "{"
                + "\"currentSize\":" + currentSize
                + ", \"currentPage\":" + currentPage
                + ", \"totalCount\":" + totalCount
                + ", \"totalPage\":" + totalPage
                + "}";
        }
    }
}
