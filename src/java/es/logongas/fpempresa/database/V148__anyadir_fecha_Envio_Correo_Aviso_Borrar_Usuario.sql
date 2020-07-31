ALTER TABLE `usuario` ADD COLUMN `fechaEnvioCorreoAvisoBorrarUsuario` DATETIME NULL DEFAULT NULL AFTER `fechaUltimoAcceso`;

CREATE INDEX index_titulado_fechaUltimoAcceso ON usuario (tipoUsuario,fechaEnvioCorreoAvisoBorrarUsuario,fechaUltimoAcceso);
