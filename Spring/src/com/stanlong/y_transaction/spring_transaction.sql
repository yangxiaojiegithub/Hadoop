create database spring_transaction;
use spring_transaction;
create table account(
    id int(11) primary key auto_increment,
    username varchar(255),
    money int(11)
);

insert into account(username, money) values('zhangsan', 10000);
insert into account(username, money) values('lisi', 8000);