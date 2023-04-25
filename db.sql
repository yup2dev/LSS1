DROP DATABASE IF EXISTS LSS;
CREATE DATABASE LSS;

# DB 선택
USE LSS;

# 게시물 테이블 생성
CREATE TABLE article (
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
memberId INT NOT NULL,
`body` TEXT NOT NULL,
hit INT(10) UNSIGNED NOT NULL
);

CREATE TABLE articlecomment(
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
articleId INT UNSIGNED NOT NULL,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
`comment` TEXT NOT NULL,
memberId INT NOT NULL
);

# 회원 테이블 생성
CREATE TABLE `member` (
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
loginId CHAR(20) NOT NULL,
loginPw CHAR(100) NOT NULL,
`name` CHAR(200) NOT NULL,
nickname CHAR(20) NOT NULL,
birth CHAR(20) NOT NULL
);


CREATE TABLE content (
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
TYPE VARCHAR(30) NOT NULL,
content_name VARCHAR(30) NOT NULL UNIQUE,
location VARCHAR(30) NOT NULL,
TIME TIME NOT NULL,
`monday` BOOLEAN NOT NULL, `tuesday` BOOLEAN NOT NULL, `wednesday` BOOLEAN NOT NULL,
`thursday` BOOLEAN NOT NULL, `friday` BOOLEAN NOT NULL, `saturday` BOOLEAN NOT NULL,
`sunday` BOOLEAN NOT NULL, `select` BOOLEAN NOT NULL
);


INSERT INTO articlecomment
SET articleId = 1,
regDate = NOW(),
updateDate = NOW(),
`comment` = '댓글1',
memberId = 1;

SELECT articlecomment.comment, articlecomment.articleId, article.id FROM articlecomment
INNER JOIN article
ON articlecomment.articleId = article.id
WHERE article.id = 1

SELECT * FROM article;
SELECT * FROM `member`;
SELECT * FROM articlecomment;
SELECT articlecomment.articleid, articlecomment.comment FROM articlecomment;

INSERT INTO content
SET TYPE = '섬', content_name = '잔혹한 장난감 섬', location = '베른 북쪽',
TIME = '210000', monday = FALSE, tuesday = FALSE, wednesday = TRUE,
thursday = FALSE, friday = FALSE, saturday = FALSE, sunday = TRUE;

INSERT INTO content
SET TYPE = '섬', content_name = '우거진 갈대의 숲', location = '아르데타인 밑바다',
TIME = '130000', monday = TRUE, tuesday = FALSE, wednesday = FALSE,
thursday = TRUE, friday = FALSE, saturday = FALSE, sunday = TRUE;

INSERT INTO content
SET TYPE = '섬', content_name = '수라도', location = '슈사이어 왼쪽 밑',
TIME = '190000', monday = FALSE, tuesday = TRUE, wednesday = FALSE,
thursday = FALSE, friday = TRUE, saturday = TRUE, sunday = FALSE;

INSERT INTO content
SET TYPE = '섬', content_name = '환영나비 섬', location = '파푸니카 위쪽 바다',
TIME = '110000', monday = TRUE, tuesday = FALSE, wednesday = TRUE,
thursday = FALSE, friday = TRUE, saturday = FALSE, sunday = FALSE;

INSERT INTO content
SET TYPE = '섬', content_name = '고요한 안식의 섬', location = '페이튼 바다',
TIME = '210000', monday = FALSE, tuesday = TRUE, wednesday = FALSE,
thursday = TRUE, friday = FALSE, saturday = TRUE, sunday = FALSE;


INSERT INTO content
SET TYPE = '섬', content_name = '포르페', location = '애니츠 앞바다',
TIME = '230000', monday = FALSE, tuesday = FALSE, wednesday = FALSE,
thursday = FALSE, friday = FALSE, saturday = TRUE, sunday = TRUE;

INSERT INTO content
SET TYPE = '섬', content_name = '스피다 섬', location = '베른 북쪽 바다',
TIME = '130000', monday = FALSE, tuesday = TRUE, wednesday = FALSE,
thursday = FALSE, friday = TRUE, saturday = FALSE, sunday = FALSE;

INSERT INTO content
SET TYPE = '섬', content_name = '환각의 섬', location = '슈사이어 세이렌 해역',
TIME = '110000', monday = TRUE, tuesday = FALSE, wednesday = TRUE,
thursday = FALSE, friday = FALSE, saturday = FALSE, sunday = FALSE;


INSERT INTO content
SET TYPE = '섬', content_name = '에라스모', location = '루테란 윗 바다',
TIME = '190000', monday = FALSE, tuesday = FALSE, wednesday = FALSE,
thursday = TRUE, friday = FALSE, saturday = FALSE, sunday = FALSE;

INSERT INTO content
SET TYPE = '필드보스', content_name = '타르실라', location = '슈사이어',
TIME = '150000', monday = TRUE, tuesday = FALSE, wednesday = FALSE,
thursday = TRUE, friday = FALSE, saturday = FALSE, sunday = FALSE;

INSERT INTO content
SET TYPE = '필드보스', content_name = '혼재의 추오', location = '아르데타인',
TIME = '150000', monday = FALSE, tuesday = TRUE, wednesday = FALSE,
thursday = FALSE, friday = TRUE, saturday = FALSE, sunday = FALSE;


INSERT INTO content
SET TYPE = '필드보스', content_name = '하르마게돈', location = '베른 북부',
TIME = '150000', monday = FALSE, tuesday = FALSE, wednesday = TRUE,
thursday = FALSE, friday = FALSE, saturday = TRUE, sunday = FALSE;


INSERT INTO content
SET TYPE = '필드보스', content_name = '티파니', location = '욘',
TIME = '150000', monday = FALSE, tuesday = FALSE, wednesday = FALSE,
thursday = FALSE, friday = FALSE, saturday = FALSE, sunday = TRUE;

INSERT INTO content
SET TYPE = '카오스게이트', content_name = '카오스게이트', location = '필드',
TIME = '190000', monday = TRUE, tuesday = FALSE, wednesday = FALSE,
thursday = TRUE, friday = FALSE, saturday = TRUE, sunday = TRUE;

INSERT INTO content
SET TYPE = '유령선', content_name = '유령선', location = '필드',
TIME = '210000', monday = FALSE, tuesday = TRUE, wednesday = TRUE,
thursday = TRUE, friday = TRUE, saturday = TRUE, sunday = FALSE;

INSERT INTO content
SET TYPE = '유령선', content_name = '유령선1', location = '필드',
TIME = '210000', monday = FALSE, tuesday = TRUE, wednesday = TRUE,
thursday = TRUE, friday = TRUE, saturday = TRUE, sunday = FALSE;

SELECT * FROM article;
SELECT * FROM `member`;