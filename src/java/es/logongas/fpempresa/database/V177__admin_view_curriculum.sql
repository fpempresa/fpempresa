/** Permisos del Titulado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, '.*BusinessProcess.Titulado.*', NULL, NULL, 1, NULL);

/** Permisos de la formacion academica **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, '.*BusinessProcess.FormacionAcademica.*', NULL, NULL, 1, NULL);

/** Permisos de la experiencia laboral **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, '.*BusinessProcess.ExperienciaLaboral.*', NULL, NULL, 1, NULL);

/** Permisos del título de idioma **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, '.*BusinessProcess.TituloIdioma.*', NULL, NULL, 1, NULL);