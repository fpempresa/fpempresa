/** Permisos estadisticas de Titulado **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 32, 'EstadisticasBusinessProcess.getEstadisticasTitulado', NULL, 'arguments
.titulado.idTitulado==identity.titulado.idTitulado', 1, NULL);
