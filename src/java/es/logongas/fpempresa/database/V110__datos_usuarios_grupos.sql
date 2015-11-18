INSERT INTO `sec_identity` (`ididentity`, `login`, `name`) VALUES (31, 'GAdministradores', 'Administradores');
INSERT INTO `sec_group` (`ididentity`) VALUES (31);

INSERT INTO `sec_identity` (`ididentity`, `login`, `name`) VALUES (32, 'GTitulados', 'Titulados');
INSERT INTO `sec_group` (`ididentity`) VALUES (32);

INSERT INTO `sec_identity` (`ididentity`, `login`, `name`) VALUES (33, 'GCentros', 'Centros');
INSERT INTO `sec_group` (`ididentity`) VALUES (33);

INSERT INTO `sec_identity` (`ididentity`, `login`, `name`) VALUES (34, 'GEmpresas', 'Empresas');
INSERT INTO `sec_group` (`ididentity`) VALUES (34);

INSERT INTO `sec_identity` (`ididentity`, `login`, `name`) VALUES (35, 'GUsuarios', 'Usuarios');
INSERT INTO `sec_group` (`ididentity`) VALUES (35);

INSERT INTO `sec_identity` (`ididentity`, `login`, `name`) VALUES (30, 'administrador@fpempresa.net', 'Administrador');
INSERT INTO `sec_user` (`ididentity`) VALUES (30);
INSERT INTO `usuario` (`idIdentity`, `email`, `nombre`, `apellidos`, `password`, `tipoUsuario`, `estadoUsuario`, `idCentro`, `idTitulado`, `idEmpresa`, `validadoEmail`, `claveValidacionEmail`, `foto`, `fecha`) VALUES
	(30, 'administrador@fpempresa.net', 'Administrador', NULL, 'S4uc3v/BHo/W4ziIHFGdjJj+xW7RW8efTWe8WSiEh6P0jaLtAnfYaIf28mdginkR', 'ADMINISTRADOR', 'ACEPTADO', NULL, NULL, NULL, 1, 'D3MPCAPEWCLB3QTCC5KYQAS2X4======', NULL, NOW());
INSERT INTO `sec_groupmember` (`idGroupMember`, `idGroup`, `ididentity`, `priority`) VALUES (10, 31, 30, NULL);
INSERT INTO `sec_groupmember` (`idGroupMember`, `idGroup`, `ididentity`, `priority`) VALUES (11, 35, 30, NULL);






