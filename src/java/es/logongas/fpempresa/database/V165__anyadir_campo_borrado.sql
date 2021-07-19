ALTER TABLE `candidato`          ADD COLUMN `borrado` INT NOT NULL DEFAULT '0' AFTER `rechazado`;
ALTER TABLE `formacionacademica` ADD COLUMN `borrado` INT NOT NULL DEFAULT '0' AFTER `formacionDual`;
ALTER TABLE `usuario`            ADD COLUMN `borrado` INT NOT NULL DEFAULT '0' AFTER `secretToken`;