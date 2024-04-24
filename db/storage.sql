DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;

DROP TABLE IF EXISTS product;

CREATE TABLE product (
                         code int primary key auto_increment default 0,
                         productName varchar(20) not null,
                         details varchar(200) not null,
                         quantity int default 0 not null,
                         category varchar(8) check(category in('Film', 'Anime', 'Fumetti', 'Giochi', 'Serie TV')) not null,
                         price float default 0 not null,
                         iva int default 0 not null,
                         discount int default 0
);

INSERT INTO product (productName, details, quantity, category, price, iva) VALUES ('Crash Bandicoot', 'Crash Bandicoot', 3, 'Giochi', 15.8, 22);
INSERT INTO product (productName, details, quantity, category, price, iva) VALUES ('Spiderman VS Goblin', 'Spiderman VS Goblin', 6, 'Fumetti', 47, 22);