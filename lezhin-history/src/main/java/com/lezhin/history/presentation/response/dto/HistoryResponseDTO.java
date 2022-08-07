package com.lezhin.history.presentation.response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lezhin.history.domain.MemberGender;
import com.lezhin.history.domain.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class HistoryResponseDTO {

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
}
