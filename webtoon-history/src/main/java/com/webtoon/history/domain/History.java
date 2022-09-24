package com.webtoon.history.domain;

import com.webtoon.history.domain.shared.CommonDateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history")
public class History extends CommonDateEntity { // 이력

    @Id
    @Column(value = "historyId")
    private long historyId; // 이력 고유번호

    @Column(value = "historyToken")
    private String historyToken; // 이력 대체 식별키

    @Column(value = "memberId")
    private long memberId; // 회원 고유번호

    @Column(value = "memberType")
    private MemberType memberType; // 회원 유형

    @Column(value = "memberEmail")
    private String memberEmail; // 회원 이메일

    @Column(value = "memberName")
    private String memberName; // 회원 이름

    @Column(value = "memberGender")
    private MemberGender memberGender; // 회원 성별

    @Column(value = "contentsId")
    private long contentsId; // 작품 고유번호

    @Column(value = "adultOnly")
    private AdultOnly adultOnly; // 성인물 여부

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"historyId\":" + historyId
            + ", \"historyToken\":\"" + historyToken + "\""
            + ", \"memberId\":" + memberId
            + ", \"memberType\":\"" + memberType + "\""
            + ", \"memberEmail\":\"" + memberEmail + "\""
            + ", \"memberName\":\"" + memberName + "\""
            + ", \"memberGender\":\"" + memberGender + "\""
            + ", \"contentsId\":" + contentsId
            + ", \"adultOnly\":\"" + adultOnly + "\""
            + "}";
    }
}
