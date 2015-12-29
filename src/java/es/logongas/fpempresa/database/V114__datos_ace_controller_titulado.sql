/** Permisos del usuario **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*Controller.Usuario.search', NULL, 'arguments.get("namedSearch")=="getUsuarioFromTitulado" && arguments.get("parametersMap").get("idTitulado").length==1 && arguments.get("parametersMap").get("idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.Usuario.read', NULL, 'arguments.get("id")==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*Controller.Usuario.update', NULL, 'arguments.get("originalEntity").idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, 'UsuarioRESTController.updatePassword', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL),
('Allow', 22, 32, 'UsuarioRESTController.getFoto', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL),
('Allow', 22, 32, 'UsuarioRESTController.updateFoto', NULL, 'arguments.get("idUsuario")==identity.idIdentity', 1, NULL);


/** Permisos del Titulado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*Controller.Titulado.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.Titulado.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.Titulado.read', NULL, '((arguments.get("id")==0) || (arguments.get("id")==identity.titulado.idTitulado))', 1, NULL),
('Allow', 22, 32, '.*Controller.Titulado.insert', NULL, 'identity.titulado==null', 1, NULL),
('Allow', 22, 32, '.*Controller.Titulado.update', NULL, 'identity.titulado!=null && arguments.get("originalEntity").idTitulado==identity.titulado.idTitulado', 1, NULL);

/** Permisos de la formacion academica **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*Controller.FormacionAcademica.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.FormacionAcademica.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.FormacionAcademica.insert', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.FormacionAcademica.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*Controller.FormacionAcademica.read', NULL, 'arguments.commandResult.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.FormacionAcademica.update', NULL, 'arguments.get("originalEntity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.FormacionAcademica.delete', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.FormacionAcademica.search', NULL, 'arguments.get("parametersMap").get("titulado.idTitulado").length==1 && arguments.get("parametersMap").get("titulado.idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL);


/** Permisos de la experiencia laboral **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*Controller.ExperienciaLaboral.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.ExperienciaLaboral.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.ExperienciaLaboral.insert', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.ExperienciaLaboral.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*Controller.ExperienciaLaboral.read', NULL, 'arguments.commandResult.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.ExperienciaLaboral.update', NULL, 'arguments.get("originalEntity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.ExperienciaLaboral.delete', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.ExperienciaLaboral.search', NULL, 'arguments.get("parametersMap").get("titulado.idTitulado").length==1 && arguments.get("parametersMap").get("titulado.idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL);



/** Permisos del título de idioma **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*Controller.TituloIdioma.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.TituloIdioma.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.TituloIdioma.insert', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.TituloIdioma.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*Controller.TituloIdioma.read', NULL, 'arguments.commandResult.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.TituloIdioma.update', NULL, 'arguments.get("originalEntity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.TituloIdioma.delete', NULL, 'arguments.get("entity").titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*Controller.TituloIdioma.search', NULL, 'arguments.get("parametersMap").get("titulado.idTitulado").length==1 && arguments.get("parametersMap").get("titulado.idTitulado")[0]==""+identity.titulado.idTitulado', 1, NULL);


/** Permisos de la oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*Controller.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.Oferta.search', NULL, '(arguments.get("namedSearch")=="getOfertasUsuarioTitulado" || arguments.get("namedSearch")=="getOfertasInscritoUsuarioTitulado" ) && arguments.get("parametersMap").get("usuario").length==1 && arguments.get("parametersMap").get("usuario")[0]==""+identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*Controller.Oferta.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*Controller.Oferta.read', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar ciclosOferta=arguments.getCommandResult().getResult().getCiclos();\r\nvar formacionesAcademicas=identity.titulado.formacionesAcademicas;\r\n\r\n\r\nfunction isRealizadoCiclo(formacionesAcademicas,idCiclo) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.ciclo.idCiclo===idCiclo) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\nfor (var iterator = ciclosOferta.iterator(); iterator.hasNext();) {\r\n   var ciclo=iterator.next();\r\n   if ( isRealizadoCiclo(formacionesAcademicas,ciclo.idCiclo)===true) {\r\n      return false;\r\n   }\r\n\r\n}\r\n\r\nreturn true;\r\n', NULL, 1, NULL);

/** Permisos del Candidado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*Controller.Candidato.insert', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar candidato=arguments.get("entity");\r\nvar ciclosOferta=candidato.getOferta().getCiclos();\r\nvar usuario=candidato.usuario;\r\n\r\nif (usuario.idIdentity!==identity.idIdentity) {\r\n  return false;\r\n}\r\n\r\nvar formacionesAcademicas=identity.titulado.formacionesAcademicas;\r\n\r\n\r\nfunction isRealizadoCiclo(formacionesAcademicas,idCiclo) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.ciclo.idCiclo===idCiclo) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\nfor (var iterator = ciclosOferta.iterator(); iterator.hasNext();) {\r\n   var ciclo=iterator.next();\r\n   if ( isRealizadoCiclo(formacionesAcademicas,ciclo.idCiclo)===true) {\r\n      return true;\r\n   }\r\n\r\n}\r\n\r\nreturn false;\r\n', NULL, 1, NULL),
('Allow', 22, 32, '.*Controller.Candidato.delete', NULL, 'arguments.get("entity").usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*Controller.Candidato.search', NULL, 'arguments.get("parametersMap").get("usuario.idIdentity").length==1 && arguments.get("parametersMap").get("usuario.idIdentity")[0]==""+identity.idIdentity', 1, NULL);

