/*=============================================================================*/
/* 신규 DB, 사용자 생성, root 권한 설정
/*=============================================================================*/
CREATE DATABASE myhub;

CREATE USER kbtapjm IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON myhub.* TO 'kbtapjm'@'%' WITH GRANT OPTION;


/*=============================================================================*/
/* Table Create 
/*=============================================================================*/

/* 사용자 */
CREATE TABLE `user` (
    `userkey` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `birthday` VARCHAR(8) NOT NULL,
    `crtDt` DATETIME NULL DEFAULT NULL,
    `email` VARCHAR(50) NOT NULL,
    `gender` VARCHAR(255) NOT NULL,
    `lastPassword` VARCHAR(100) NOT NULL,
    `loginFailCount` INT(11) NOT NULL,
    `loginFailDt` DATETIME NULL DEFAULT NULL,
    `modDt` DATETIME NULL DEFAULT NULL,
    `password` VARCHAR(100) NOT NULL,
    `passwordModDt` DATETIME NULL DEFAULT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `username` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`userkey`),
    UNIQUE INDEX `email` (`email`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2;

CREATE TABLE `userauth` (
    `userAuthKey` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `crtDt` DATETIME NULL DEFAULT NULL,
    `email` VARCHAR(50) NOT NULL,
    `priv` INT(11) NOT NULL,
    `userKey` BIGINT(20) NOT NULL,
    PRIMARY KEY (`userAuthKey`),
    UNIQUE INDEX `email` (`email`),
    INDEX `FKF3EFC433BE330993` (`userKey`),
    CONSTRAINT `FKF3EFC433BE330993` FOREIGN KEY (`userKey`) REFERENCES `user` (`userkey`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2;


/* 로그인 이력 */
CREATE TABLE `loginlog` (
    `loginlogkey` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(50) NOT NULL,
    `ipaddress` VARCHAR(100) NOT NULL,
    `logindate` DATETIME NULL DEFAULT NULL,
    `logoutdate` DATETIME NULL DEFAULT NULL,
    `userKey` BIGINT(20) NOT NULL,
    PRIMARY KEY (`loginlogkey`),
    INDEX `FK7890971BBE330993` (`userKey`),
    CONSTRAINT `FK7890971BBE330993` FOREIGN KEY (`userKey`) REFERENCES `user` (`userkey`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4;
