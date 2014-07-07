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
    `phoneNo` VARCHAR(11) NOT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `username` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`userkey`),
    UNIQUE INDEX `email` (`email`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=13;

CREATE TABLE `userauth` (
    `userkey` BIGINT(20) NOT NULL,
    `crtDt` DATETIME NULL DEFAULT NULL,
    `email` VARCHAR(50) NOT NULL,
    `priv` INT(11) NOT NULL,
    PRIMARY KEY (`userkey`),
    UNIQUE INDEX `userkey` (`userkey`),
    UNIQUE INDEX `email` (`email`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

/* 로그 이력 */
CREATE TABLE `loghistory` (
    `logHistoryKey` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(50) NOT NULL,
    `ipAddress` VARCHAR(100) NOT NULL,
    `logDate` DATETIME NULL DEFAULT NULL,
    `logType` VARCHAR(255) NOT NULL,
    `userKey` BIGINT(20) NOT NULL,
    `sessionId` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`logHistoryKey`),
    INDEX `FKBEB964B0BE330993` (`userKey`),
    CONSTRAINT `FKBEB964B0BE330993` FOREIGN KEY (`userKey`) REFERENCES `user` (`userkey`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=136;
