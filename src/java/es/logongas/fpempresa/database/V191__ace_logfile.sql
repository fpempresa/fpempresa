
INSERT INTO `sec_ace`
(`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES
('Allow', 22, 31, 'LogFileBusinessProcess.search', NULL, NULL, 1, NULL),
('Allow', 22, 31, 'LogFileBusinessProcess.read', NULL, NULL, 1, NULL);
