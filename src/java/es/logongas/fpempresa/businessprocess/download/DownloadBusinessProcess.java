package es.logongas.fpempresa.businessprocess.download;

import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.Date;

/**
 *
 * @author logongas
 */
public interface DownloadBusinessProcess {

    byte[] getHojaCalculoOfertasAdministrador(GetHojaCalculoOfertasAdministradorArguments getHojaCalculoOfertasAdministradorArguments) throws BusinessException;


    public class GetHojaCalculoOfertasAdministradorArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Date fechaInicio;
        public Date fechaFin;

        public GetHojaCalculoOfertasAdministradorArguments() {
        }

        public GetHojaCalculoOfertasAdministradorArguments(Principal principal, DataSession dataSession, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }







}
