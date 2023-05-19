INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 1, 'CaptchaBusinessProcess.getImage', NULL, NULL, 1, NULL);

INSERT INTO `sec_ace` 
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES 
('Allow', 22, 1, 'CaptchaBusinessProcess.getKeyCaptcha', NULL, NULL, 1, NULL);


CREATE TABLE `captcha` (
        `idCaptcha` int(11) NOT NULL AUTO_INCREMENT,
	`KeyCaptcha` VARCHAR(255) NOT NULL,
	`fecha` TIMESTAMP NOT NULL,
	PRIMARY KEY (`idCaptcha`),
        UNIQUE INDEX `index_keyCaptcha` (`KeyCaptcha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

