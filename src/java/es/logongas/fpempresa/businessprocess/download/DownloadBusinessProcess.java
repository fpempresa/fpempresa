package es.logongas.fpempresa.businessprocess.download;

import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.Date;

/**
 *
 * @author logongas
 */
public interface DownloadBusinessProcess extends BusinessProcess  {
    
    byte[] getHojaCalculoOfertasNoCentro(GetHojaCalculoOfertasNoCentroArguments getHojaCalculoOfertasAdministradorArguments) throws BusinessException;


    public class GetHojaCalculoOfertasNoCentroArguments extends BusinessProcess.BusinessProcessArguments {

        public Date fechaInicio;
        public Date fechaFin;

        public GetHojaCalculoOfertasNoCentroArguments() {
        }

        public GetHojaCalculoOfertasNoCentroArguments(Principal principal, DataSession dataSession, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }









}
