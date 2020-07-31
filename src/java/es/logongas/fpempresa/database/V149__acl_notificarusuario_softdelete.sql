INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, 'UsuarioCRUDBusinessProcess.Usuario.notificarUsuarioInactivo', NULL, null, 1, NULL);

INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, 'UsuarioCRUDBusinessProcess.Usuario.softDelete', NULL, null, 1, NULL);