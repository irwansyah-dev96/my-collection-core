-- CREATE DATABASE mybook_ms_core;

USE mybook_ms_core;

-- DDL
CREATE TABLE `tb_user` (
  `name` varchar(50) NOT NULL,
  `password` varchar(250) NOT NULL,
  `role` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `tb_status` (
  `status_code` varchar(5) NOT NULL,
  `status_name` varchar(15) NOT NULL,
  PRIMARY KEY (`status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `tb_category` (
  `category_code` varchar(5) NOT NULL,
  `category_name` varchar(20) NOT NULL,
  PRIMARY KEY (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `tb_book` (
  `issbn` varchar(15) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` text,
  `author_name` varchar(20) NOT NULL,
  `publisher` varchar(20) NOT NULL,
  `release_date` BIGINT NOT NULL,
  `number_of_page` int NOT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `status_code` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`issbn`),
  CONSTRAINT `FK_status_code` FOREIGN KEY (`status_code`) REFERENCES `tb_status` (`status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_category_book`(
	`id` varchar(36) not null,
	`issbn` varchar(15) NOT NULL,
	`category_code` varchar(5) NOT null,
	primary key(`id`),
	CONSTRAINT `FK_issbn` FOREIGN KEY (`issbn`) REFERENCES `tb_book` (`issbn`),
	CONSTRAINT `FK_category_code` FOREIGN KEY (`category_code`) REFERENCES `tb_category` (`category_code`),
	constraint `UQ_issbn_category_code` unique(`issbn`,`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DML
INSERT INTO tb_status
(status_code, status_name) VALUES
('C', 'complete'),
('N', 'new'),
('R', 'read');

INSERT INTO mybook_ms_core.tb_user
(name, password, `role`, username)
VALUES('irwansyah', '$2a$10$PTHasV2A84HC7u8rlHAVCepJGm2ZY8haOxmhtVfTYDjKk91TyaSCe', 'se', 'irwan.dev');