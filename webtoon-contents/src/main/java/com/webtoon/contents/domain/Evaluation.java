package com.webtoon.contents.domain;

import com.webtoon.contents.domain.shared.CommonDateEntity;
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
@Table(name = "evaluation")
public class Evaluation extends CommonDateEntity { // 평가

    @Id
    @Column(value = "evaluationId")
    private long evaluationId; // 평가 고유번호

    @Column(value = "evaluationToken")
    private String evaluationToken; // 평가 대체 식별키

    @Column(value = "memberId")
    private long memberId; // 회원 고유번호

    @Column(value = "contentsId")
    private long contentsId; // 작품 고유번호

    @Column(value = "evaluationType")
    private EvaluationType evaluationType; // 평가 유형

    @Column(value = "comment")
    private String comment; // 댓글

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"evaluationId\":" + evaluationId
            + ", \"evaluationToken\":\"" + evaluationToken + "\""
            + ", \"memberId\":" + memberId
            + ", \"contentsId\":" + contentsId
            + ", \"evaluationType\":\"" + evaluationType + "\""
            + ", \"comment\":\"" + comment + "\""
            + "}";
    }
}
