/** Permisos del usuario **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Usuario.search', NULL, 'arguments.get("namedSearch")=="getUsuarioFromTitulado" && arguments.get("parametersMap").get("idTitulado").length==1 && arguments.get("parametersMap").get("idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Usuario.read', NULL, 'arguments.get("id")==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Usuario.update', NULL, '(arguments.get("originalEntity").idIdentity==identity.idIdentity) && (arguments.get("originalEntity").titulado?.idTitulado==arguments.get("entity").titulado?.idTitulado)', 1, NULL),
('Allow', 22, 32, 'UsuarioCURDBusinessProcess.updatePassword', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL),
('Allow', 22, 32, 'UsuarioCURDBusinessProcess.getFoto', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL),
('Allow', 22, 32, 'UsuarioCURDBusinessProcess.updateFoto', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL);


/** Permisos del Titulado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Titulado.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.read', NULL, '((arguments.get("id")==0) || (arguments.get("id")==identity.titulado.idTitulado))', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.insert', NULL, 'identity.titulado==null', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.update', NULL, 'identity.titulado!=null && arguments.get("originalEntity").idTitulado==identity.titulado.idTitulado', 1, NULL);

/** Permisos de la formacion academica **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.insert', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.FormacionAcademica.read', NULL, 'arguments.commandResult.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.update', NULL, 'arguments.get("originalEntity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.delete', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.search', NULL, 'arguments.get("parametersMap").get("titulado.idTitulado").length==1 && arguments.get("parametersMap").get("titulado.idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL);


/** Permisos de la experiencia laboral **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.insert', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.ExperienciaLaboral.read', NULL, 'arguments.commandResult.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.update', NULL, 'arguments.get("originalEntity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.delete', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.search', NULL, 'arguments.get("parametersMap").get("titulado.idTitulado").length==1 && arguments.get("parametersMap").get("titulado.idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL);



/** Permisos del título de idioma **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.insert', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.TituloIdioma.read', NULL, 'arguments.commandResult.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.update', NULL, 'arguments.get("originalEntity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.delete', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.search', NULL, 'arguments.get("parametersMap").get("titulado.idTitulado").length==1 && arguments.get("parametersMap").get("titulado.idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL);


/** Permisos de la oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Oferta.search', NULL, '(arguments.get("namedSearch")=="getOfertasUsuarioTitulado" || arguments.get("namedSearch")=="getOfertasInscritoUsuarioTitulado" ) && arguments.get("parametersMap").get("usuario").length==1 && arguments.get("parametersMap").get("usuario")[0]==""+identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Oferta.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.Oferta.read', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar ciclosOferta=arguments.getCommandResult().getResult().getCiclos();\r\nvar formacionesAcademicas=identity.titulado.formacionesAcademicas;\r\n\r\n\r\nfunction isRealizadoCiclo(formacionesAcademicas,idCiclo) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.ciclo.idCiclo===idCiclo) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\nfor (var iterator = ciclosOferta.iterator(); iterator.hasNext();) {\r\n   var ciclo=iterator.next();\r\n   if ( isRealizadoCiclo(formacionesAcademicas,ciclo.idCiclo)===true) {\r\n      return false;\r\n   }\r\n\r\n}\r\n\r\nreturn true;\r\n', NULL, 1, NULL);

/** Permisos del Candidado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Candidato.insert', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar candidato=arguments.get("entity");\r\nvar ciclosOferta=candidato.getOferta().getCiclos();\r\nvar usuario=candidato.usuario;\r\n\r\nif (usuario.idIdentity!==identity.idIdentity) {\r\n  return false;\r\n}\r\n\r\nvar formacionesAcademicas=identity.titulado.formacionesAcademicas;\r\n\r\n\r\nfunction isRealizadoCiclo(formacionesAcademicas,idCiclo) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.ciclo.idCiclo===idCiclo) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\nfor (var iterator = ciclosOferta.iterator(); iterator.hasNext();) {\r\n   var ciclo=iterator.next();\r\n   if ( isRealizadoCiclo(formacionesAcademicas,ciclo.idCiclo)===true) {\r\n      return true;\r\n   }\r\n\r\n}\r\n\r\nreturn false;\r\n', NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Candidato.delete', NULL, 'arguments.get("entity").usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Candidato.search', NULL, 'arguments.get("parametersMap").get("usuario.idIdentity").length==1 && arguments.get("parametersMap").get("usuario.idIdentity")[0]==""+identity.idIdentity', 1, NULL);

