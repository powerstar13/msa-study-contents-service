package com.lezhin.member.presentation.response;

import com.lezhin.member.presentation.shared.response.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeMemberTokenResponse extends SuccessResponse {

    private long memberId; // 회원 고유번호

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"memberId\":" + memberId
            + "}";
    }
}
