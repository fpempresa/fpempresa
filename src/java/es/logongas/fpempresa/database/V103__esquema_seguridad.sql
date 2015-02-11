CREATE TABLE `usuario` (
  `idIdentity` int(11) NOT NULL,
  `eMail` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `ape1` varchar(255) DEFAULT NULL,
  `ape2` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tipoUsuario` int(11) DEFAULT NULL,
  `estadoUsuarioCentro` int(11) DEFAULT NULL,
  `idCentro` int(11) DEFAULT NULL,
  `foto` tinyblob,
  PRIMARY KEY (`idIdentity`),
  UNIQUE KEY `eMail` (`eMail`),
  KEY `Centro` (`idCentro`),
  CONSTRAINT `Centro` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`idCentro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
