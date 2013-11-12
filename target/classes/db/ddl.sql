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
    `userkey` INT NOT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `password` VARCHAR(12) NOT NULL,
    `username` VARCHAR(100) NOT NULL,
    `birthday` CHAR(8) NULL DEFAULT NULL,
    `gender` CHAR(1) NULL DEFAULT NULL,
    `priv` INT NOT NULL,
    PRIMARY KEY (`userid`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT

/* 로그인 이력 */