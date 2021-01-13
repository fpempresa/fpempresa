/* Que el centro pueda volver a ver los candidatos */
DELETE  from sec_ace where aceType="Deny" AND idPermission=22 AND idIdentity=33 AND secureResourceRegExp=".*BusinessProcess.Candidato.getCandidatosOferta" ;
