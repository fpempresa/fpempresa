UPDATE
	titulado INNER JOIN 
	usuario  ON titulado.idTitulado=usuario.idtitulado  
SET 
	titulado.notificarPorEmail = 0
WHERE
	usuario.borrado=0 AND 
	titulado.notificarPorEmail=1 AND
	(SELECT COUNT(*) FROM tituladoprovincianotificacion  WHERE tituladoprovincianotificacion.idTitulado=titulado.idtitulado)=0