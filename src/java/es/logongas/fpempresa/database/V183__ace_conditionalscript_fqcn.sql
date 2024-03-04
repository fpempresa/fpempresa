UPDATE 
    sec_ace 
SET 
    conditionalScript='es.logongas.fpempresa.security.ace.ConditionalScriptEvaluatorCentroAllowUsuarioGetFoto' 
WHERE conditionalScript IS NOT NULL AND aceType='Allow' AND idPermission=22 AND idIdentity=33 AND secureResourceRegExp='UsuarioCRUDBusinessProcess.Usuario.getFoto';

UPDATE 
    sec_ace 
SET 
    conditionalScript='es.logongas.fpempresa.security.ace.ConditionalScriptEvaluatorCentroDenyUsuarioRead' 
WHERE conditionalScript IS NOT NULL AND aceType='Deny' AND idPermission=23 AND idIdentity=33 AND secureResourceRegExp='.*BusinessProcess.Usuario.read';


UPDATE 
    sec_ace 
SET 
    conditionalScript='es.logongas.fpempresa.security.ace.ConditionalScriptEvaluatorTituladoAllowCandidatoInsert' 
WHERE conditionalScript IS NOT NULL AND aceType='Allow' AND idPermission=22 AND idIdentity=32 AND secureResourceRegExp='.*BusinessProcess.Candidato.insert';


UPDATE 
    sec_ace 
SET 
    conditionalScript='es.logongas.fpempresa.security.ace.ConditionalScriptEvaluatorTituladoDenyOfertaRead' 
WHERE conditionalScript IS NOT NULL AND aceType='Deny' AND idPermission=23 AND idIdentity=32 AND secureResourceRegExp='.*BusinessProcess.Oferta.read';
