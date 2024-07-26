INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, '.*BusinessProcess.Usuario.notificarUsuariosInactivos', NULL, NULL, 1, NULL),
('Allow', 22, 31, '.*BusinessProcess.Usuario.softDeleteUsuariosInactivosYNotificados', NULL, NULL, 1, NULL);
