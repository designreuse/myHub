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
    `userkey` INT(11) NOT NULL AUTO_INCREMENT,
    `birthday` VARCHAR(8) NOT NULL,
    `crtdt` DATETIME NULL DEFAULT NULL,
    `email` VARCHAR(50) NOT NULL,
    `gender` VARCHAR(255) NOT NULL,
    `modDt` DATETIME NULL DEFAULT NULL,
    `password` VARCHAR(12) NOT NULL,
    `priv` INT(11) NOT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `username` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`userkey`),
    UNIQUE INDEX `userkey` (`userkey`),
    UNIQUE INDEX `email` (`email`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


/* 로그인 이력 */