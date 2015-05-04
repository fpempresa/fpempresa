CREATE TABLE `provincia` (
  `idProvincia` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProvincia`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

CREATE TABLE `municipio` (
  `idMunicipio` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `codigo` varchar(5) DEFAULT NULL,
  `idProvincia` int(11) DEFAULT NULL,
  PRIMARY KEY (`idMunicipio`),
  KEY `FK863DB7CDDA49940A` (`idProvincia`),
  CONSTRAINT `FK863DB7CDDA49940A` FOREIGN KEY (`idProvincia`) REFERENCES `provincia` (`idProvincia`)
) ENGINE=InnoDB AUTO_INCREMENT=8149 DEFAULT CHARSET=utf8;


CREATE TABLE `leyeducativa` (
  `idLeyEducativa` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) DEFAULT '0',
  PRIMARY KEY (`idLeyEducativa`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `familia` (
  `idFamilia` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idFamilia`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;


CREATE TABLE `grado` (
  `idGrado` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) DEFAULT '0',
  PRIMARY KEY (`idGrado`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `ciclo` (
  `idCiclo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `idFamilia` int(11) DEFAULT NULL,
  `idGrado` int(11) DEFAULT NULL,
  `idLeyEducativa` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCiclo`),
  KEY `FK3E162E041ECA7CA` (`idFamilia`),
  KEY `FK_ciclo_grado` (`idGrado`),
  KEY `FK_ciclo_leyeducativa` (`idLeyEducativa`),
  CONSTRAINT `FK_ciclo_leyeducativa` FOREIGN KEY (`idLeyEducativa`) REFERENCES `leyeducativa` (`idLeyEducativa`),
  CONSTRAINT `FK3E162E041ECA7CA` FOREIGN KEY (`idFamilia`) REFERENCES `familia` (`idFamilia`),
  CONSTRAINT `FK_ciclo_grado` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`idGrado`)
) ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8;


