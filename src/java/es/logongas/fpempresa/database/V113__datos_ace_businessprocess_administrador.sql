
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, 'EstadisticasBusinessProcess.getEstadisticasAdministrador', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*BusinessProcess.Centro\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*BusinessProcess.Empresa\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*BusinessProcess.Oferta\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*BusinessProcess.Usuario\\..*', NULL, NULL, 1, NULL),
('Allow', 22, 31, 'UsuarioCRUDBusinessProcess.updatePassword', NULL, NULL, 1, NULL),
('Allow', 22, 31, 'LogBusinessProcess.getLogLevel', NULL, NULL, 1, NULL),
('Allow', 22, 31, 'LogBusinessProcess.setLogLevel', NULL, NULL, 1, NULL);

