/** Estadisticas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, 'EstadisticasBusinessProcess.getEstadisticasEmpresa', NULL, 'arguments.get("idEmpresa")==identity.empresa.idEmpresa', 1, NULL);

/** Empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*BusinessProcess.Empresa.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.insert', NULL, 'identity.empresa==null', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.read', NULL, 'arguments.get("id")==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.update', NULL, '(arguments.get("originalEntity").idEmpresa==identity.empresa.idEmpresa) && (arguments.get("originalEntity").empresa?.idEmpresa==arguments.get("entity").empresa?.idEmpresa)', 1, NULL);

/** Usuarios de la empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*BusinessProcess.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.insert', NULL, 'arguments.get("entity").tipoUsuario==T(es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario).EMPRESA && arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.update', NULL, 'arguments.get("originalEntity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.delete', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*BusinessProcess.Usuario.read', NULL, 'arguments.commandResult.result.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.search', NULL, 'arguments.get("parametersMap").get("tipoUsuario").length==1 && arguments.get("parametersMap").get("tipoUsuario")[0]=="EMPRESA" && arguments.get("parametersMap").get("empresa.idEmpresa").length==1 && arguments.get("parametersMap").get("empresa.idEmpresa")[0]==""+identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, 'UsuarioCURDBusinessProcess.updatePassword', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL);


/** Oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*BusinessProcess.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.insert', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.update', NULL, 'arguments.get("originalEntity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.delete', NULL, 'arguments.get("entity").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*BusinessProcess.Oferta.read', NULL, 'arguments.commandResult.result.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.search', NULL, 'arguments.get("parametersMap").get("empresa.idEmpresa").length==1 && arguments.get("parametersMap").get("empresa.idEmpresa")[0]==""+identity.empresa.idEmpresa', 1, NULL);

/** Candidato **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 22, 34, '.*BusinessProcess.Candidato.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Candidato.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*BusinessProcess.Candidato.read', NULL, 'arguments.commandResult.result.oferta.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL), 
('Allow', 22, 34, '.*BusinessProcess.Candidato.search', NULL, 'arguments.get("namedSearch")=="getNumCandidatosOferta" && arguments.get("parameters").get("oferta").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Candidato.search', NULL, 'arguments.get("namedSearch")=="getCandidatosOferta" && arguments.get("parameters").get("oferta").empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, 'CandidatoCRUDBusinessProcess.getFoto', NULL, 'arguments.get("candidato").oferta.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL);


