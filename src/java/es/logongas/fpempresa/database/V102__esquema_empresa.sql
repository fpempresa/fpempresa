CREATE TABLE `empresa` (
  `idEmpresa` int(11) NOT NULL AUTO_INCREMENT,
  `nombreComercial` varchar(255) DEFAULT NULL,
  `razonSocial` varchar(255) DEFAULT NULL,
  `cif` varchar(9) DEFAULT NULL,
  `tipoVia` varchar(20) DEFAULT NULL,
  `nombreVia` varchar(255) DEFAULT NULL,
  `otrosDireccion` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  `idCentro` int(11) DEFAULT NULL,
  `url` VARCHAR(255) DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `persona` VARCHAR(255) DEFAULT NULL,
  `telefono` VARCHAR(15) DEFAULT NULL,
  `fax` VARCHAR(15) DEFAULT NULL,
  `textoLibre` TEXT DEFAULT NULL,
  PRIMARY KEY (`idEmpresa`),
  KEY `KEY_EMPRESA_MUNICIPIO` (`idMunicipio`),
  CONSTRAINT `KEY_EMPRESA_MUNICIPIO` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`),
  KEY `KEY_EMPRESA_Centro` (`idCentro`),
  CONSTRAINT `KEY_EMPRESA_Centro` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`idCentro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `oferta` (
    `idOferta` int(11) NOT NULL AUTO_INCREMENT,
    `fecha` date DEFAULT NULL,
    `idEmpresa` integer DEFAULT NULL,
    `puesto` varchar(255) DEFAULT NULL,
    `descripcion` longtext DEFAULT NULL,
    `idMunicipio` integer DEFAULT NULL,
    `idFamilia` integer DEFAULT NULL,
    `tipoOferta` varchar(20) DEFAULT NULL,
    `cerrada` int(11) DEFAULT NULL,
    `url` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `persona` VARCHAR(255) DEFAULT NULL,
    `telefono` VARCHAR(15) DEFAULT NULL,
    `fax` VARCHAR(15) DEFAULT NULL,
    `textoLibre` TEXT DEFAULT NULL,
    PRIMARY KEY  (`idOferta`),
    KEY `KEY_OFERTA_EMPRESA`   (`idEmpresa`)  ,CONSTRAINT `KEY_OFERTA_EMPRESA`   FOREIGN KEY (`idEmpresa`)   REFERENCES `empresa`   (`idEmpresa`),
    KEY `KEY_OFERTA_MUNICIPIO` (`idMunicipio`),CONSTRAINT `KEY_OFERTA_MUNICIPIO` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`),
    KEY `KEY_OFERTA_FAMILIA`   (`idFamilia`)  ,CONSTRAINT `KEY_OFERTA_FAMILIA`   FOREIGN KEY (`idFamilia`)   REFERENCES `familia`   (`idFamilia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ofertaciclo` (
    `idOfertaCiclo` int(11) NOT NULL AUTO_INCREMENT,
    `idOferta` integer not null,
    `idCiclo` integer not null,
    PRIMARY KEY  (`idOfertaCiclo`),
    KEY `KEY_OFERTACICLO_OFERTA`(`idOferta`),CONSTRAINT `KEY_OFERTACICLO_OFERTA` FOREIGN KEY (`idOferta`) REFERENCES `oferta`(`idOferta`),
    KEY `KEY_OFERTACICLO_CICLO` (`idCiclo`) ,CONSTRAINT `KEY_OFERTACICLO_CICLO`  FOREIGN KEY (`idCiclo`)  REFERENCES `ciclo` (`idCiclo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `candidato` (
    `idCandidato` int(11) NOT NULL AUTO_INCREMENT,
    `idOferta` integer not null,
    `idIdentity` integer not null,
    `fecha` date DEFAULT NULL,
    `rechazado` int(11) DEFAULT NULL,    
    PRIMARY KEY  (`idCandidato`),
    KEY `KEY_CANDIDATO_OFERTA`(`idOferta`),CONSTRAINT `KEY_CANDIDATO_OFERTA` FOREIGN KEY (`idOferta`) REFERENCES `oferta`(`idOferta`),
    KEY `KEY_CANDIDATO_USER` (`idIdentity`) ,CONSTRAINT `KEY_CANDIDATO_USER`  FOREIGN KEY (`idIdentity`)  REFERENCES `sec_user` (`idIdentity`),
    UNIQUE INDEX `CANDIDATO_UNICO_OFERTA` (`idOferta`, `idIdentity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;