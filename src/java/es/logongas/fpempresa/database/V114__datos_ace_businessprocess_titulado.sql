/** Permisos del usuario **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Usuario.getUsuarioFromTitulado', NULL, 'arguments.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Usuario.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Usuario.read', NULL, 'arguments.id==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Usuario.update', NULL, '(arguments.originalEntity.idIdentity==identity.idIdentity) && (arguments.originalEntity.titulado?.idTitulado==arguments.entity.titulado?.idTitulado)', 1, NULL),
('Allow', 22, 32, 'UsuarioCRUDBusinessProcess.Usuario.updatePassword', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, 'UsuarioCRUDBusinessProcess.Usuario.getFoto', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, 'UsuarioCRUDBusinessProcess.Usuario.updateFoto', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL);


/** Permisos del Titulado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Titulado.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.read', NULL, '((arguments.id==0) || (arguments.id==identity.titulado.idTitulado))', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.insert', NULL, 'identity.titulado==null', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Titulado.update', NULL, '(identity.titulado!=null) && arguments.originalEntity.idTitulado==identity.titulado.idTitulado', 1, NULL);

/** Permisos de la formacion academica **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.insert', NULL, 'arguments.entity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.FormacionAcademica.read', NULL, 'arguments.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.update', NULL, 'arguments.originalEntity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.delete', NULL, 'arguments.entity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.FormacionAcademica.search', NULL, 'arguments.filters.getUniquePropertyNameFilter("titulado.idTitulado",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.titulado.idTitulado', 1, NULL);


/** Permisos de la experiencia laboral **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.insert', NULL, 'arguments.entity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.ExperienciaLaboral.read', NULL, 'arguments.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.update', NULL, 'arguments.originalEntity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.delete', NULL, 'arguments.entity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.ExperienciaLaboral.search', NULL, 'arguments.filters.getUniquePropertyNameFilter("titulado.idTitulado",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.titulado.idTitulado', 1, NULL);



/** Permisos del título de idioma **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.create', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.insert', NULL, 'arguments.entity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.TituloIdioma.read', NULL, 'arguments.result.titulado.idTitulado!=identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.update', NULL, 'arguments.originalEntity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.delete', NULL, 'arguments.entity.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.TituloIdioma.search', NULL, 'arguments.filters.getUniquePropertyNameFilter("titulado.idTitulado",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.titulado.idTitulado', 1, NULL);


/** Permisos de la oferta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Oferta.schema', NULL, NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Oferta.getOfertasUsuarioTitulado', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Oferta.getOfertasInscritoUsuarioTitulado', NULL, 'arguments.usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Oferta.read', NULL, NULL, 1, NULL),
('Deny' , 23, 32, '.*BusinessProcess.Oferta.read', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar ciclosOferta=arguments.result.getCiclos();\r\nvar formacionesAcademicas=identity.titulado.formacionesAcademicas;\r\n\r\n\r\nfunction isRealizadoCiclo(formacionesAcademicas,idCiclo) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.ciclo.idCiclo===idCiclo) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\nfor (var iterator = ciclosOferta.iterator(); iterator.hasNext();) {\r\n   var ciclo=iterator.next();\r\n   if ( isRealizadoCiclo(formacionesAcademicas,ciclo.idCiclo)===true) {\r\n      return false;\r\n   }\r\n\r\n}\r\n\r\nreturn true;\r\n', NULL, 1, NULL);

/** Permisos del Candidado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, '.*BusinessProcess.Candidato.insert', 'importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica)\r\nvar candidato=arguments.entity;\r\nvar ciclosOferta=candidato.getOferta().getCiclos();\r\nvar usuario=candidato.usuario;\r\n\r\nif (usuario.idIdentity!==identity.idIdentity) {\r\n  return false;\r\n}\r\n\r\nvar formacionesAcademicas=identity.titulado.formacionesAcademicas;\r\n\r\n\r\nfunction isRealizadoCiclo(formacionesAcademicas,idCiclo) {\r\n    for (var iterator = formacionesAcademicas.iterator(); iterator.hasNext();) {\r\n       var formacionAcademica=iterator.next();\r\n        if (formacionAcademica.tipoFormacionAcademica===TipoFormacionAcademica.CICLO_FORMATIVO) {\r\n            if (formacionAcademica.ciclo.idCiclo===idCiclo) {\r\n                return true;\r\n            }\r\n        }\r\n   }\r\n   return false;\r\n}\r\nfor (var iterator = ciclosOferta.iterator(); iterator.hasNext();) {\r\n   var ciclo=iterator.next();\r\n   if ( isRealizadoCiclo(formacionesAcademicas,ciclo.idCiclo)===true) {\r\n      return true;\r\n   }\r\n\r\n}\r\n\r\nreturn false;\r\n', NULL, 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Candidato.delete', NULL, 'arguments.entity.usuario.idIdentity==identity.idIdentity', 1, NULL),
('Allow', 22, 32, '.*BusinessProcess.Candidato.search', NULL, 'arguments.filters.getUniquePropertyNameFilter("usuario.idIdentity",T(es.logongas.ix3.dao.FilterOperator).eq)?.getValue()==identity.idIdentity', 1, NULL);

