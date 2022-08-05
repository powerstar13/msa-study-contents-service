package com.lezhin.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberIdInfo {

        private long memberId; // 회원 고유번호

        @Override
        public String toString() {
            return "{"
                + "\"memberId\":" + memberId
                + "}";
        }
    }
}
