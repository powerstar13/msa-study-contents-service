package com.lezhin.member.domain;


import com.lezhin.member.domain.shared.CommonDateEntity;
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
@Table(name = "member")
public class Member extends CommonDateEntity { // 회원

    @Id
    @Column(value = "memberId")
    private long memberId; // 회원 고유번호

    @Column(value = "memberToken")
    private String memberToken; // 회원 대체 식별키

    @Column(value = "memberType")
    private MemberType memberType; // 회원 유형

    @Column(value = "memberEmail")
    private String memberEmail; // 회원 이메일

    @Column(value = "memberName")
    private String memberName; // 회원 이름

    @Column(value = "memberGender")
    private MemberGender memberGender; // 회원 성별

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"memberId\":" + memberId
            + ", \"memberToken\":\"" + memberToken + "\""
            + ", \"memberType\":\"" + memberType + "\""
            + ", \"memberEmail\":\"" + memberEmail + "\""
            + ", \"memberName\":\"" + memberName + "\""
            + ", \"memberGender\":\"" + memberGender + "\""
            + "}";
    }
}

