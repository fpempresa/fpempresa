INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, 'DownloadBusinessProcess.getHojaCalculoEmpresasNoCentro', NULL, null, 1, NULL);


INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, 'DownloadBusinessProcess.getHojaCalculoEmpresasCentro', null, 'arguments.centro.idCentro==identity.centro.idCentro', 1, NULL);
