-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS `minecraft_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `minecraft_db`;

-- Crear la tabla `inventarios`
CREATE TABLE IF NOT EXISTS `inventarios` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `capacidad` INT NOT NULL,
  `items` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Insertar datos iniciales en `inventarios`
INSERT INTO `inventarios` (`id`, `capacidad`, `items`) VALUES
(1, 20, 'Espada de diamante, Manzana dorada'),
(2, 15, 'Arco, Flechas, Pociones'),
(3, 30, 'Pico de netherite, Antorchas, Bloques de construcción'),
(4, 0, 'Mena de hierro, Pico de piedra, Trigo');

-- Crear la tabla `jugadores`
CREATE TABLE IF NOT EXISTS `jugadores` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `nivel` INT DEFAULT 1,
  `inventario_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `inventario_id` (`inventario_id`),
  CONSTRAINT `jugadores_ibfk_1` FOREIGN KEY (`inventario_id`) REFERENCES `inventarios` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Insertar datos iniciales en `jugadores`
INSERT INTO `jugadores` (`id`, `nombre`, `nivel`, `inventario_id`) VALUES
(5, 'Steve', 10, 1),
(6, 'Alex', 12, 2),
(7, 'Herobrine', 99, 3),
(10, 'Pepe', 7, 4);