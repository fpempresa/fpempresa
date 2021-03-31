ALTER TABLE `usuario`
	ADD COLUMN `aceptarEnvioCorreos` INT NULL DEFAULT NULL AFTER `numFailedLogins`;


UPDATE 
	usuario
SET
	aceptarEnvioCorreos=1
WHERE
	tipoUsuario!="TITULADO";
	
UPDATE 
	Usuario 
INNER JOIN  
	Titulado  ON Usuario.IdTitulado=Titulado.idTitulado
SET 
	aceptarEnvioCorreos=1
WHERE
	tipoUsuario="TITULADO" AND Titulado.notificarporemail=1;

UPDATE 
	Usuario 
INNER JOIN  
	Titulado  ON Usuario.IdTitulado=Titulado.idTitulado
SET 
	aceptarEnvioCorreos=0
WHERE
	tipoUsuario="TITULADO" AND Titulado.notificarporemail=0;
	

UPDATE 
	Usuario 
SET 
	aceptarEnvioCorreos=1
WHERE
	tipoUsuario="TITULADO" AND Usuario.IdTitulado IS NULL;


/** Esto es porque ahora no se envian correos a los usuarios sino unicamente a este campo **/
UPDATE 
	Empresa
SET
	email=(SELECT email FROM usuario WHERE usuario.idempresa=Empresa.idEmpresa AND usuario.tipoUsuario="EMPRESA" LIMIT 1)

WHERE
	Empresa.email IS null AND Empresa.idCentro IS NULL