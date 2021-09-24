CREATE DATABASE  IF NOT EXISTS `prueba` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `prueba`;
-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: prueba
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.21.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carga`
--

DROP TABLE IF EXISTS `carga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carga` (
  `id_carga` int NOT NULL AUTO_INCREMENT,
  `fecha` varchar(255) DEFAULT NULL,
  `control_carga` int DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `carga_inicial` tinyint DEFAULT NULL,
  `productos` int DEFAULT NULL,
  `fecha_y_hora` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_carga`),
  KEY `fk_cr_control_idx` (`control_carga`),
  KEY `fk_cr_productos_idx` (`productos`),
  CONSTRAINT `fk_cr_control` FOREIGN KEY (`control_carga`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `fk_cr_productos` FOREIGN KEY (`productos`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carga`
--

LOCK TABLES `carga` WRITE;
/*!40000 ALTER TABLE `carga` DISABLE KEYS */;
/*!40000 ALTER TABLE `carga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nombre_cliente` varchar(45) DEFAULT NULL,
  `apellido_cliente` varchar(45) DEFAULT NULL,
  `direccion_cliente` varchar(45) DEFAULT NULL,
  `telefono_cliente` varchar(45) DEFAULT NULL,
  `saldo_cliente` varchar(45) DEFAULT NULL,
  `zona` varchar(45) DEFAULT NULL,
  `freezer` tinyint DEFAULT NULL,
  `id_order` int DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `fk_cl_orden_idx` (`id_order`),
  CONSTRAINT `fk_cl_orden` FOREIGN KEY (`id_order`) REFERENCES `orden` (`id_orden`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Pirucha','P','La central','mail@mail.com','0.0','La Central',0,NULL),(2,'Ezequiel','E','La central','mail@mail.com','0.0','La Central',0,NULL),(3,'Eli','DePetri','La central','2634651258','0.0','La Central',0,NULL),(4,'Juan ','Gauna','La central','2634590037','0.0','La Central',0,NULL),(5,'Supermercado','El Paso','La central','2634696262','0.0','La Central',0,NULL),(6,'Daniel','Morón','La central','2634780180','0.0','La Central',0,NULL),(7,'Cecilia','C','Divisadero','2634320378','0.0','El Dvisadero',0,NULL),(8,'GNC','Gimenez','El mirador','0','0.0','El Mirado',0,NULL),(9,'Negro','Aguirre','B° Bermejo','2634769896','0.0','El Mirador',0,NULL),(10,'Estela ','Varela','Sagrada Familia','2634255731','0.0','El Mirador',0,NULL),(11,'Hugo','Machin','Carril Florida','0','0.0','El Mirador',0,NULL),(12,'Romina','Jurado','B° Albarracin Godoy','2634932799','0.0','El Mirador',0,NULL),(13,'Gabriela','G','Comeglio','2634682396','0.0','El Mirador',0,NULL),(14,'Delicia','Baez','La central','2634778880','0.0','La Central',0,NULL),(15,'Yaya','Brucolo','Carril Florida','2634679750','0.0','B° Lencina',0,NULL),(16,'Abel','Amaya','B° Lencinas','2634724251','0.0','Los Campamentos',0,NULL),(17,'Daniela','Gutierrez','Furlani','2634529521','0.0','Los Campamentos',0,NULL),(18,'Miriam','Coria','B° Campamento','2616769237','0.0','Los Campamentos',0,NULL),(19,'Nora','N','Carril Florida','2634','0.0','La Central',0,NULL),(20,'Brenda y','Juan','B° Florida','2634724324',NULL,'Los Campamentos',0,NULL),(21,'Norma','Chocans','B° Florida','2634317580','0.0','Los Campamentos',0,NULL),(22,'Horacio','Gutierrez','B° Florida','2634504584','0.0','Los Campamentos',0,NULL),(23,'Jorge','Caldarella','Carril Florida','2615006236','0.0','Los Campamentos',0,NULL),(24,'Eli y','Cristina','Sta María','2634566526','0.0','Santa María de Oro',0,NULL),(25,'Alberto ','Frías','Sta María','2634546526','0.0','Santa María de Oro',0,NULL),(26,'Adriana','Leggio','Sta María','2634366142','0.0','Santa María de Oro',0,NULL),(27,'Marisol','Rivera','Liniers 56','2634553131','0.0','Santa María de Oro',0,NULL),(28,'Roberto y','Claudia','Sta María','2634540262','0.0','Santa María de Oro',0,NULL),(29,'Estela','Montón ','Villa Antigua','2634','0.0','La Paz',0,NULL),(30,'Alejandra','Perez','B° Bollero','2634577120','0.0','La Paz',0,NULL),(31,'Raulito','Roldán','La Paz','2634598118','0.0','La Paz',0,NULL),(32,'Rolando ','Musseto','Maipú 2150','3582443614','0.0','La Paz',0,NULL),(33,'Titi','Lopez','General Paz B° Ferroviario','2613674335','0.0','La Paz',0,NULL),(34,'Mirta','M','La Paz','2634','0.0','La Paz',0,NULL),(35,'Miguel y Monica','Rosales','25 De Mayo','2634280672','0.0','La Paz',0,NULL),(36,'Escuela','Ferrocarril','La paz','2634','0.0','La Paz',0,NULL),(37,'Escuela','Moreno','La paz','2634','0.0','La Paz',0,NULL),(38,'Escuela','La Primera Junta','La paz','2634','0.0','La Paz',0,NULL),(39,'Gaston','G','La paz','2634273311','0.0','La Paz',0,NULL),(40,'Belen','Bergelin','Calle Control 2284','2634556705','0.0','La Paz',0,NULL),(41,'Enio','E','La paz','2634','0.0','La Paz',0,NULL),(42,'Claudio',' ','La paz','2634529502','0.0','La Paz',0,NULL),(43,'Edith','Fernandez','La paz','2634212359','0.0','La Paz',0,NULL),(44,'Juan',' ','La paz','2634','0.0','La Paz',0,NULL),(45,'Marcelo','Barrera','Lamenta','2634','0.0','La Paz',0,NULL),(46,'Johana',' ','Ruta 50','2634305385','0.0','La Dormida',0,NULL);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente_agregado`
--

DROP TABLE IF EXISTS `cliente_agregado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente_agregado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `zona` varchar(45) DEFAULT NULL,
  `id_orden` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ca_orden_idx` (`id_orden`),
  CONSTRAINT `fk_ca_orden` FOREIGN KEY (`id_orden`) REFERENCES `orden` (`id_orden`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente_agregado`
--

LOCK TABLES `cliente_agregado` WRITE;
/*!40000 ALTER TABLE `cliente_agregado` DISABLE KEYS */;
INSERT INTO `cliente_agregado` VALUES (1,'Gaston','G','La paz','La Paz',1),(2,'Claudio',' ','La paz','La Paz',2),(3,'Cecilia','C','Divisadero','El Dvisadero',3),(4,'Adriana','Leggio','Sta María','Santa María de Oro',4),(5,'Edith','Fernandez','La paz','La Paz',5);
/*!40000 ALTER TABLE `cliente_agregado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listas_precios`
--

DROP TABLE IF EXISTS `listas_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listas_precios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listas_precios`
--

LOCK TABLES `listas_precios` WRITE;
/*!40000 ALTER TABLE `listas_precios` DISABLE KEYS */;
INSERT INTO `listas_precios` VALUES (8,'Revendedores');
/*!40000 ALTER TABLE `listas_precios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orden`
--

DROP TABLE IF EXISTS `orden`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orden` (
  `id_orden` int NOT NULL AUTO_INCREMENT,
  `fecha` varchar(255) DEFAULT NULL,
  `pagado` tinyint DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `fecha_y_hora` varchar(45) DEFAULT NULL,
  `orden_reporte` int DEFAULT NULL,
  PRIMARY KEY (`id_orden`),
  UNIQUE KEY `id_orden_UNIQUE` (`id_orden`),
  KEY `fk_or_orepor_idx` (`orden_reporte`),
  CONSTRAINT `fk_or_orepor` FOREIGN KEY (`orden_reporte`) REFERENCES `reporte` (`id_reporte`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orden`
--

LOCK TABLES `orden` WRITE;
/*!40000 ALTER TABLE `orden` DISABLE KEYS */;
INSERT INTO `orden` VALUES (1,NULL,0,NULL,'17-09-2021 || 10:00',2),(2,NULL,0,NULL,'17-09-2021 || 12:10',2),(3,NULL,0,NULL,'18-09-2021 || 15:18',2),(4,NULL,0,NULL,'19-09-2021 || 12:46',3),(5,NULL,0,NULL,'19-09-2021 || 12:47',3);
/*!40000 ALTER TABLE `orden` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `marca` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `codigo` varchar(45) DEFAULT NULL,
  `existencia` float DEFAULT NULL,
  `precio` float DEFAULT NULL,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Agua Acapulco','Acapulco','Palito','01',100000,18),(2,'Frutales Surtido','Dolce Neve','Palito','02',99980,23),(3,'Bombón Acapulco','Acapulco','Palito','03',99880,26),(4,'Bombón Rich','Rich','Palito','04',100000,33),(5,'Bombón DolceNeve','Dolce Neve','Palito','05',99980,38),(6,'Barrita Dolce Bom','Dolce Neve','Barrita','06',100000,38),(7,'Combinado','Acapulco','Palito','07',100000,26),(8,'Sombrero Loco','Dolce Neve','Palito','08',99970,38),(9,'Carita (Emoji)','Dolce Neve','Palito','09',100000,38),(10,'Tutti','Dolce Neve','Palito','10',100000,45),(11,'Zombie Relleno','Dolce Neve','Palito','11',100000,35),(12,'Alfajor Helado','Dolce Neve','Alfajor','12',100000,65),(13,'Ecco (Cono)','Dolce Neve','Cono','13',100000,82),(14,'Cono Bola','Acapulco','Cono','14',100000,60),(15,'Piú (Bombón Gigante)','Dolce Neve','Palito','15',99988,82),(16,'Copacrem','Dolce Neve','Copa','16',99976,65),(17,'Pote 360','Acapulco','Pote','17',99988,65),(18,'Pote Mackenzie','Mackenzie','Pote','18',99983,90),(19,'Torta Helada Queen','Queen','Torta','19',100000,480),(20,'Pote 600cc. Artesanal','Mackenzie','Pote','20',99996,200),(21,'Balde x 3 litros BH','Bello Horizonte','Balde','21',99976,360),(22,'Balde x 3 litros Verde','Acapulco','Balde','22',99995,400),(23,'Balde x 3 litros Rojo','Acapulco','Balde','23',100000,430),(24,'Balde x 3 litros Mackenzie','Mackenzie','Balde','24',99995,450),(25,'Balde x 5 litros Mackenzie','Mackenzie','Balde','25',100000,720),(26,'Balde x 10 litros Artesanal','Mackenzie','Balde','26',100000,1800),(27,'Hielo Mediano','Dalmasso','Hielo','27',100000,35),(28,'Hielo Grande','Dalmasso','Hielo','28',100000,70),(29,'Jugos Dulcín x 30','Dulcín','Juguitos Congelados','29',100000,260),(30,'Jugazo x 120','Jugazo','Juguitos Congelados','30',100000,420),(31,'Crocantino','Dolce Neve','Palito','31',100000,57);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto_vendido`
--

DROP TABLE IF EXISTS `producto_vendido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto_vendido` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` float DEFAULT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `orden_id` int DEFAULT NULL,
  `reporte_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKssaaq1yqijvn9q87l4y6xdcoi` (`orden_id`),
  KEY `fk_pv_repor_idx` (`reporte_id`),
  CONSTRAINT `fk_pv_orden` FOREIGN KEY (`orden_id`) REFERENCES `orden` (`id_orden`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_pv_repor` FOREIGN KEY (`reporte_id`) REFERENCES `reporte` (`id_reporte`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto_vendido`
--

LOCK TABLES `producto_vendido` WRITE;
/*!40000 ALTER TABLE `producto_vendido` DISABLE KEYS */;
INSERT INTO `producto_vendido` VALUES (1,40,'03','Bombón Acapulco',26,1,2),(2,5,'21','Balde x 3 litros BH',360,1,2),(3,12,'16','Copacrem',65,1,2),(4,30,'08','Sombrero Loco',38,1,2),(5,5,'24','Balde x 3 litros Mackenzie',450,2,2),(6,5,'22','Balde x 3 litros Verde',400,2,2),(7,12,'15','Piú (Bombón Gigante)',82,2,2),(8,4,'20','Pote 600cc. Artesanal',200,2,2),(9,20,'05','Bombón DolceNeve',38,2,2),(10,12,'16','Copacrem',65,3,2),(11,5,'21','Balde x 3 litros BH',360,3,2),(12,40,'03','Bombón Acapulco',26,3,2),(13,10,'18','Pote Mackenzie',90,4,3),(14,20,'02','Frutales Surtido',23,4,3),(15,10,'21','Balde x 3 litros BH',360,4,3),(16,40,'03','Bombón Acapulco',26,5,3),(17,4,'21','Balde x 3 litros BH',360,5,3),(18,7,'18','Pote Mackenzie',90,5,3),(19,12,'17','Pote 360',65,5,3);
/*!40000 ALTER TABLE `producto_vendido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos_listas`
--

DROP TABLE IF EXISTS `productos_listas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos_listas` (
  `producto_id` int NOT NULL AUTO_INCREMENT,
  `producto` int DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `lista` int DEFAULT NULL,
  PRIMARY KEY (`producto_id`),
  KEY `fk_pl_producto_idx` (`producto`),
  KEY `fk_pl_lista_idx` (`lista`),
  CONSTRAINT `fk_pl_lista` FOREIGN KEY (`lista`) REFERENCES `listas_precios` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_pl_producto` FOREIGN KEY (`producto`) REFERENCES `producto` (`id_producto`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos_listas`
--

LOCK TABLES `productos_listas` WRITE;
/*!40000 ALTER TABLE `productos_listas` DISABLE KEYS */;
INSERT INTO `productos_listas` VALUES (33,1,18,8),(34,2,23,8),(35,3,26,8),(36,4,33,8),(37,5,38,8),(38,6,38,8),(39,7,26,8),(40,8,38,8),(41,9,38,8),(42,10,45,8),(43,11,35,8),(44,12,65,8),(45,13,82,8),(46,14,60,8),(47,15,82,8),(48,16,65,8),(49,17,65,8),(50,18,90,8),(51,19,480,8),(52,20,200,8),(53,21,360,8),(54,22,400,8),(55,23,430,8),(56,24,450,8),(57,25,720,8),(58,26,1800,8),(59,27,35,8),(60,28,70,8),(61,29,260,8),(62,30,420,8),(63,31,57,8);
/*!40000 ALTER TABLE `productos_listas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte`
--

DROP TABLE IF EXISTS `reporte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte` (
  `id_reporte` int NOT NULL AUTO_INCREMENT,
  `fecha_y_hora` varchar(45) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_reporte`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte`
--

LOCK TABLES `reporte` WRITE;
/*!40000 ALTER TABLE `reporte` DISABLE KEYS */;
INSERT INTO `reporte` VALUES (2,'18-09-2021 || 15:18',NULL),(3,'19-09-2021 || 12:47',NULL);
/*!40000 ALTER TABLE `reporte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id_rol` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_rol`),
  KEY `fk_rol_usuario_idx` (`id_usuario`),
  CONSTRAINT `fk_rol_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (9,'ROLE_ADMIN',4),(10,'ROLE_USER',4);
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (4,'Admin','$2a$10$rs9/x3ma3GgdAq/lVzYVeeQqlhTi34w4Omgmoq5TSzcFzKGBDnoDu');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-20 12:53:12
