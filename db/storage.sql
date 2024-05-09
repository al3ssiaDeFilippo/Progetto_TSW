DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;


CREATE TABLE product (
                         code int primary key AUTO_INCREMENT,
                         productName char(20) not null,
                         details char(200),
                         quantity int default 0,
                         category varchar(8) check(category in('Film', 'Anime', 'Fumetti', 'Giochi', 'Serie TV')) not null,
                         price float default 0 not null,
                         iva int default 0 not null,
                         discount int default 0,
                         frame varchar(8) check(frame in('no frame', 'wood', 'PVC')) not null,
                         frameColor varchar(5) check(frameColor in ('black', 'brown', 'white')) not null,
                         size varchar(5) check(size in('21x30', '85x60', '91x61')) not null
);

CREATE TABLE cart (
                      code int primary key auto_increment,
                      quantity int not null,
                      price float not null,
                      idProduct int not null,
                      foreign key (idProduct) references product(code)
);

INSERT INTO product (productName, details, quantity, category, price, iva, frame, frameColor, size) VALUES ('Crash Bandicoot', 'Crash Bandicoot', 3, 'Giochi', 15.8, 22, 'wood', 'brown', '85x60');
INSERT INTO product (productName, details, quantity, category, price, iva, frame, frameColor, size) VALUES ('Spiderman VS Goblin', 'Spiderman VS Goblin', 6, 'Fumetti', 47, 22, 'PVC', 'white', '91x61');

SELECT * FROM cart;