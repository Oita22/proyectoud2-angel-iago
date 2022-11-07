drop database if exists DB_NEWS;
create database DB_NEWS;
use DB_NEWS;
    
create table news(
	category varchar(150) not null,

	author varchar(150) not null,
    content text not null,
    `date` varchar(150) not null,
    id varchar(150) not null,
    imageUrl varchar(150) not null,
    readMoreUrl text,
    `time` varchar(150) not null,
    title varchar(150) not null,
    url varchar(150) not null,
                            
    primary key(category, id)
)engine innodb;



drop database if exists DB_USERS;
create database DB_USERS;
use DB_USERS;

create table users(
    id int auto_increment,
    username varchar(30) not null,
    psd text,

    primary key(id)
)engine innodb;