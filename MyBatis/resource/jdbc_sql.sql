DROP TABLE IF EXISTS jdbc_database;

CREATE DATABASE jdbc_database DEFAULT CHARACTER SET utf8;

USE jdbc_database;

CREATE TABLE IF NOT EXISTS t_stu
(
    id int(11) primary key auto_increment,
    name varchar(255),
    age  int
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


--带有输入参数的存储过程
DELIMITER $
CREATE PROCEDURE pro_findById(IN uid INT)
BEGIN
	SELECT * FROM t_stu WHERE id = uid;
END $

CALL pro_findById(1);


--带有输出参数的存储过程
DELIMITER $
CREATE PROCEDURE pro_findById2(IN uid INT, OUT uname VARCHAR(20))
BEGIN
	SELECT name INTO uname FROM t_stu WHERE id = uid;
END $

CALL pro_findById2(1, @uname);
SELECT @uname;
END 