DROP TABLE IF EXISTS `ace`;
DROP TABLE IF EXISTS `permission`;
DROP TABLE IF EXISTS `secureresource`;
DROP TABLE IF EXISTS `secureresourcetype`;
DROP TABLE IF EXISTS `groupmember`;
DROP TABLE IF EXISTS `groupp`;
DROP TABLE IF EXISTS `userr`;
DROP TABLE IF EXISTS `identity`;




CREATE TABLE IF NOT EXISTS `identity` (
  `ididentity` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ididentity`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `userr` (
  `ididentity` int(11) NOT NULL,
  PRIMARY KEY (`ididentity`),
  KEY `FK285FEB1EDD9A75` (`ididentity`),
  CONSTRAINT `FK285FEB1EDD9A75` FOREIGN KEY (`ididentity`) REFERENCES `identity` (`ididentity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `groupp` (
  `ididentity` int(11) NOT NULL,
  PRIMARY KEY (`ididentity`),
  KEY `FK41E065F1EDD9A75` (`ididentity`),
  CONSTRAINT `FK41E065F1EDD9A75` FOREIGN KEY (`ididentity`) REFERENCES `identity` (`ididentity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `groupmember` (
  `idGroupMember` int(11) NOT NULL AUTO_INCREMENT,
  `idGroup` int(11) DEFAULT NULL,
  `ididentity` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`idGroupMember`),
  KEY `FK8598F9D9AAEDEABC` (`idGroup`),
  KEY `FK8598F9D91EDD9A75` (`ididentity`),
  CONSTRAINT `FK8598F9D91EDD9A75` FOREIGN KEY (`ididentity`) REFERENCES `identity` (`ididentity`),
  CONSTRAINT `FK8598F9D9AAEDEABC` FOREIGN KEY (`idGroup`) REFERENCES `groupp` (`ididentity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;










CREATE TABLE IF NOT EXISTS `secureresourcetype` (
  `idSecureResourceType` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idSecureResourceType`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `secureresource` (
  `idSecureResource` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `idSecureResourceType` int(11) DEFAULT NULL,
  PRIMARY KEY (`idSecureResource`),
  UNIQUE KEY `name_idSecureResourceType` (`name`,`idSecureResourceType`),
  KEY `FK920CE8C5850089C0` (`idSecureResourceType`),
  CONSTRAINT `FK920CE8C5850089C0` FOREIGN KEY (`idSecureResourceType`) REFERENCES `secureresourcetype` (`idSecureResourceType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS `permission` (
  `idPermission` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `idSecureResourceType` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPermission`),
  UNIQUE KEY `name_idSecureResourceType` (`name`,`idSecureResourceType`),
  KEY `FK57F7A1EF850089C0` (`idSecureResourceType`),
  CONSTRAINT `FK57F7A1EF850089C0` FOREIGN KEY (`idSecureResourceType`) REFERENCES `secureresourcetype` (`idSecureResourceType`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ace` (
  `idACE` int(11) NOT NULL AUTO_INCREMENT,
  `aceType` varchar(255) DEFAULT NULL,
  `idPermission` int(11) DEFAULT NULL,
  `ididentity` int(11) DEFAULT NULL,
  `secureResourceRegExp` varchar(255) DEFAULT NULL,
  `conditionalScript` text,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`idACE`),
  KEY `FKFC63E44E74A0` (`idPermission`),
  KEY `FKFC631EDD9A75` (`ididentity`),
  CONSTRAINT `FKFC631EDD9A75` FOREIGN KEY (`ididentity`) REFERENCES `identity` (`ididentity`),
  CONSTRAINT `FKFC63E44E74A0` FOREIGN KEY (`idPermission`) REFERENCES `permission` (`idPermission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

