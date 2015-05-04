INSERT INTO `sec_identity` (`ididentity`, `login`, `name`) VALUES
	(30, 'administrador@fpempresa.net', 'Administrador');

INSERT INTO `sec_user` (`ididentity`) VALUES
	(30);

INSERT INTO `usuario` (`idIdentity`, `email`, `nombre`, `apellidos`, `password`, `tipoUsuario`, `estadoUsuario`, `idCentro`, `idTitulado`, `idEmpresa`, `validadoEmail`, `claveValidacionEmail`, `foto`) VALUES
	(30, 'administrador@fpempresa.net', 'Administrador', NULL, 'S4uc3v/BHo/W4ziIHFGdjJj+xW7RW8efTWe8WSiEh6P0jaLtAnfYaIf28mdginkR', 'ADMINISTRADOR', 'ACEPTADO', NULL, NULL, NULL, 1, 'D3MPCAPEWCLB3QTCC5KYQAS2X4======', NULL);
