RENAME TABLE certificadotitulo TO OLD_certificadotitulo;

INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 33, 'CertificadoBusinessProcess.getCertificadosAnyoCentro', NULL, 'arguments.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, 'CertificadoBusinessProcess.getCertificadosCicloCentro', NULL, 'arguments.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, 'CertificadoBusinessProcess.getCertificadosTituloCentro', NULL, 'arguments.centro.idCentro==identity.centro.idCentro', 1, NULL),
('Allow', 22, 33, 'CertificadoBusinessProcess.certificarTituloCentro', NULL, 'arguments.centro.idCentro==identity.centro.idCentro', 1, NULL);

    

