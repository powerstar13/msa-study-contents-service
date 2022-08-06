CREATE TABLE IF NOT EXISTS `contents`
(
    `contentsId`        BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '작품 고유번호',
    `contentsToken`     VARCHAR(36)     NOT NULL    COMMENT '작품 대체 식별키',
    `contentsName`      VARCHAR(255)    NOT NULL    COMMENT '작품명',
    `author`            VARCHAR(45)     NOT NULL    COMMENT '작가',
    `pricingType`       VARCHAR(45)     NOT NULL    COMMENT '가격 유형',
    `coin`              INT             NOT NULL    DEFAULT 0 COMMENT '금액',
    `adultOnly`         VARCHAR(45)     NOT NULL    DEFAULT 'N' COMMENT '성인물 여부',
    `openAt`            DATE            NOT NULL    COMMENT '서비스 제공일',
    `createdAt`         TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`         TIMESTAMP       NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (contentsId)
);

CREATE TABLE IF NOT EXISTS `evaluation`
(
    `evaluationId`      BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '평가 고유번호',
    `evaluationToken`   VARCHAR(36)     NOT NULL    COMMENT '평가 대체 식별키',
    `memberId`          BIGINT          NOT NULL    COMMENT '회원 고유번호',
    `contentsId`        BIGINT          NOT NULL    COMMENT '작품 고유번호',
    `evaluationType`    VARCHAR(45)     NOT NULL    COMMENT '평가 유형',
    `comment`           VARCHAR(255)    NULL        COMMENT '댓글',
    `createdAt`         TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`         TIMESTAMP       NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (evaluationId),
    CONSTRAINT FK_evaluation_contentsId_contents_contentsId FOREIGN KEY (contentsId)
        REFERENCES contents (contentsId) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff01', '일반작품1', '무료작가1', 'FREE', 0, 'N', '2022-08-03');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff02', '일반작품2', '무료작가2', 'FREE', 0, 'N', '2022-08-03');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff03', '일반작품3', '유료작가3', 'PAY', 100, 'N', '2022-08-04');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff04', '일반작품4', '유료작가4', 'PAY', 300, 'N', '2022-08-04');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff05', '일반작품5', '유료작가5', 'PAY', 500, 'N', '2022-08-05');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff06', '성인작품6', '무료작가6', 'FREE', 0, 'Y', '2022-08-05');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff07', '성인작품7', '무료작가7', 'FREE', 0, 'Y', '2022-08-06');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff08', '성인작품8', '유료작가8', 'PAY', 100, 'Y', '2022-08-06');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff09', '성인작품9', '유료작가9', 'PAY', 300, 'Y', '2022-08-07');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff10', '성인작품10', '유료작가10', 'PAY', 500, 'Y', '2022-08-07');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff11', '무료로 변경할 작품', '수정작가', 'PAY', 500, 'Y', '2022-08-08');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff12', '유료로 변경할 작품', '수정작가', 'FREE', 0, 'N', '2022-08-08');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff13', '좋아요 Top1 작품', '좋아요Top1작가', 'FREE', 0, 'N', '2022-08-09');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff14', '좋아요 Top2 작품', '좋아요Top2작가', 'FREE', 0, 'N', '2022-08-09');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff15', '좋아요 Top3 작품', '좋아요Top3작가', 'FREE', 0, 'N', '2022-08-09');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff16', '싫어요 Top1 작품', '싫어요Top1작가', 'FREE', 0, 'N', '2022-08-09');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff17', '싫어요 Top2 작품', '싫어요Top2작가', 'FREE', 0, 'N', '2022-08-09');
INSERT INTO contents (contentsToken, contentsName, author, pricingType, coin, adultOnly, openAt)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff18', '싫어요 Top3 작품', '싫어요Top3작가', 'FREE', 0, 'N', '2022-08-09');

INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 2, 13, 'LIKE', '좋아요Top1작가 LIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 3, 13, 'LIKE', '좋아요Top1작가 LIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 4, 13, 'LIKE', '좋아요Top1작가 LIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 5, 14, 'LIKE', '좋아요Top2작가 LIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 6, 14, 'LIKE', '좋아요Top2작가 LIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 7, 15, 'LIKE', '좋아요Top3작가 LIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 2, 16, 'DISLIKE', '싫어요Top1작가 DISLIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 3, 16, 'DISLIKE', '싫어요Top1작가 DISLIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 4, 16, 'DISLIKE', '싫어요Top1작가 DISLIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 5, 17, 'DISLIKE', '싫어요Top2작가 DISLIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 6, 17, 'DISLIKE', '싫어요Top2작가 DISLIKE');
INSERT INTO evaluation (evaluationToken, memberId, contentsId, evaluationType, comment)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', 7, 18, 'DISLIKE', '싫어요Top3작가 DISLIKE');
