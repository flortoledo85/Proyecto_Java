-- DATOS DE EJEMPLO PARA ENTREGA; NO ES UN RESPALDO PRIVADO COMPLETO.
-- Conserva el catalogo y su estructura; no incluye usuarios ni pedidos reales.
-- Desplegar en una base vacia y crear el administrador mediante la inicializacion del backend.

-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.11 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.22.0.7382
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para optica
CREATE DATABASE IF NOT EXISTS `optica` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `optica`;

-- Volcando estructura para tabla optica.armazones
CREATE TABLE IF NOT EXISTS `armazones` (
  `id` int(11) NOT NULL,
  `ancho_frente` double NOT NULL,
  `largo_patilla` double NOT NULL,
  `ancho_cristal` double NOT NULL,
  `alto_cristal` double NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_armazones_producto` FOREIGN KEY (`id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla optica.armazones: ~20 rows (aproximadamente)
INSERT INTO `armazones` (`id`, `ancho_frente`, `largo_patilla`, `ancho_cristal`, `alto_cristal`) VALUES
	(1, 136, 140, 51, 40),
	(2, 140, 145, 53, 43),
	(3, 145, 145, 56, 49),
	(4, 138, 142, 52, 42),
	(5, 141, 145, 54, 39),
	(6, 134, 140, 50, 41),
	(7, 143, 145, 55, 38),
	(8, 144, 145, 55, 47),
	(9, 147, 150, 58, 50),
	(10, 146, 145, 57, 48),
	(11, 142, 145, 55, 47),
	(12, 137, 140, 52, 37),
	(13, 135, 140, 51, 42),
	(14, 139, 145, 53, 44),
	(15, 132, 135, 49, 32),
	(16, 141, 145, 54, 44),
	(17, 134, 140, 50, 34),
	(18, 148, 150, 59, 51),
	(19, 146, 150, 57, 46),
	(20, 138, 145, 52, 43);

-- Volcando estructura para tabla optica.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla optica.categoria: ~6 rows (aproximadamente)
INSERT INTO `categoria` (`id`, `nombre`) VALUES
	(1, 'Armazones'),
	(2, 'Anteojos de sol'),
	(3, 'Lentes de contacto'),
	(4, 'Accesorios'),
	(5, 'Liquidos'),
	(6, 'Anteojos de Lectura');

-- Volcando estructura para tabla optica.linea_pedido
CREATE TABLE IF NOT EXISTS `linea_pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pedido_id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(12,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pedido_id` (`pedido_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `linea_pedido_ibfk_1` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`),
  CONSTRAINT `linea_pedido_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sin datos de linea_pedido: los registros se crearán desde la aplicación.

-- Volcando estructura para tabla optica.pedido
CREATE TABLE IF NOT EXISTS `pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` decimal(12,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sin datos de pedido: los registros se crearán desde la aplicación.

-- Volcando estructura para tabla optica.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT '0',
  `categoria_id` int(11) NOT NULL,
  `ruta_imagen` varchar(255) DEFAULT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla optica.producto: ~21 rows (aproximadamente)
INSERT INTO `producto` (`id`, `nombre`, `precio`, `stock`, `categoria_id`, `ruta_imagen`, `descripcion`) VALUES
	(1, 'Pink Love', 9000.00, 69, 1, './pictures/pink_love.jpg', 'Armazón de acetato negro exterior con rosa-fucsia interior. Forma redondeada.'),
	(2, 'Cristal', 12000.00, 100, 1, './pictures/cristal.jpg', 'Armazón de acetato transparente cristal. Forma cuadrada.'),
	(3, 'Sol Bordo', 19800.00, 40, 2, './pictures/sunglasses.jpg', 'Anteojos de Sol con armazón bordo oscuro. Curvado envolvente.'),
	(4, 'Jaspeado', 11800.00, 70, 1, './pictures/jaspeado.jpg', 'Armazón acetato marrón jaspeado. Forma cuadrada'),
	(5, 'Negro Clásico', 9500.00, 95, 1, './pictures/negro_clasico.jpg', 'Armazón metálico sólido, negro mate. Forma rectángular.'),
	(6, 'Soft Coral', 9900.00, 58, 1, './pictures/soft_coral.jpg', 'Montura femenina estilo suave. Color: Coral transparente. Forma rectángular redondeados.'),
	(7, 'Urban Steel', 13200.00, 72, 1, './pictures/urban_steel.jpg', 'Armazón metálico liviano. Color: Plateado y cobre.'),
	(8, 'Retro 80', 11300.00, 36, 1, './pictures/retro_80.jpg', 'Montura estilo redondo retro. Color: metalizado.'),
	(9, 'Sol Espejado Amarillo', 16000.00, 10, 2, './pictures/sol_espejado_amarillo.jpg', 'Anteojos de Sol Murray. Lente espejado Amarillo reflectivo. Protección UV400.'),
	(10, 'Sol RB Polarizado', 21000.00, 85, 2, './pictures/rayban_p.jpg', 'Anteojos de Sol estilo RayBan negros (no original). Polarizado categoría 3.'),
	(11, 'Sol Celeste Tornasol', 17500.00, 22, 2, './pictures/sun_reflective_beach.jpg', 'Anteojos de Sol, exterior negro con interior celeste agua. Lentes espejados efecto tornasol azul-violeta.'),
	(12, 'Aire Minimalista', 14700.00, 60, 1, './pictures/aire_minimalista.jpg', 'Montura sin marco estilo minimalista. Color: Incoloro con bordes metálicos.'),
	(13, 'Rose Elegance', 8700.00, 55, 1, './pictures/rose_elegance.jpg', 'Marco rosa metálico. Forma redondeada. Estilo elegante y juvenil.'),
	(14, 'Golden Drop', 9500.00, 42, 1, './pictures/golden_drop.jpg', 'Marco redondeado inferior. Color dorado metálico. Diseño liviano y clásico.'),
	(15, 'Red Impact', 6000.00, 63, 6, './pictures/red_impact.jpg', 'Anteojos de lectura. Marco acetato color rojo intenso exterior con transparente interior. Forma rectangular.'),
	(16, 'Print Wild', 11000.00, 15, 1, './pictures/print_wild.jpg', 'Media montura superior con estilo animal print. Material plástico resistente.'),
	(17, 'Dual Shadow', 7200.00, 75, 6, './pictures/dual_shadow.jpg', 'Anteojos de lectura. Marcos acetato negros exterior con azul interior. Forma rectangular moderna.'),
	(18, 'Sun Fire Yellow', 13800.00, 18, 2, './pictures/sun_fire_yellow.jpg', 'Anteojos de sol. Filtro amarillo, no espejados. Estilo urbano, ideal conducción.'),
	(19, 'Polar Night', 20500.00, 66, 2, './pictures/polar_night.jpg', 'Anteojos polarizados. Media montura superior. Filtro degradé negro. Estilo deportivo.'),
	(20, 'Crystal Hexa', 12500.00, 52, 1, './pictures/crystal_hexa.jpg', 'Marco acetato transparente cristal. Forma redonda con contorno hexagonal sutil. Moderno y ligero.'),
	(22, 'Blue Dream', 12000.00, 48, 1, './pictures/blue_dream.jpg', 'Marco acetato degrade negro a azul. Forma redondeada. Moderno y ligero.');

-- Volcando estructura para tabla optica.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `rol` varchar(20) NOT NULL DEFAULT 'USER',
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  `fecha_registro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Sin datos de usuario: los registros se crearán desde la aplicación.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
