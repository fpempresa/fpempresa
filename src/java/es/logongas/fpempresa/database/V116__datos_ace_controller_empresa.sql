/** Estadisticas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, 'EstadisticasController.getEstadisticasEmpresa', NULL, 'arguments.get("idEmpresa")==identity.empresa.idEmpresa', 1, NULL);

/** Empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*Controller.Empresa.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*Controller.Empresa.read', NULL, 'arguments.get("id")==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Empresa.update', NULL, 'arguments.get("entity").idEmpresa==identity.empresa.idEmpresa', 1, NULL);

/** Usuarios de la empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*Controller.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*Controller.Usuario.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*Controller.Usuario.insert', NULL, 'arguments.get("entity").tipoUsuario="EMPRESA" && arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Usuario.update', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Usuario.delete', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Usuario.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*Controller.Usuario.read', NULL, 'arguments.commandResult.result.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Usuario.search', NULL, 'arguments.get("parametersMap").get("tipoUsuario").length==1 && arguments.get("parametersMap").get("tipoUsuario")[0]=="EMPRESA" && arguments.get("parametersMap").get("empresa.idEmpresa").length==1 && arguments.get("parametersMap").get("empresa.idEmpresa")[0]==""+identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, 'UsuarioRESTController.updatePassword', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL);


/** Oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*Controller.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*Controller.Oferta.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*Controller.Oferta.insert', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Oferta.update', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Oferta.delete', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Oferta.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*Controller.Oferta.read', NULL, 'arguments.commandResult.result.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Oferta.search', NULL, 'arguments.get("parametersMap").get("empresa.idEmpresa").length==1 && arguments.get("parametersMap").get("empresa.idEmpresa")[0]==""+identity.empresa.idEmpresa', 1, NULL);

/** Candidato **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 22, 34, '.*Controller.Candidato.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*Controller.Candidato.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*Controller.Candidato.read', NULL, 'arguments.commandResult.result.oferta.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL), 
('Allow', 22, 34, '.*Controller.Candidato.search', NULL, 'arguments.get("namedSearch")=="getNumCandidatosOferta" && arguments.get("parameters").get("oferta").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*Controller.Candidato.search', NULL, 'arguments.get("namedSearch")=="getCandidatosOferta" && arguments.get("parameters").get("oferta").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, 'CandidatoController.getFoto', NULL, 'arguments.get("candidato").oferta.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL);


