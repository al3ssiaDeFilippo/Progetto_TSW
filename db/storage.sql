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
  frame VARCHAR(8) CHECK (frame IN ('default', 'no frame', 'wood', 'PVC')),
  frameColor VARCHAR(8) CHECK (frameColor IN ('default', 'black', 'brown', 'white', 'no color')),
  size VARCHAR(7) CHECK (size IN ('default', '21x30', '85x60', '91x61')),
  photo LONGBLOB
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE user (
   idUser INT PRIMARY KEY AUTO_INCREMENT,
   surname VARCHAR(50) NOT NULL,
   name VARCHAR(20) NOT NULL,
   username VARCHAR(30) UNIQUE NOT NULL,
   BirthDate DATE NOT NULL,
   email VARCHAR(50) UNIQUE NOT NULL,
    /*Inizio Modifiche Qui*/
   password VARCHAR(64) NOT NULL, -- Password hashata
   salt VARCHAR(32) NOT NULL, -- Salt per l'hashing della password
   /*Fine Modifiche Qui*/
   TelNumber VARCHAR(13) NOT NULL,
   admin BOOLEAN NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE cart (
	  idCart INT PRIMARY KEY AUTO_INCREMENT,
	  idUser INT,
	  productCode INT NOT NULL,
	  quantity INT DEFAULT 1,
      frame VARCHAR(8) NOT NULL CHECK (frame IN ('no frame', 'wood', 'PVC')) DEFAULT 'no frame',
	  frameColor VARCHAR(8) NOT NULL CHECK (frameColor IN ('black', 'brown', 'white', 'no color')) DEFAULT 'no color',
	  size VARCHAR(5) NOT NULL CHECK (size IN ('21x30', '85x60', '91x61')) DEFAULT '21x30',
	  price FLOAT NOT NULL,
	  FOREIGN KEY (idUser) REFERENCES user(idUser),
	  FOREIGN KEY (productCode) REFERENCES product(code)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE card (
                     idCard VARCHAR(16) PRIMARY KEY,
                     ownerCard VARCHAR(50) NOT NULL,
                     expirationDate DATE NOT NULL,
                     cvv INT NOT NULL,
                     idUser INT,
                     FOREIGN KEY (idUser) REFERENCES user(idUser)
                         ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE shipping (
                         idShipping INT PRIMARY KEY AUTO_INCREMENT,
                         recipientName VARCHAR(50) NOT NULL,
                         address VARCHAR(50) NOT NULL,
                         city VARCHAR(50) NOT NULL,
                         cap INT NOT NULL,
                         idUser INT NOT NULL,
                         FOREIGN KEY (idUser) REFERENCES  user(idUser)
                         ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE orders (
                        idOrder INT PRIMARY KEY AUTO_INCREMENT,
                        idUser INT NOT NULL,
                        idShipping INT NOT NULL,
                        idCreditCard VARCHAR(16),
                        orderDate DATE NOT NULL,
                        totalPrice FLOAT NOT NULL,
                        FOREIGN KEY (idUser) REFERENCES user(idUser)
                            ON UPDATE CASCADE ON DELETE CASCADE,
                        FOREIGN KEY (idShipping) REFERENCES shipping(idShipping)
                            ON UPDATE CASCADE ON DELETE CASCADE,
                        FOREIGN KEY (idCreditCard) REFERENCES card(idCard)
                            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE orderDetails(
	  idUser INT PRIMARY KEY,
	  productCode INT NOT NULL,
	  quantity INT DEFAULT 1,
      frame VARCHAR(8) NOT NULL CHECK (frame IN ('no frame', 'wood', 'PVC')),
	  frameColor VARCHAR(8) NOT NULL CHECK (frameColor IN ('black', 'brown', 'white', 'no color')),
	  size VARCHAR(5) NOT NULL CHECK (size IN ('21x30', '85x60', '91x61')),
	  price FLOAT NOT NULL,
      idOrder INT NOT NULL,
	  FOREIGN KEY (idUser) REFERENCES user(idUser),
	  FOREIGN KEY (productCode) REFERENCES product(code),
      FOREIGN KEY (idOrder) REFERENCES orders(idOrder)
);

CREATE TABLE photo (
                       idPhoto INT PRIMARY KEY AUTO_INCREMENT,
                       photo LONGBLOB,
                       productCode INT NOT NULL,
					   frame VARCHAR(8) CHECK (frame IN ('no frame', 'wood', 'PVC')),
					   frameColor VARCHAR(8) CHECK (frameColor IN ('black', 'brown', 'white', 'no color')),
                       FOREIGN KEY (productCode) REFERENCES product(code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO product (productName, details, quantity, category, price, iva, discount, photo)
VALUES ('Crash Bandicoot', 'Crash Bandicoot', 3, 'Giochi', 15.8, 22, 10, LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Crash.jpg'));
INSERT INTO product (productName, details, quantity, category, price, iva, discount, photo)
VALUES ('Spiderman VS Goblin', 'Spiderman VS Goblin', 6, 'Fumetti', 47, 22, 20, LOAD_FILE('C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Marvel Art Print SpiderMan vs Green Goblin 41 x 61 cm.jpg'));


INSERT INTO user(surname, name, username, BirthDate, email, password, salt, TelNumber, admin)  values('a', 'b', 'admin', '2004-01-02', 'a@a.iy', '8c249d06dfaff657ef96429eaa52537cb1f4139994ead43e1fecd9923000277a', 'dd','34676424', true);	/*password: ciao*/

