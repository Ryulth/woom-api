--
-- User database `woom_test`
--
CREATE DATABASE IF NOT EXISTS `woom_test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `woom_test`;
--
-- Table structure `User`
--
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
    `id` bigint(20) NOT NULL auto_increment,
    `hasCategorySet` longtext,
    `interestedCategorySet` longtext,
    `loginType` VARCHAR(255),
    `publicEmail` VARCHAR(255),
    `firstName` VARCHAR(255),
    `lastName` VARCHAR(255),
    `nickName` VARCHAR(255),
    `woomId` VARCHAR(255),
    PRIMARY KEY (`id`)
) engine=InnoDB;
--
-- Table structure `EmailUser`
--
DROP TABLE IF EXISTS `EmailUser`;
CREATE TABLE `EmailUser` (
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255),
    `userId` bigint NOT NULL,
    PRIMARY KEY (`email`)
) engine=InnoDB;
--
-- Table structure `AppleUser`
--
DROP TABLE IF EXISTS `AppleUser`;
CREATE TABLE `AppleUser` (
    `appleId` VARCHAR(255) NOT NULL,
    `appleEmail` VARCHAR(255),
    `lastAccessToken` VARCHAR(255),
    `userId` bigint NOT NULL,
    PRIMARY KEY (`appleId`)
) engine=InnoDB;
--
-- Table structure `AppleUser`
--
DROP TABLE IF EXISTS `KakaoUser`;
CREATE TABLE `KakaoUser` (
    `kakaoId` VARCHAR(255) NOT NULL,
    `kakaoEmail` VARCHAR(255),
    `lastAccessToken` VARCHAR(255),
    `userId` bigint NOT NULL,
    PRIMARY KEY (`kakaoId`)
) engine=InnoDB;
--
-- Table structure `Category`
--
DROP TABLE IF EXISTS `Category`;
CREATE TABLE `Category` (
    `categoryCode` VARCHAR(255) NOT NULL,
    `upperCategoryCode` VARCHAR(255),
    `description` VARCHAR(255),
    `userCount` bigint DEFAULT 0,
    `postCount` bigint DEFAULT 0,
    PRIMARY KEY (`categoryCode`)
) engine=InnoDB;
INSERT INTO `Category` (`categoryCode`, `upperCategoryCode`, `description`)
values
('LANGUAGE', null, '언어'),
('DESIGN', null, '디자인'),
('IT', null, 'IT지식'),
('MUSIC', null, '음악'),
('DANCE', null, '춤'),
('COOKING', null, '요리/음료'),
('BEAUTY', null, '뷰티'),
('EXERCISE', null, '운동'),
('KNOWLEDGE', null, '지식'),
('ENGLISH', 'LANGUAGE', '영어'),
('CHINESE', 'LANGUAGE', '중국어'),
('JAPANESE', 'LANGUAGE', '일본어'),
('GERMAN', 'LANGUAGE', '독일어'),
('RUSSIAN', 'LANGUAGE', '러시아어'),
('VIETNAMESE', 'LANGUAGE', '베트남어'),
('SPANISH', 'LANGUAGE', '스페인어'),
('ITALIAN', 'LANGUAGE', '이탈리아어'),
('INDONESIAN', 'LANGUAGE', '인도네시아어'),
('FRENCH', 'LANGUAGE', '프랑스어'),
('THAI', 'LANGUAGE', '태국어'),
('KOREAN', 'LANGUAGE', '한국어'),
('LANGUAGE_ETC', 'LANGUAGE', '기타'),
('PHOTOSHOP', 'DESIGN', '포토샵'),
('ILLUSTRATION', 'DESIGN', '일러스트'),
('VIDEO_EDITING', 'DESIGN', '영상 편집'),
('3D_MODELING', 'DESIGN', '3D 모델링'),
('WEBTOON_CHARACTER', 'DESIGN', '웹툰/캐릭터'),
('CLOTHING_DESIGN', 'DESIGN', '의류 디자인'),
('WEB_APP_DESIGN', 'DESIGN', '웹/앱 디자인'),
('INTERIOR', 'DESIGN', '인테리어'),
('EDIT_DESIGN', 'DESIGN', '편집 디자인'),
('DRAWING', 'DESIGN', '드로잉'),
('CALLIGRAPHY', 'DESIGN', '캘리그래피'),
('DESIGN_ETC', 'DESIGN', '기타'),
('PROGRAMMING_LANGUAGE', 'IT', '프로그래밍 언어'),
('WEB_PUBLISHING', 'IT', '웹 퍼블리싱'),
('FRONTEND', 'IT', '프론트엔드'),
('BACKEND', 'IT', '백엔드'),
('DEVOPS', 'IT', '데브옵스'),
('APP', 'IT', '앱'),
('BIG_DATA', 'IT', '빅 데이터'),
('GAME_DEVELOPMENT', 'IT', '게임 개발'),
('DATABASE', 'IT', '데이터베이스'),
('AI', 'IT', '인공지능'),
('IT_ETC', 'IT', '기타'),
('INSTRUMENT', 'MUSIC', '악기'),
('VOCAL', 'MUSIC', '보컬'),
('DJING', 'MUSIC', '성악'),
('COMPOSITION_LYRICS', 'MUSIC', '작곡/작사'),
('RAP', 'MUSIC', '랩'),
('VOCALIST', 'MUSIC', '성악'),
('MUSIC_ETC', 'MUSIC', '기타'),
('IDOL_DANCE', 'DANCE', '아이돌 댄스'),
('HIP_HOP_DANCE', 'DANCE', '힙합 댄스'),
('SPORTS_DANCE', 'DANCE', '스포츠 댄스'),
('DANCE_ETC', 'DANCE', '기타'),
('KOREAN_FOOD', 'COOKING', '한식'),
('CHINESE_FOOD', 'COOKING', '중식'),
('JAPANESE_FOOD', 'COOKING', '일식'),
('WESTERN_FOOD', 'COOKING', '양식'),
('BARISTA', 'COOKING', '바리스타'),
('COOKING_ETC', 'COOKING', '기타'),
('MAKE_UP', 'BEAUTY', '메이크업'),
('HAIR', 'BEAUTY', '헤어'),
('NAIL_ART', 'BEAUTY', '네일 아트'),
('FASHION', 'BEAUTY', '패션'),
('BEAUTY_ETC', 'BEAUTY', '기타'),
('HEALTH_PT', 'EXERCISE', '헬스/PT'),
('PILATES_YOGA', 'EXERCISE', 'PILATES_YOGA'),
('EXERCISE_ETC', 'EXERCISE', '기타'),
('WRITING', 'KNOWLEDGE', '글쓰기'),
('FINANCIAL_TECH', 'KNOWLEDGE', '재테크'),
('REAL_ESTATE', 'KNOWLEDGE', '부동산'),
('CAREER', 'KNOWLEDGE', '커리어'),
('KNOWLEDG_ETC', 'KNOWLEDGE', '기타'),
('TEST_UPPER', null, '테스트'),
('TEST_LOWER', 'TEST_UPPER', '테스트');
