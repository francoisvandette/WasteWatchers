CREATE DATABASE wastewatchers;
USE wastewatchers;
CREATE TABLE users (
	userID int PRIMARY KEY auto_increment, 
    username varchar(50) not null unique,
    email varchar(200) not null unique,
    pswd varchar(255) not null,
    verified boolean default TRUE,
    hash varchar(255) not null unique
    );
