CREATE TABLE IF NOT EXISTS member
(
    `memberId`        INT                         NOT NULL    AUTO_INCREMENT COMMENT '회원 고유번호',
    `memberToken`     VARCHAR(36)                 NOT NULL    COMMENT '회원 대체 식별키',
    `memberType`      VARCHAR(45)                 NOT NULL    COMMENT '회원 유형',
    `memberEmail`     VARCHAR(45)                 NOT NULL    COMMENT '회원 이메일',
    `memberName`      VARCHAR(45)                 NOT NULL    COMMENT '회원 이름',
    `memberGender`    VARCHAR(45)                 NOT NULL    COMMENT '회원 성별',
    `createdAt`       TIMESTAMP                   NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`       TIMESTAMP                   NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (memberId)
);

INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('492bc213-5c47-422f-9390-1b0c2206bcbe', 'ADMIN', 'admin@gmail.com', '레진관리자', 'M');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('5df1d272-57fa-4063-ae9d-abd64c9cc070', 'NORMAL', 'normal@gmail.com', '레진일반사용자', 'W');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95abf8', 'ADULT', 'adult@gmail.com', '레진성인사용자', 'M');
