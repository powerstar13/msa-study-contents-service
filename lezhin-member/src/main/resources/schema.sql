CREATE TABLE IF NOT EXISTS member
(
    `memberId`        BIGINT                      NOT NULL    AUTO_INCREMENT COMMENT '회원 고유번호',
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
VALUES ('492bc213-5c47-422f-9390-1b0c2206bcb1', 'ADMIN', 'admin1@gmail.com', '레진관리자1', 'M');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('5df1d272-57fa-4063-ae9d-abd64c9cc072', 'NORMAL', 'normal2@gmail.com', '레진일반사용자2', 'W');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95abf3', 'NORMAL', 'normal3@gmail.com', '레진일반사용자3', 'W');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('5df1d272-57fa-4063-ae9d-abd64c9cc074', 'NORMAL', 'normal4@gmail.com', '레진일반사용자4', 'M');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95abf5', 'NORMAL', 'normal5@gmail.com', '레진일반사용자5', 'M');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('5df1d272-57fa-4063-ae9d-abd64c9cc076', 'NORMAL', 'normal6@gmail.com', '레진일반사용자6', 'W');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95abf7', 'NORMAL', 'normal7@gmail.com', '레진일반사용자7', 'W');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('5df1d272-57fa-4063-ae9d-abd64c9cc078', 'ADULT', 'adult8@gmail.com', '레진성인사용자8', 'M');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95abf9', 'ADULT', 'adult9@gmail.com', '레진성인사용자9', 'M');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('5df1d272-57fa-4063-ae9d-abd64c9cc010', 'ADULT', 'adult10@gmail.com', '레진성인사용자10', 'W');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab11', 'ADULT', 'adult11@gmail.com', '레진성인사용자11', 'W');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('5df1d272-57fa-4063-ae9d-abd64c9cc012', 'ADULT', 'adult12@gmail.com', '레진성인사용자12', 'M');
INSERT INTO member (memberToken, memberType, memberEmail, memberName, memberGender)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab13', 'ADULT', 'adult13@mail.com', '레진성인사용자13', 'M');
