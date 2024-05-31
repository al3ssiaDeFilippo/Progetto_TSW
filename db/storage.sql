DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;

CREATE TABLE product (
  code INT PRIMARY KEY AUTO_INCREMENT,
  productName VARCHAR(50) UNIQUE NOT NULL,
  details VARCHAR(255),
  quantity INT DEFAULT 0,
  category VARCHAR(8) NOT NULL CHECK (category IN ('Film', 'Anime', 'Fumetti', 'Giochi', 'Serie TV')),
  price FLOAT DEFAULT 0 NOT NULL,
  iva INT DEFAULT 0 NOT NULL,
  discount INT DEFAULT 0,
  frame VARCHAR(8) NOT NULL CHECK (frame IN ('no frame', 'wood', 'PVC')),
  frameColor VARCHAR(5) NOT NULL CHECK (frameColor IN ('black', 'brown', 'white')),
  size VARCHAR(5) NOT NULL CHECK (size IN ('21x30', '85x60', '91x61')),
  photo LONGBLOB
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE cart (
  code INT PRIMARY KEY AUTO_INCREMENT,
  quantity INT NOT NULL,
  price FLOAT NOT NULL,
  idProduct INT NOT NULL,
  FOREIGN KEY (idProduct) REFERENCES product(code)
  ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE user(
    idUser INT PRIMARY KEY AUTO_INCREMENT,
    surname VARCHAR(50) NOT NULL,
    name VARCHAR(20) NOT NULL,
    username VARCHAR(30) UNIQUE NOT NULL,
    BirthDate DATE NOT NULL,
    address VARCHAR(70) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    TelNumber VARCHAR(12) NOT NULL,
	type VARCHAR(6) NOT NULL CHECK (type IN ('admin','user'))
 );

CREATE VIEW user_view AS
SELECT
    idUser,
    username,
    name,
    surname,
    BirthDate,
    address,
    email,
    TelNumber
FROM user;

INSERT INTO product (productName, details, quantity, category, price, iva, frame, frameColor, size, photo) 
VALUES ('Crash Bandicoot', 'Crash Bandicoot', 3, 'Giochi', 15.8, 22, 'wood', 'brown', '85x60', LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Crash.jpg'));
INSERT INTO product (productName, details, quantity, category, price, iva, frame, frameColor, size, photo) VALUES ('Spiderman VS Goblin', 'Spiderman VS Goblin', 6, 'Fumetti', 47, 22, 'PVC', 'white', '91x61', LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Marvel Art Print SpiderMan vs Green Goblin 41 x 61 cm.jpg'));

SELECT * FROM user;

