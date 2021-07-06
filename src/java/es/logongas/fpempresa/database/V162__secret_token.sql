ALTER TABLE `usuario` ADD COLUMN `secretToken` VARCHAR(50) NULL DEFAULT NULL AFTER `aceptarEnvioCorreos`;


DELIMITER $$
CREATE TRIGGER `trigger_usuario_secret_token` BEFORE INSERT ON `usuario` FOR EACH ROW BEGIN
SET NEW.secretToken= (md5(concat(UUID(),CONVERT(RAND(),CHAR))));
END$$
DELIMITER;

UPDATE `usuario` SET `secretToken` = (SELECT (md5(concat(UUID(),CONVERT(RAND(),CHAR)))));