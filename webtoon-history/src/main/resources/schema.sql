CREATE TABLE IF NOT EXISTS history
(
    `historyId`         BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '이력 고유번호',
    `historyToken`      VARCHAR(36)     NOT NULL    COMMENT '이력 대체 식별키',
    `memberId`          BIGINT          NOT NULL    COMMENT '회원 고유번호',
    `memberType`        VARCHAR(45)     NOT NULL    COMMENT '회원 유형',
    `memberEmail`       VARCHAR(45)     NOT NULL    COMMENT '회원 이메일',
    `memberName`        VARCHAR(45)     NOT NULL    COMMENT '회원 이름',
    `memberGender`      VARCHAR(45)     NOT NULL    COMMENT '회원 성별',
    `contentsId`        BIGINT          NOT NULL    COMMENT '작품 고유번호',
    `adultOnly`         VARCHAR(45)     NOT NULL    DEFAULT 'N' COMMENT '성인물 여부',
    `createdAt`         TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`         TIMESTAMP       NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (historyId)
);

INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 2, 'NORMAL', 'normal2@gmail.com', '웹툰일반사용자2', 'W', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 3, 'NORMAL', 'normal3@gmail.com', '웹툰일반사용자3', 'W', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 4, 'NORMAL', 'normal4@gmail.com', '웹툰일반사용자4', 'M', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 5, 'NORMAL', 'normal5@gmail.com', '웹툰일반사용자5', 'M', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 6, 'NORMAL', 'normal6@gmail.com', '웹툰일반사용자6', 'W', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 7, 'NORMAL', 'normal7@gmail.com', '웹툰일반사용자7', 'W', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 8, 'ADULT', 'adult8@gmail.com', '웹툰성인사용자8', 'M', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 9, 'ADULT', 'adult9@gmail.com', '웹툰성인사용자9', 'M', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 10, 'ADULT', 'adult10@gmail.com', '웹툰성인사용자10', 'W', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 11, 'ADULT', 'adult11@gmail.com', '웹툰성인사용자11', 'W', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 12, 'ADULT', 'adult12@gmail.com', '웹툰성인사용자12', 'M', 1, 'N');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 13, 'ADULT', 'adult13@gmail.com', '웹툰성인사용자13', 'M', 1, 'N');

INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 8, 'ADULT', 'adult8@gmail.com', '웹툰성인사용자8', 'M', 6, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 9, 'ADULT', 'adult9@gmail.com', '웹툰성인사용자9', 'M', 6, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 10, 'ADULT', 'adult10@gmail.com', '웹툰성인사용자10', 'W', 6, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 11, 'ADULT', 'adult11@gmail.com', '웹툰성인사용자11', 'W', 6, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 12, 'ADULT', 'adult12@gmail.com', '웹툰성인사용자12', 'M', 6, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 13, 'ADULT', 'adult13@gmail.com', '웹툰성인사용자13', 'M', 6, 'Y');

INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 8, 'ADULT', 'adult8@gmail.com', '웹툰성인사용자8', 'M', 7, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 9, 'ADULT', 'adult9@gmail.com', '웹툰성인사용자9', 'M', 7, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 10, 'ADULT', 'adult10@gmail.com', '웹툰성인사용자10', 'W', 7, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 11, 'ADULT', 'adult11@gmail.com', '웹툰성인사용자11', 'W', 7, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 12, 'ADULT', 'adult12@gmail.com', '웹툰성인사용자12', 'M', 7, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 13, 'ADULT', 'adult13@gmail.com', '웹툰성인사용자13', 'M', 7, 'Y');

INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 8, 'ADULT', 'adult8@gmail.com', '웹툰성인사용자8', 'M', 8, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 9, 'ADULT', 'adult9@gmail.com', '웹툰성인사용자9', 'M', 8, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 10, 'ADULT', 'adult10@gmail.com', '웹툰성인사용자10', 'W', 8, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 11, 'ADULT', 'adult11@gmail.com', '웹툰성인사용자11', 'W', 8, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 12, 'ADULT', 'adult12@gmail.com', '웹툰성인사용자12', 'M', 8, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 13, 'ADULT', 'adult13@gmail.com', '웹툰성인사용자13', 'M', 8, 'Y');

INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 8, 'ADULT', 'adult8@gmail.com', '웹툰성인사용자8', 'M', 9, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 9, 'ADULT', 'adult9@gmail.com', '웹툰성인사용자9', 'M', 9, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 10, 'ADULT', 'adult10@gmail.com', '웹툰성인사용자10', 'W', 9, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 11, 'ADULT', 'adult11@gmail.com', '웹툰성인사용자11', 'W', 9, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 12, 'ADULT', 'adult12@gmail.com', '웹툰성인사용자12', 'M', 9, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 13, 'ADULT', 'adult13@gmail.com', '웹툰성인사용자13', 'M', 9, 'Y');

INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 8, 'ADULT', 'adult8@gmail.com', '웹툰성인사용자8', 'M', 10, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 9, 'ADULT', 'adult9@gmail.com', '웹툰성인사용자9', 'M', 10, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 10, 'ADULT', 'adult10@gmail.com', '웹툰성인사용자10', 'W', 10, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 11, 'ADULT', 'adult11@gmail.com', '웹툰성인사용자11', 'W', 10, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 12, 'ADULT', 'adult12@gmail.com', '웹툰성인사용자12', 'M', 10, 'Y');
INSERT INTO history (historyToken, memberId, memberType, memberEmail, memberName, memberGender, contentsId, adultOnly)
VALUES (UUID(), 13, 'ADULT', 'adult13@gmail.com', '웹툰성인사용자13', 'M', 10, 'Y');
