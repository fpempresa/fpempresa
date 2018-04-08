INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, 'DownloadBusinessProcess.getHojaCalculoOfertasCentro', null, 'arguments.centro.idCentro==identity.centro.idCentro', 1, NULL);
