create table users (
  id bigint auto_increment primary key,
  password varchar(100) null,
  username varchar(100) not null,
  constraint UK_username
    unique (username)
);