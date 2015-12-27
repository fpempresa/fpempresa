
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, 'EstadisticasController.getEstadisticasAdministrador', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*Controller.Centro\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*Controller.Empresa\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*Controller.Oferta\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*Controller.Usuario\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, 'UsuarioRESTController.updatePassword', NULL, NULL, 1, NULL),
('Allow', 22, 31, 'LogController.getLogLevel', NULL, NULL, 1, NULL),
('Allow', 22, 31, 'LogController.setLogLevel', NULL, NULL, 1, NULL);

