CREATE TABLE `dominioconocido` (
  `idDominioConocido` int(11) NOT NULL AUTO_INCREMENT,
  `dominio` varchar(255) DEFAULT NULL,
  `tipoDominioConocido` varchar(50) DEFAULT NULL,
  `fechaAlta` date DEFAULT NULL,
  PRIMARY KEY (`idDominioConocido`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

