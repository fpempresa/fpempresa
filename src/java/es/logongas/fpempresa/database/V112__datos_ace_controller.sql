INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Deny', 22, 1, '.*', NULL, NULL, 10, "Por defecto todos los PRE controladoreas están prohibidos"),
('Allow', 23, 1, '.*', NULL, NULL, 10, "Por defecto todos los Post controladoreas están permitidos");


INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 22, 1, 'SessionRestController\\..*', NULL, NULL, 1, "A todos se pemite la gestion de sesiones");

INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 22, 1, 'EchoController.echoNoDatabase', NULL, NULL, 1, "Permitir el echoNoDatabase"),
('Allow', 22, 1, 'EchoController.echoDataBase', NULL, NULL, 1, "Permitir el echoDataBase");


/** Todos los usuarios registrados pueden "search" en las tablas basicas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 35, '.*\\.Municipio\\.search', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Provincia\\.search', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Familia\\.search', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Grado\\.search', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.LeyEducativa\\.search', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Ciclo\\.search', NULL, NULL, 1, NULL);

/** Todos los usuarios registrados pueden "search" en las tablas basicas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 35, '.*\\.Municipio\\.pageableSearch', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Provincia\\.pageableSearch', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Familia\\.pageableSearch', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Grado\\.pageableSearch', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.LeyEducativa\\.pageableSearch', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Ciclo\\.pageableSearch', NULL, NULL, 1, NULL);


/** Todos los usuarios registrados pueden "get" en las tablas basicas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 35, '.*\\.Municipio\\.read', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Provincia\\.read', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Familia\\.read', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Grado\\.read', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.LeyEducativa\\.read', NULL, NULL, 1, NULL),
('Allow', 22, 35, '.*\\.Ciclo\\.read', NULL, NULL, 1, NULL);

/** Crear una cuenta **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 1, '.*Controller.Usuario.create', NULL, NULL, 1, NULL),
('Allow', 22, 1, '.*Controller.Usuario.insert', NULL, NULL, 1, NULL);