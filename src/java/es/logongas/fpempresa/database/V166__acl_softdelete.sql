INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 35,'.*BusinessProcess\\.Usuario\\.softDelete', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, 'Te puedes borrar a ti mismo');