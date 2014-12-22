CREATE TABLE IF NOT EXISTS `titulado` (
  `idTitulado` int(11) NOT NULL AUTO_INCREMENT,
  `idIdentity` int(11) DEFAULT NULL,
  `fechaNacimiento` datetime DEFAULT NULL,
  `tipoVia` int(11) DEFAULT NULL,
  `nombreVia` varchar(255) DEFAULT NULL,
  `otrosDireccion` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `idProvincia` int(11) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `telefonoAlternativo` varchar(255) DEFAULT NULL,
  `tipoDocumento` int(11) DEFAULT NULL,
  `numeroDocumento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idTitulado`),
  KEY `FK94E67D96B3306F0C` (`idIdentity`),
  KEY `FK94E67D96DA49940A` (`idProvincia`),
  KEY `FK94E67D96391E4C8A` (`idMunicipio`),
  CONSTRAINT `FK94E67D96391E4C8A` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`),
  CONSTRAINT `FK94E67D96B3306F0C` FOREIGN KEY (`idIdentity`) REFERENCES `usuario` (`idIdentity`),
  CONSTRAINT `FK94E67D96DA49940A` FOREIGN KEY (`idProvincia`) REFERENCES `provincia` (`idProvincia`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `experiencialaboral` (
  `idExperienciaLaboral` int(11) NOT NULL AUTO_INCREMENT,
  `nombreEmpresa` varchar(255) DEFAULT NULL,
  `fechaInicio` datetime DEFAULT NULL,
  `fechaFin` datetime DEFAULT NULL,
  `puestoTrabajo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `idTitulado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idExperienciaLaboral`),
  KEY `FK5E73A0484C28477E` (`idTitulado`),
  CONSTRAINT `FK5E73A0484C28477E` FOREIGN KEY (`idTitulado`) REFERENCES `titulado` (`idTitulado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS `formacionacademica` (
  `idFormacionAcademica` int(11) NOT NULL AUTO_INCREMENT,
  `tipoFormacionAcademica` int(11) DEFAULT NULL,
  `otroCentro` varchar(255) DEFAULT NULL,
  `otroTitulo` varchar(255) DEFAULT NULL,
  `idCentro` int(11) DEFAULT NULL,
  `idCiclo` int(11) DEFAULT NULL,
  `idTitulado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFormacionAcademica`),
  KEY `FK7C4E0018EE23894D` (`idCentro`),
  KEY `FK7C4E0018E6B99AB0` (`idCiclo`),
  KEY `FK7C4E00184C28477E` (`idTitulado`),
  CONSTRAINT `FK7C4E00184C28477E` FOREIGN KEY (`idTitulado`) REFERENCES `titulado` (`idTitulado`),
  CONSTRAINT `FK7C4E0018E6B99AB0` FOREIGN KEY (`idCiclo`) REFERENCES `ciclo` (`idCiclo`),
  CONSTRAINT `FK7C4E0018EE23894D` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`idCentro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tituloidioma` (
  `idTituloIdioma` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `idioma` int(11) DEFAULT NULL,
  `otroIdioma` varchar(255) DEFAULT NULL,
  `nivelIdioma` int(11) DEFAULT NULL,
  `idTitulado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTituloIdioma`),
  KEY `FK51BC8E0E4C28477E` (`idTitulado`),
  CONSTRAINT `FK51BC8E0E4C28477E` FOREIGN KEY (`idTitulado`) REFERENCES `titulado` (`idTitulado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;