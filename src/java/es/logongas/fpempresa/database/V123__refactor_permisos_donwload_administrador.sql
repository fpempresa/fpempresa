/** Esto es porque ha cambiado el nombre de un BusinessProcess de 'getExcelOfertasAdministrador' a 'getHojaCalculoOfertasNoCentro' **/
DELETE FROM sec_ace WHERE secureResourceRegExp='DownloadBusinessProcess.getExcelOfertasAdministrador';

/** Permisos Download del administrador **/
INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 31, 'DownloadBusinessProcess.getHojaCalculoOfertasNoCentro', NULL, null, 1, NULL);



