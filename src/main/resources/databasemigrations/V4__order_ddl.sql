create table orders (
  id bigint auto_increment primary key,
  delivery_address varchar(100) not null,
  status varchar(100) not null,
  amount int null
);