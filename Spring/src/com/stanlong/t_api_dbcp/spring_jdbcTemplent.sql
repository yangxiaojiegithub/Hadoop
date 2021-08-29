create database spring_jdbcTemplent;
use spring_jdbcTemplent;
create table tb_user(
    id int(11) primary key auto_increment,
    username varchar(255),
    password varchar(36)
);