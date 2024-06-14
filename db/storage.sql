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

CREATE TABLE user(
    idUser INT PRIMARY KEY AUTO_INCREMENT,
    surname VARCHAR(50) NOT NULL,
    name VARCHAR(20) NOT NULL,
    username VARCHAR(30) UNIQUE NOT NULL,
    BirthDate DATE NOT NULL,
    address VARCHAR(70) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    TelNumber VARCHAR(13) NOT NULL,
	admin BOOLEAN NOT NULL
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE cart (
  idCart INT PRIMARY KEY AUTO_INCREMENT,
  idUser INT,
  productCode INT NOT NULL,
  quantity INT DEFAULT 1,
  price FLOAT NOT NULL,
  FOREIGN KEY (idUser) REFERENCES user(idUser),
  FOREIGN KEY (productCode) REFERENCES product(code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE card(
                     idCard VARCHAR(16) PRIMARY KEY,
                     ownerCard VARCHAR(50) NOT NULL,
                     expirationDate DATE NOT NULL,
                     cvv INT NOT NULL,
                     idUser INT,
                     FOREIGN KEY (idUser) REFERENCES user(idUser)
                         ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE shipping(
                         idShipping INT PRIMARY KEY AUTO_INCREMENT,
                         recipientName VARCHAR(50) NOT NULL,
                         address VARCHAR(50) NOT NULL,
                         city VARCHAR(50) NOT NULL,
                         cap INT NOT NULL,
                         idUser INT NOT NULL,
                         FOREIGN KEY (idUser) REFERENCES  user(idUser)
                         ON UPDATE CASCADE ON DELETE CASCADE
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

INSERT INTO product (productName, details, quantity, category, price, iva, discount, frame, frameColor, size, photo)
VALUES ('Crash Bandicoot', 'Crash Bandicoot', 3, 'Giochi', 15.8, 22, 10, 'wood', 'brown', '85x60', LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Crash.jpg'));
INSERT INTO product (productName, details, quantity, category, price, iva, discount, frame, frameColor, size, photo)
VALUES ('Spiderman VS Goblin', 'Spiderman VS Goblin', 6, 'Fumetti', 47, 22, 20, 'PVC', 'white', '91x61', LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Marvel Art Print SpiderMan vs Green Goblin 41 x 61 cm.jpg'));


INSERT INTO user(surname, name, username, BirthDate, address, email, password, TelNumber, admin)  values('a', 'b', 'admin', '2004-01-02', 'ciao', 'a@a.iy', 'admin', '34676424', true);
SELECT * FROM product;
SELECT COUNT(*) FROM product WHERE code = 2;

