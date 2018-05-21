
package es.logongas.fpempresa.service.report;

import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.Service;
import java.util.Map;

/**
 * Servicio de Informes
 * @author logongas
 */
public interface ReportService extends Service {
    
    byte[] exportToPdf(DataSession dataSession,String reportName,Map<String, Object> parameters);
    
}
