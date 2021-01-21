INSERT INTO `grado` (`idGrado`, `descripcion`) VALUES
	(4, 'Curso de Especialización');

INSERT INTO `ciclo` (`descripcion`, `idFamilia`, `idGrado`, `idLeyEducativa`) VALUES
	('Ciberseguridad en Entornos de las Tecnologías de Operación', 7, 4, 2),
	('Panadería y Bollería Artesanales', 9, 4, 2),
	('Audiodescripción y Subtitulación', 11, 4, 2),
	('Ciberseguridad en Entornos de las Tecnologías de la Información', 14, 4, 2),
	('Digitalización del Mantenimiento Industrial', 15, 4, 2),
	('Fabricación Inteligente', 15, 4, 2),
	('Cultivos Celulares', 18, 4, 2);
