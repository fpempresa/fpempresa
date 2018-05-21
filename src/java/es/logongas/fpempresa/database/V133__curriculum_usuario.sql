INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, 'UsuarioCRUDBusinessProcess.Usuario.getCurriculum', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL);