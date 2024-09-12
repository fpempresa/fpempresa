DROP TRIGGER trigger_usuario_secret_token;
DROP TRIGGER trigger_oferta_secret_token;


UPDATE usuario
SET secretToken= (md5(concat(UUID(),CONVERT(RAND(),CHAR))))
WHERE secretToken is null;


UPDATE oferta
SET secretToken= (md5(concat(UUID(),CONVERT(RAND(),CHAR))))
WHERE secretToken is null;
