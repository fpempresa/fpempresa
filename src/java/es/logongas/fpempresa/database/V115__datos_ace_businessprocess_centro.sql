
/** Estadisticas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, 'EstadisticasBusinessProcess.getEstadisticasCentro', NULL, 'arguments.centro.idCentro==identity.centro.idCentro', 1, NULL);


/** Centro **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*BusinessProcess.Centro.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Centro.read', NULL, 'arguments.id==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Centro.update', NULL, 'arguments.originalEntity.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Centro.pageableSearch', NULL, '(arguments.pageRequest.pageSize<=100)', 1, NULL);


/** Usuarios **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*BusinessProcess.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Usuario.pageableSearch', NULL, '((arguments.filters.getUniquePropertyNameFilter("tipoUsuario",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==T(es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario).TITULADO) && (arguments.filters.getUniquePropertyNameFilter("titulado.formacionesAcademicas.centro.idCentro",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.centro.idCentro) && (arguments.pageRequest.pageSize<=100))', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Usuario.pageableSearch', NULL, '((arguments.filters.getUniquePropertyNameFilter("tipoUsuario",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==T(es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario).CENTRO) && (arguments.filters.getUniquePropertyNameFilter("centro.idCentro",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.centro.idCentro) && (arguments.pageRequest.pageSize<=100))', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Usuario.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*BusinessProcess.Usuario.read', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica);\r\nimportClass(Packages.es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario);\r\nvar idCentro=identity.centro.idCentro;\r\nvar usuario=arguments.result;\r\n\r\nif (usuario.tipoUsuario===TipoUsuario.CENTRO) {\r\n\r\n    if (usuario.centro.idCentro===idCentro) {\r\n       return false;\r\n    } else {\r\n       return true;\r\n    }\r\n\r\n} else if (usuario.tipoUsuario===TipoUsuario.TITULADO) {\r\n\r\n    var formacionesAcademicas=usuario.titulado.formacionesAcademicas;\r\n\r\n    \r\n    function isRealizadoEnCentro(formacionesAcademicas,idCentro) {\r\n        for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n           var formacionAcademica=iterator.next();\r\n            if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n                if (formacionAcademica.centro.idCentro===idCentro) {\r\n                    return true;\r\n                }\r\n            }\r\n       }\r\n       return false;\r\n    }\r\n    \r\n    if (isRealizadoEnCentro(formacionesAcademicas,idCentro)) {\r\n      return false;\r\n    } else {\r\n      return true;\r\n    }\r\n\r\n} else {\r\n  return true;\r\n}\r\n\r\n', NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Usuario.update', NULL, 'arguments.originalEntity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Usuario.delete', NULL, 'arguments.entity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, 'UsuarioCRUDBusinessProcess.Usuario.updateCentro', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 33, 'UsuarioCRUDBusinessProcess.Usuario.getFoto', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar formacionesAcademicas=arguments.usuario.titulado.formacionesAcademicas;\r\nvar idCentro=identity.centro.idCentro;\r\n\r\nfunction isRealizadoEnCentro(formacionesAcademicas,idCentro) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.centro.idCentro===idCentro) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\n\r\nif (isRealizadoEnCentro(formacionesAcademicas,idCentro)) {\r\n  return true;\r\n} else {\r\n  return false;\r\n}\r\n\r\n', NULL, 1, NULL),
('Allow', 22, 33, 'UsuarioCRUDBusinessProcess.Usuario.updatePassword', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL);

/** CertificadoTitulo **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*BusinessProcess.CertificadoTitulo.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.CertificadoTitulo.create', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.CertificadoTitulo.insert', NULL, 'arguments.entity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.CertificadoTitulo.update', NULL, 'arguments.originalEntity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.CertificadoTitulo.delete', NULL, 'arguments.entity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.CertificadoTitulo.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*BusinessProcess.CertificadoTitulo.read', NULL, 'arguments.result.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.CertificadoTitulo.pageableSearch', NULL, '(arguments.filters.getUniquePropertyNameFilter("centro.idCentro",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.centro.idCentro) &&  (arguments.pageRequest.pageSize<=100)', 1, NULL);


/** Empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*BusinessProcess.Empresa.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Empresa.create', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Empresa.insert', NULL, 'arguments.entity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Empresa.update', NULL, 'arguments.originalEntity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Empresa.delete', NULL, 'arguments.entity.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Empresa.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*BusinessProcess.Empresa.read', NULL, 'arguments.result.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Empresa.pageableSearch', NULL, '(arguments.filters.getUniquePropertyNameFilter("centro.idCentro",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.centro.idCentro) && (arguments.pageRequest.pageSize<=100)', 1, NULL);

/** Oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*BusinessProcess.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Oferta.create', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Oferta.insert', NULL, 'arguments.entity.empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Oferta.update', NULL, 'arguments.originalEntity.empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Oferta.delete', NULL, 'arguments.entity.empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Oferta.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*BusinessProcess.Oferta.read', NULL, 'arguments.result.empresa.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Oferta.pageableSearch', NULL, '(arguments.filters.getUniquePropertyNameFilter("empresa.centro.idCentro",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.centro.idCentro) && (arguments.pageRequest.pageSize<=100)', 1, NULL);

/** Candidato **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*BusinessProcess.Candidato.getNumCandidatosOferta', NULL, 'arguments.oferta.empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Candidato.getCandidatosOferta', NULL, '(arguments.oferta.empresa.centro.idCentro==identity.centro.idCentro) && (arguments.pageRequest.pageSize<=100)', 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Candidato.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*BusinessProcess.Candidato.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*BusinessProcess.Candidato.read', NULL, 'arguments.result.oferta.empresa.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, 'CandidatoCRUDBusinessProcess.Candidato.getFotoCandidato', NULL, 'arguments.candidato.oferta.empresa.centro.idCentro==identity.centro.idCentro', 1, NULL);
