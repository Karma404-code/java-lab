
CREATE SCHEMA IF NOT EXISTS school;
SET SCHEMA school;

CREATE TABLE IF NOT EXISTS student(
    id int AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    email varchar(50),
    batch int,
    gender varchar(6)
);

CREATE TABLE IF NOT EXISTS account
(
    id long AUTO_INCREMENT PRIMARY KEY,
    username varchar(50),
    password varchar(50)
);

insert into student (name, email, batch, gender)
values ('Rehan Boyaju', 'reytan@gmail.com', 2022, 'male');

insert into account (username, password)
values ('admin', 'admin');

