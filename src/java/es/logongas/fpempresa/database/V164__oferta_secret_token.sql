ALTER TABLE `oferta` ADD COLUMN `secretToken` VARCHAR(50) NULL DEFAULT NULL AFTER `textoLibre`;


DELIMITER $$
CREATE TRIGGER `trigger_oferta_secret_token` BEFORE INSERT ON `oferta` FOR EACH ROW BEGIN
SET NEW.secretToken= (md5(concat(UUID(),CONVERT(RAND(),CHAR))));
END$$
DELIMITER;

UPDATE `oferta` SET `secretToken` = (SELECT (md5(concat(UUID(),CONVERT(RAND(),CHAR)))));

INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 1, 'OfertaCRUDBusinessProcess.Oferta.cerrarOferta', NULL, null, 1, NULL);
