
/** Estadisticas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, 'EstadisticasController.getEstadisticasCentro', NULL, 'arguments.get("idCentro")==identity.centro.idCentro', 1, NULL);


/** Centro **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*Controller.Centro.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Centro.read', NULL, 'arguments.get("id")==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Centro.update', NULL, 'arguments.get("originalEntity").idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Centro.search', NULL, NULL, 1, NULL);


/** Usuarios **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*Controller.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Usuario.search', NULL, 'arguments.get("parametersMap").get("tipoUsuario").length==1 && arguments.get("parametersMap").get("tipoUsuario")[0]=="TITULADO" && arguments.get("parametersMap").get("titulado.formacionesAcademicas.centro.idCentro").length==1 && arguments.get("parametersMap").get("titulado.formacionesAcademicas.centro.idCentro")[0]==""+identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Usuario.search', NULL, 'arguments.get("parametersMap").get("tipoUsuario").length==1 && arguments.get("parametersMap").get("tipoUsuario")[0]=="CENTRO" && arguments.get("parametersMap").get("centro.idCentro").length==1 && arguments.get("parametersMap").get("centro.idCentro")[0]==""+identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Usuario.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*Controller.Usuario.read', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica);\r\nimportClass(Packages.es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario);\r\nvar idCentro=identity.centro.idCentro;\r\nvar usuario=arguments.commandResult.result;\r\n\r\nif (usuario.tipoUsuario===TipoUsuario.CENTRO) {\r\n\r\n    if (usuario.centro.idCentro===idCentro) {\r\n       return false;\r\n    } else {\r\n       return true;\r\n    }\r\n\r\n} else if (usuario.tipoUsuario===TipoUsuario.TITULADO) {\r\n\r\n    var formacionesAcademicas=usuario.titulado.formacionesAcademicas;\r\n\r\n    \r\n    function isRealizadoEnCentro(formacionesAcademicas,idCentro) {\r\n        for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n           var formacionAcademica=iterator.next();\r\n            if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n                if (formacionAcademica.centro.idCentro===idCentro) {\r\n                    return true;\r\n                }\r\n            }\r\n       }\r\n       return false;\r\n    }\r\n    \r\n    if (isRealizadoEnCentro(formacionesAcademicas,idCentro)) {\r\n      return false;\r\n    } else {\r\n      return true;\r\n    }\r\n\r\n} else {\r\n  return true;\r\n}\r\n\r\n', NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Usuario.update', NULL, 'arguments.get("originalEntity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Usuario.delete', NULL, 'arguments.get("entity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, 'UsuarioRESTController.updateCentro', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL),
('Allow', 22, 33, 'UsuarioRESTController.getFoto', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar formacionesAcademicas=arguments.get("usuario").titulado.formacionesAcademicas;\r\nvar idCentro=identity.centro.idCentro;\r\n\r\nfunction isRealizadoEnCentro(formacionesAcademicas,idCentro) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.centro.idCentro===idCentro) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\n\r\nif (isRealizadoEnCentro(formacionesAcademicas,idCentro)) {\r\n  return true;\r\n} else {\r\n  return false;\r\n}\r\n\r\n', NULL, 1, NULL),
('Allow', 22, 33, 'UsuarioRESTController.updatePassword', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL);

/** CertificadoTitulo **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*Controller.CertificadoTitulo.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.CertificadoTitulo.create', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.CertificadoTitulo.insert', NULL, 'arguments.get("entity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.CertificadoTitulo.update', NULL, 'arguments.get("originalEntity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.CertificadoTitulo.delete', NULL, 'arguments.get("entity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.CertificadoTitulo.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*Controller.CertificadoTitulo.read', NULL, 'arguments.commandResult.result.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*CrudRestController.CertificadoTitulo.search', NULL, 'arguments.get("parametersMap").get("centro.idCentro").length==1 && arguments.get("parametersMap").get("centro.idCentro")[0]==""+identity.centro.idCentro', 1, NULL);


/** Empresa **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*Controller.Empresa.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Empresa.create', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Empresa.insert', NULL, 'arguments.get("entity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Empresa.update', NULL, 'arguments.get("originalEntity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Empresa.delete', NULL, 'arguments.get("entity").centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Empresa.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*Controller.Empresa.read', NULL, 'arguments.commandResult.result.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*CrudRestController.Empresa.search', NULL, 'arguments.get("parametersMap").get("centro.idCentro").length==1 && arguments.get("parametersMap").get("centro.idCentro")[0]==""+identity.centro.idCentro', 1, NULL);

/** Oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*Controller.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Oferta.create', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Oferta.insert', NULL, 'arguments.get("entity").empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Oferta.update', NULL, 'arguments.get("originalEntity").empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Oferta.delete', NULL, 'arguments.get("entity").empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Oferta.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*Controller.Oferta.read', NULL, 'arguments.commandResult.result.empresa.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*CrudRestController.Oferta.search', NULL, 'arguments.get("parametersMap").get("empresa.centro.idCentro").length==1 && arguments.get("parametersMap").get("empresa.centro.idCentro")[0]==""+identity.centro.idCentro', 1, NULL);

/** Candidato **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, '.*Controller.Candidato.search', NULL, 'arguments.get("namedSearch")=="getNumCandidatosOferta" && arguments.get("parameters").get("oferta").empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Candidato.search', NULL, 'arguments.get("namedSearch")=="getCandidatosOferta" && arguments.get("parameters").get("oferta").empresa.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, '.*Controller.Candidato.schema', NULL, NULL, 1, NULL),
('Allow', 22, 33, '.*Controller.Candidato.read', NULL, NULL, 1, NULL),
('Deny',  23, 33, '.*Controller.Candidato.read', NULL, 'arguments.commandResult.result.oferta.empresa.centro.idCentro!=identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, 'CandidatoController.getFoto', NULL, 'arguments.get("candidato").oferta.empresa.centro.idCentro==identity.centro.idCentro', 1, NULL);
