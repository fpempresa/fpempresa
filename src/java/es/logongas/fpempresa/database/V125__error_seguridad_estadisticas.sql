/** Error porque no había permisos para las estadisticas públicas **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 1, 'EstadisticasBusinessProcess.getEstadisticasPublicas', NULL, null, 1, NULL);



/** Esto es porque se crearon unas estadisticas de usuario que nunca debieron existir **/
DELETE FROM sec_ace WHERE secureResourceRegExp='EstadisticasBusinessProcess.getEstadisticasTitulado';


