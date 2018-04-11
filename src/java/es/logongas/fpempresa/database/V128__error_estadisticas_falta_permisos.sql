/** El Administrador debe poder ver las estadisticas de los centros **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, 'EstadisticasBusinessProcess.getEstadisticasCentro', NULL, NULL, 1, NULL);
