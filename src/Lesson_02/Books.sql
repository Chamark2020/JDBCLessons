use jdbclessons;
create table if not exists Books (bookId int not null AUTO_INCREMENT primary key, name varchar(25) not null, price double not null);
insert into Books (name, price) value ('Inferno', 55.0);
insert into Books (name, price) value ('Harry Potter', 50.0);
insert into Books (name, price) value ('It', 25.0);
insert into Books (name, price) value ('Spartacus', 30.0);
insert into Books (name, price) value ('Green Mile', 105.0);
insert into Books (name, price) value ('Solomon Key', 5.0);