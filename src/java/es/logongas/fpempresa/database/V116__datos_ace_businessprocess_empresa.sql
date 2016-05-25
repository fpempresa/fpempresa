/** Estadisticas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, 'EstadisticasBusinessProcess.getEstadisticasEmpresa', NULL, 'arguments.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL);

/** Empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*BusinessProcess.Empresa.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.insert', NULL, 'identity.empresa==null', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.read', NULL, 'arguments.id==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Empresa.update', NULL, '(arguments.originalEntity.idEmpresa==identity.empresa.idEmpresa)', 1, NULL);

/** Usuarios de la empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*BusinessProcess.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.insert', NULL, '(arguments.entity.tipoUsuario==T(es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario).EMPRESA) && (arguments.entity.empresa.idEmpresa==identity.empresa.idEmpresa)', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.update', NULL, 'arguments.originalEntity.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.delete', NULL, 'arguments.entity.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*BusinessProcess.Usuario.read', NULL, 'arguments.result.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Usuario.pageableSearch', NULL, '(arguments.filters.getUniquePropertyNameFilter("tipoUsuario",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==T(es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario).EMPRESA) && (arguments.filters.getUniquePropertyNameFilter("empresa.idEmpresa",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.empresa.idEmpresa)', 1, NULL),
('Allow', 22, 34, 'UsuarioCRUDBusinessProcess.Usuario.updatePassword', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL);


/** Oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 34, '.*BusinessProcess.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.create', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.insert', NULL, 'arguments.entity.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.update', NULL, 'arguments.originalEntity.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.delete', NULL, 'arguments.entity.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*BusinessProcess.Oferta.read', NULL, 'arguments.result.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Oferta.pageableSearch', NULL, '(arguments.filters.getUniquePropertyNameFilter("empresa.idEmpresa",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.empresa.idEmpresa)', 1, NULL);

/** Candidato **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 22, 34, '.*BusinessProcess.Candidato.schema', NULL, NULL, 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Candidato.read', NULL, NULL, 1, NULL),
('Deny',  23, 34, '.*BusinessProcess.Candidato.read', NULL, 'arguments.result.oferta.empresa.idEmpresa!=identity.empresa.idEmpresa', 1, NULL), 
('Allow', 22, 34, '.*BusinessProcess.Candidato.getNumCandidatosOferta', NULL, 'arguments.oferta.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL),
('Allow', 22, 34, '.*BusinessProcess.Candidato.getCandidatosOferta', NULL, '(arguments.oferta.empresa.idEmpresa==identity.empresa.idEmpresa)', 1, NULL),
('Allow', 22, 34, 'CandidatoCRUDBusinessProcess.Candidato.getFotoCandidato', NULL, 'arguments.candidato.oferta.empresa.idEmpresa==identity.empresa.idEmpresa', 1, NULL);

