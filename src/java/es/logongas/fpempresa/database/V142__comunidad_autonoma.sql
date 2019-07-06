CREATE TABLE `comunidadautonoma` (
	`idComunidadAutonoma` INT(11) NOT NULL AUTO_INCREMENT,
	`descripcion` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`idComunidadAutonoma`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=20;

INSERT INTO `comunidadautonoma` (`idComunidadAutonoma`, `descripcion`) VALUES
(1,  'Andalucía'),
(2,  'Aragón'),
(3,  'Asturias, Principado de'),
(4,  'Balears, Illes'),
(5,  'Canarias'),
(6,  'Cantabria'),
(7,  'Castilla y León'),
(8,  'Castilla - La Mancha'),
(9,  'Cataluña'),
(10, 'Comunitat Valenciana'),
(11, 'Extremadura'),
(12, 'Galicia'),
(13, 'Madrid, Comunidad de'),
(14, 'Murcia, Región de'),
(15, 'Navarra, Comunidad Foral de'),
(16, 'País Vasco'),
(17, 'Rioja, La'),
(18, 'Ceuta'),
(19, 'Melilla');


ALTER TABLE `provincia`
	ADD COLUMN `idComunidadAutonoma` INT(11) NULL DEFAULT NULL AFTER `descripcion`,
	ADD CONSTRAINT `ConunidadAutonoma` FOREIGN KEY (`idComunidadAutonoma`) REFERENCES `comunidadautonoma` (`idComunidadAutonoma`);

UPDATE provincia SET idcomunidadautonoma=16 WHERE idprovincia=1;
UPDATE provincia SET idcomunidadautonoma=8  WHERE idprovincia=2;
UPDATE provincia SET idcomunidadautonoma=10 WHERE idprovincia=3;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=4;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=5;
UPDATE provincia SET idcomunidadautonoma=11 WHERE idprovincia=6;
UPDATE provincia SET idcomunidadautonoma=4  WHERE idprovincia=7;
UPDATE provincia SET idcomunidadautonoma=9  WHERE idprovincia=8;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=9;
UPDATE provincia SET idcomunidadautonoma=11 WHERE idprovincia=10;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=11;
UPDATE provincia SET idcomunidadautonoma=10 WHERE idprovincia=12;
UPDATE provincia SET idcomunidadautonoma=8  WHERE idprovincia=13;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=14;
UPDATE provincia SET idcomunidadautonoma=12 WHERE idprovincia=15;
UPDATE provincia SET idcomunidadautonoma=8  WHERE idprovincia=16;
UPDATE provincia SET idcomunidadautonoma=9  WHERE idprovincia=17;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=18;
UPDATE provincia SET idcomunidadautonoma=8  WHERE idprovincia=19;
UPDATE provincia SET idcomunidadautonoma=16 WHERE idprovincia=20;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=21;
UPDATE provincia SET idcomunidadautonoma=2  WHERE idprovincia=22;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=23;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=24;
UPDATE provincia SET idcomunidadautonoma=9  WHERE idprovincia=25;
UPDATE provincia SET idcomunidadautonoma=17 WHERE idprovincia=26;
UPDATE provincia SET idcomunidadautonoma=12 WHERE idprovincia=27;
UPDATE provincia SET idcomunidadautonoma=13 WHERE idprovincia=28;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=29;
UPDATE provincia SET idcomunidadautonoma=14 WHERE idprovincia=30;
UPDATE provincia SET idcomunidadautonoma=15 WHERE idprovincia=31;
UPDATE provincia SET idcomunidadautonoma=12 WHERE idprovincia=32;
UPDATE provincia SET idcomunidadautonoma=3  WHERE idprovincia=33;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=34;
UPDATE provincia SET idcomunidadautonoma=5  WHERE idprovincia=35;
UPDATE provincia SET idcomunidadautonoma=12 WHERE idprovincia=36;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=37;
UPDATE provincia SET idcomunidadautonoma=5  WHERE idprovincia=38;
UPDATE provincia SET idcomunidadautonoma=6  WHERE idprovincia=39;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=40;
UPDATE provincia SET idcomunidadautonoma=1  WHERE idprovincia=41;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=42;
UPDATE provincia SET idcomunidadautonoma=9  WHERE idprovincia=43;
UPDATE provincia SET idcomunidadautonoma=2  WHERE idprovincia=44;
UPDATE provincia SET idcomunidadautonoma=8  WHERE idprovincia=45;
UPDATE provincia SET idcomunidadautonoma=10 WHERE idprovincia=46;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=47;
UPDATE provincia SET idcomunidadautonoma=16 WHERE idprovincia=48;
UPDATE provincia SET idcomunidadautonoma=7  WHERE idprovincia=49;
UPDATE provincia SET idcomunidadautonoma=2  WHERE idprovincia=50;
UPDATE provincia SET idcomunidadautonoma=18 WHERE idprovincia=51;
UPDATE provincia SET idcomunidadautonoma=19 WHERE idprovincia=52;
