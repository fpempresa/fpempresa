/** Quitar los permisos para que el centro no vea los titulados **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Deny', 22, 33, 'UsuarioCRUDBusinessProcess.Usuario.getFoto', NULL, NULL, -10, NULL),
('Deny', 22, 33, 'DownloadBusinessProcess.getHojaCalculoUsuariosTituladosCentro', null, null, -10, NULL);

DELETE  from sec_ace where idPermission=22 AND idIdentity=33 AND secureResourceRegExp=".*BusinessProcess.Usuario.pageableSearch" AND conditionalExpression like "%TITULADO%";

UPDATE sec_ace SET conditionalScript='importClass(Packages.es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica);\r\nimportClass(Packages.es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario);\r\nvar usuario=arguments.result;\r\nif (identity.idIdentity===usuario.idIdentity) {\r\nreturn false;\r\n}\r\nvar idCentro=identity.centro.idCentro;\r\n\r\nif (usuario.tipoUsuario===TipoUsuario.CENTRO) {\r\n\r\n    if (usuario.centro.idCentro===idCentro) {\r\n       return false;\r\n    } else {\r\n       return true;\r\n    }\r\n\r\n} else {\r\n  return true;\r\n}\r\n\r\n' 
WHERE aceType='Deny' AND idPermission=23 AND idIdentity=33 AND secureResourceRegExp='.*BusinessProcess.Usuario.read';


/** Quitar los permisos para que el centro no vea los Candidatos **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Deny', 22, 33, '.*BusinessProcess.Candidato.getCandidatosOferta', NULL, NULL, -10, NULL),
('Deny', 22, 33, '.*BusinessProcess.Candidato.read', NULL, NULL, -10, NULL),
('Deny',  23, 33, '.*BusinessProcess.Candidato.read', NULL, NULL, -10, NULL),
('Deny', 22, 33, 'CandidatoCRUDBusinessProcess.Candidato.getFotoCandidato', NULL, NULL, -10, NULL);
