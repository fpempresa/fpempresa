ALTER TABLE `usuario`
	ADD COLUMN `fechaUltimoEnvioCorreoValidacionEmail` DATETIME NULL DEFAULT NULL AFTER `validadoEmail`;

ALTER TABLE `usuario`
	ADD COLUMN `numEnviosCorreoValidacionEmail` INT NOT NULL DEFAULT '0' AFTER `fechaUltimoEnvioCorreoValidacionEmail`;
