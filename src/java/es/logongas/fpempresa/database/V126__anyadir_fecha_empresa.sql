/** Anyadir la columna fecha a la tabla Empresa **/
ALTER TABLE `empresa` ADD COLUMN `fecha` date DEFAULT NULL;

/** Ahora vamos a calcular  la fecha de creacion de cada  empresa **/

/** Usamos la fecha del menor usuario de esa empresa **/
UPDATE empresa set fecha=
	(
		SELECT 
			MIN(fecha)
		FROM 
			usuario
		WHERE 
			tipoUsuario="EMPRESA" AND 
			usuario.idEmpresa=empresa.idEmpresa 	
	)
WHERE
	empresa.idCentro is null AND
	(fecha is null);	
	

/** Para empresas de centros usamos la de la primera oferta de la empresa **/
UPDATE empresa set fecha=
	(
		SELECT 
			MIN(fecha)
		FROM 
			oferta
		WHERE  
			oferta.idEmpresa=empresa.idEmpresa 	
	)
WHERE
	(NOT (empresa.idCentro is null)) AND
	(fecha is null);	
	
	
/** Para el resto de empresas de centro que no tiene ofertas usamos la de creacion del usuario del centro **/
UPDATE empresa set fecha=
	(
		SELECT 
			MIN(fecha)
		FROM 
			usuario
		WHERE 
			tipoUsuario="CENTRO" AND 
			usuario.idCentro=empresa.idCentro 	
	)
WHERE
	(NOT (empresa.idCentro is null)) AND
	(fecha is null);
	
/** Para las empresas que quedan , usamos una fecha arbitraria **/	
UPDATE 
	empresa 
SET 
	fecha='2016-09-01'
WHERE
	empresa.fecha is null;	
	