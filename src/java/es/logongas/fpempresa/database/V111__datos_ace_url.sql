/**  prohibido todas las URL a todos **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Deny', 1, 1, '/api/.*', NULL, NULL, 100, 'GET Prohibido'),
('Deny', 2, 1, '/api/.*', NULL, NULL, 100, 'POST Prohibido'),
('Deny', 3, 1, '/api/.*', NULL, NULL, 100, 'PUT Prohibido'),
('Deny', 4, 1, '/api/.*', NULL, NULL, 100, 'DELETE Prohibido');


/**  Permitido el acceso a la sesion a todos **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 1, 1, '/api/session', NULL, NULL, 10, 'GET /session Permitido'),
('Allow', 2, 1, '/api/session', NULL, NULL, 10, 'POST /session Permitido'),
('Allow', 4, 1, '/api/session', NULL, NULL, 10, 'DELETE /session Permitido');

/**  Permitido el acceso al $echo a todos **/
INSERT INTO `sec_ace` (`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 1, 1, '/api/\\$echo/\\d+', NULL, NULL, 10, '$echoDB Permitido'),
('Allow', 1, 1, '/api/\\$echo', NULL, NULL, 10, '$echoNoDB Permitido');

/**  Permitido el acceso al $log a todos verlo y cambiarlo solo al administrador **/
INSERT INTO `sec_ace` (`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 1, 1, '/api/\\$log', NULL, NULL, 10, 'get log todos'),
('Allow', 3, 31,'/api/\\$log', NULL, NULL, 10, 'put log admin');


/**  permitido /site a todos **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 1, 1, '/api/site/.*', NULL, NULL, 10, 'GET Permitido'),
('Allow', 2, 1, '/api/site/.*', NULL, NULL, 10, 'POST Permitido'),
('Allow', 3, 1, '/api/site/.*', NULL, NULL, 10, 'PUT Permitido'),
('Allow', 4, 1, '/api/site/.*', NULL, NULL, 10, 'DELETE Permitido'),
('Allow', 5, 1, '/api/site/.*', NULL, NULL, 10, 'PATCH Permitido');

/**  Permitido /administrador a los administradores **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 1, 31, '/api/administrador/.*', NULL, NULL, 1, 'GET Permitido'),
('Allow', 2, 31, '/api/administrador/.*', NULL, NULL, 1, 'POST Permitido'),
('Allow', 3, 31, '/api/administrador/.*', NULL, NULL, 1, 'PUT Permitido'),
('Allow', 4, 31, '/api/administrador/.*', NULL, NULL, 1, 'DELETE Permitido'),
('Allow', 5, 31, '/api/administrador/.*', NULL, NULL, 1, 'PATCH Permitido');

/**  Permitido /titulado a los titulados **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 1, 32, '/api/titulado/.*', NULL, NULL, 1, 'GET Permitido'),
('Allow', 2, 32, '/api/titulado/.*', NULL, NULL, 1, 'POST Permitido'),
('Allow', 3, 32, '/api/titulado/.*', NULL, NULL, 1, 'PUT Permitido'),
('Allow', 4, 32, '/api/titulado/.*', NULL, NULL, 1, 'DELETE Permitido'),
('Allow', 5, 32, '/api/titulado/.*', NULL, NULL, 1, 'PATCH Permitido');

/**  Permitido /centro a los centros **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 1, 33, '/api/centro/.*', NULL, NULL, 1, 'GET Permitido'),
('Allow', 2, 33, '/api/centro/.*', NULL, NULL, 1, 'POST Permitido'),
('Allow', 3, 33, '/api/centro/.*', NULL, NULL, 1, 'PUT Permitido'),
('Allow', 4, 33, '/api/centro/.*', NULL, NULL, 1, 'DELETE Permitido'),
('Allow', 5, 33, '/api/centro/.*', NULL, NULL, 1, 'PATCH Permitido');

/**  Permitido /empresa a las empresas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 1, 34, '/api/empresa/.*', NULL, NULL, 1, 'GET Permitido'),
('Allow', 2, 34, '/api/empresa/.*', NULL, NULL, 1, 'POST Permitido'),
('Allow', 3, 34, '/api/empresa/.*', NULL, NULL, 1, 'PUT Permitido'),
('Allow', 4, 34, '/api/empresa/.*', NULL, NULL, 1, 'DELETE Permitido'),
('Allow', 5, 34, '/api/empresa/.*', NULL, NULL, 1, 'PATCH Permitido');
