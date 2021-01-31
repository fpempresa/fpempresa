ALTER TABLE `usuario`
	ADD COLUMN `lockedUntil` DATETIME NULL DEFAULT NULL AFTER `fechaClaveResetearContrasenya`,
	ADD COLUMN `numFailedLogins` INT NOT NULL DEFAULT '0' AFTER `lockedUntil`;
