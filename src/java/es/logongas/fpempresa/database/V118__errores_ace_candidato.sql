
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*BusinessProcess.Candidato.update', NULL, '(arguments.entity.oferta.empresa.idEmpresa==identity.empresa.idEmpresa) &&  (arguments.originalEntity.oferta.idOferta==arguments.entity.oferta.idOferta) &&  (arguments.originalEntity.usuario.idIdentity==arguments.entity.usuario.idIdentity)', 1, NULL);