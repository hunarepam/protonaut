create table persons (
  id bigint auto_increment primary key,
  firstname varchar(100) not null,
  lastname varchar(100) not null,
  sex varchar(10) not null,
  age int null
);