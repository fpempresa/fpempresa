package es.logongas.fpempresa.service.download;

import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import java.util.Date;

/**
 *
 * @author logongas
 */
public interface DownloadService {
    byte [] getHojaCalculoOfertas(DataSession dataSession,Date fechaInicio,Date fechaFin) throws BusinessException;
    
}
