CREATE TABLE `centro` (
  `idCentro` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `estadoCentro` varchar(25) DEFAULT NULL,
  `tipoVia` varchar(20) DEFAULT NULL,
  `nombreVia` varchar(255) DEFAULT NULL,
  `otrosDireccion` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  `url` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `persona` VARCHAR(255) DEFAULT NULL,
  `telefono` VARCHAR(15) DEFAULT NULL,
  `fax` VARCHAR(15) DEFAULT NULL,
  `textoLibre` TEXT DEFAULT NULL,
  PRIMARY KEY (`idCentro`),
  KEY `KEY_CENTRO_MUNICIPIO` (`idMunicipio`),
  CONSTRAINT `KEY_CENTRO_MUNICIPIO` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;