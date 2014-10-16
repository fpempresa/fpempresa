CREATE TABLE IF NOT EXISTS `centro` (
  `idCentro` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `fpempresa` tinyint(1) DEFAULT NULL,
  `tipoVia` int(11) DEFAULT NULL,
  `nombreVia` varchar(255) DEFAULT NULL,
  `otrosDireccion` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `idProvincia` int(11) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCentro`),
  KEY `FK7817BA05DA49940A` (`idProvincia`),
  KEY `FK7817BA05391E4C8A` (`idMunicipio`),
  CONSTRAINT `FK7817BA05391E4C8A` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`),
  CONSTRAINT `FK7817BA05DA49940A` FOREIGN KEY (`idProvincia`) REFERENCES `provincia` (`idProvincia`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;