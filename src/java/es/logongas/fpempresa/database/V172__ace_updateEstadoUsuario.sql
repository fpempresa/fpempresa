INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*BusinessProcess.Usuario.updateEstadoUsuario', NULL, 'arguments.usuario.centro.idCentro==identity.centro.idCentro', 1, NULL);