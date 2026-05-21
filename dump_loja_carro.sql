-- Dump SQL para o banco de dados LojaCarro
-- Baseado na entidade Carro e no arquivo de dados de teste existente.

CREATE DATABASE IF NOT EXISTS `lojacarros_test`;
USE `lojacarros_test`;

DROP TABLE IF EXISTS `carro`;
CREATE TABLE `carro` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `modelo` VARCHAR(255) NOT NULL,
  `ano` INT NOT NULL,
  `preco` DECIMAL(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `carro` (`id`, `modelo`, `ano`, `preco`) VALUES
  (100, 'Fiesta', 2019, 45000.00);
