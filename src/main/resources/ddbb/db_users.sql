drop database if exists DB_USERS;
create database DB_USERS;
use DB_USERS;

create table users(
                      id int auto_increment,
                      username varchar(30) not null,
                      psd blob not null,

                      primary key(id)
)engine innodb;


INSERT INTO `db_users`.`users` (`username`, `psd`) VALUES ('admin', 0xefbfbd6976efbfbd410415efbfbdefbfbd08efbfbd4defbfbd15dfb167efbfbdefbfbd73efbfbd4befbfbdefbfbd1f6f2aefbfbd48efbfbd18);
INSERT INTO `db_users`.`users` (`username`, `psd`) VALUES ('admin',_binary 'ŒivåµA½é½Mîß±g©ÈsüK¸¨o*´H©');