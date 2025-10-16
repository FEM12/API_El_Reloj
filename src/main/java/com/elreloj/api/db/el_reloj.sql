DROP DATABASE IF EXISTS `el_reloj`;
CREATE DATABASE IF NOT EXISTS `el_reloj` CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `el_reloj`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users`(

  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `full_name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `password` CHAR(60) NOT NULL

)ENGINE=InnoDB;

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories`(

  `code` CHAR(8) NOT NULL PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `description` TEXT NOT NULL

)ENGINE=InnoDB;

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products`(

  `sku` CHAR(12) NOT NULL PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `price` DECIMAL(4,2) NOT NULL,
  `category_code` CHAR(12) NOT NULL,
  `description` TEXT NOT NULL,
  FOREIGN KEY (`category_code`) REFERENCES `categories`(`code`)

)ENGINE=InnoDB;

DROP TABLE IF EXISTS `extras`;
CREATE TABLE IF NOT EXISTS `extras`(

  `code` CHAR(8) NOT NULL PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `price` DECIMAL(4,2) NOT NULL,
  `category_code` CHAR(12) NOT NULL,
  `description` TEXT NOT NULL,
  FOREIGN KEY (`category_code`) REFERENCES `categories`(`code`)

)ENGINE=InnoDB;

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders`(

  `ref_number` CHAR(36) NOT NULL PRIMARY KEY,
  `user_id` INT NOT NULL,
  `datetime` DATETIME NOT NULL,
  `method_pay` ENUM('Cash') NOT NULL DEFAULT 'Cash',
  `total` DECIMAL(5,2) NOT NULL,
  `status` ENUM('Active','Inactive') NOT NULL DEFAULT 'Active',
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)

)ENGINE=InnoDB;

DROP TABLE IF EXISTS `order_details`;
CREATE TABLE IF NOT EXISTS `order_details`(

  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `order_ref_number` CHAR(36) NOT NULL,
  `product_sku` CHAR(12) NOT NULL,
  FOREIGN KEY (`order_ref_number`) REFERENCES `orders`(`ref_number`),
  FOREIGN KEY (`product_sku`) REFERENCES `products`(`sku`)

)ENGINE=InnoDB;

DROP TABLE IF EXISTS `order_details_extras`;
CREATE TABLE IF NOT EXISTS `order_details_extras`(

  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `order_detail_id` INT NOT NULL,
  `extra_code` CHAR(12) NOT NULL,
  FOREIGN KEY (`order_detail_id`) REFERENCES `order_details`(`id`),
  FOREIGN KEY (`extra_code`) REFERENCES `extras`(`code`)

)ENGINE=InnoDB;

INSERT INTO `users` VALUES
(DEFAULT,'Alexis Baldomero Zelaya Contreras','example01@gmail.com','$2a$14$/IkaSNsTc32Jr18hSun8dO9adeZviaHto7yraARlE5wqVLdMWc8SS');

INSERT INTO `categories` VALUES
('BC749302','Bebidas calientes','Aromáticas y reconfortantes, perfectas para acompañar momentos de relax o iniciar el día con energía.'),
('BH482917','Bebidas heladas','Refrescantes preparaciones frías, ideales para calmar la sed y disfrutar sabores intensos en días calurosos.'),
('PT639184','Postres','Dulces y tentadores, desde clásicos pasteles hasta creaciones innovadoras que completan tu experiencia.'),
('CI473820','Comidas','Platos ligeros y sabrosos, pensados para acompañar tus bebidas favoritas y satisfacer el apetito.'),
('TA123456','Tamaño','');

INSERT INTO `products` VALUES
('PRD100000001','Café Espresso',1.50,'BC749302','Café intenso y aromático, perfecto para iniciar el día con energía.'),
('PRD100000002','Té Verde',1.20,'BC749302','Infusión suave y saludable, rica en antioxidantes.'),
('PRD100000003','Smoothie de Fresa',2.50,'BH482917','Bebida fría y refrescante con fresas naturales y yogur.'),
('PRD100000004','Limonada con Hierbabuena',1.80,'BH482917','Refrescante limonada con un toque de hierbabuena fresca.'),
('PRD100000005','Cheesecake de Frutos Rojos',3.00,'PT639184','Postre cremoso con base de galleta y topping de frutos rojos.'),
('PRD100000006','Brownie de Chocolate',2.80,'PT639184','Clásico brownie de chocolate con nueces, húmedo y delicioso.'),
('PRD100000007','Sándwich de Pavo',3.50,'CI473820','Sándwich recién hecho con pavo, queso y vegetales frescos.'),
('PRD100000008','Ensalada César',3.20,'CI473820','Ensalada fresca con lechuga, croutons, queso parmesano y aderezo César.'),
('PRD100000009','Capuchino',2.00,'BC749302','Bebida caliente con espresso, leche vaporizada y espuma cremosa.'),
('PRD100000010','Mojito de Frutas',2.70,'BH482917','Bebida fría con ron, frutas frescas y un toque de hierbabuena.');

INSERT INTO `extras` VALUES
('EXT10001','Test01',1.00,'TA123456',''),
('EXT10002','Test02',2.00,'TA123456',''),
('EXT10003','Test03',3.00,'TA123456',''),
('EXT10004','Test04',4.00,'TA123456','');