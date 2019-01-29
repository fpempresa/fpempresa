/** Campos para aceptar la RGPD Reglamento General de Protección de Datos **/
ALTER TABLE `usuario` ADD COLUMN `aceptadoRGPD` INT(11)  DEFAULT '0';
ALTER TABLE `usuario` ADD COLUMN `enviadoCorreoRGPD` INT(11)  DEFAULT '0';
ALTER TABLE `usuario` ADD COLUMN `secureKeyRGPD` VARCHAR(255) DEFAULT NULL;

INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 22, 1, 'UsuarioCRUDBusinessProcess.Usuario.aceptarRGPD', NULL, NULL, 1, "Permitir aceptar la RGPD");