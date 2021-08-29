CREATE DATABASE IF NOT EXISTS test default charset utf8 COLLATE utf8_general_ci;

use test;

DROP TABLE IF EXISTS tbl_employee;
CREATE TABLE tbl_employee (
  id int(11) NOT NULL AUTO_INCREMENT,
  last_name varchar(255) DEFAULT NULL,
  gender char(1) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  d_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


INSERT INTO tbl_employee VALUES ('1', 'zhang', 'm', '123@163.com', null);
INSERT INTO tbl_employee VALUES ('3', 'lisi', 'm', 'lisi@sina.cn', null);
INSERT INTO tbl_employee VALUES ('4', 'lisi', 'm', 'lisi@sina.cn', null);
INSERT INTO tbl_employee VALUES ('8', 'lisi', 'm', 'lisi@sina.cn', null);
INSERT INTO tbl_employee VALUES ('9', 'lisi', 'm', 'lisi@sina.cn', null);

create table tbl_dept(
    id int(11) NOT NULL AUTO_INCREMENT
   ,dept_name varchar(255)
   ,PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;;

INSERT INTO tbl_dept VALUES(id, dept_name) values(1, '开发部');
INSERT INTO tbl_dept VALUES(2, '测试部');

alter table tbl_employee add column d_id int(11);

alter table tbl_employee add constraint fk_emp_dept
foreign key(d_id) references tbl_dept(id);