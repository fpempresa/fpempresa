/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.estadisticas;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaOfertasEstadistica;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.List;

/**
 *
 * @author logongas
 */
public interface EstadisticasBusinessProcess  extends BusinessProcess  {

    Estadisticas getEstadisticasAdministrador(GetEstadisticasAdministradorArguments getEstadisticasAdministradorArguments) throws BusinessException;
    Estadisticas getEstadisticasCentro(GetEstadisticasCentroArguments getEstadisticasCentroArguments) throws BusinessException;
    Estadisticas getEstadisticasEmpresa(GetEstadisticasEmpresaArguments getEstadisticasEmpresaArguments) throws BusinessException;
    Estadisticas getEstadisticasPublicas(GetEstadisticasPublicasArguments getEstadisticasPublicasArguments) throws BusinessException;
    List<FamiliaOfertasEstadistica> getEstadisticasFamiliaOfertasPublicas(GetEstadisticasFamiliaOfertasPublicasArguments getEstadisticasFamiliaOfertasPublicasArguments) throws BusinessException;

    public class GetEstadisticasAdministradorArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public GetEstadisticasAdministradorArguments() {
        }

        public GetEstadisticasAdministradorArguments(Principal principal, DataSession dataSession) {
            super(principal, dataSession);
        }

    }

    public class GetEstadisticasCentroArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Centro centro;
        public Integer anyoInicio;
        public Integer anyoFin;

        public GetEstadisticasCentroArguments() {
        }

        public GetEstadisticasCentroArguments(Principal principal, DataSession dataSession, Centro centro) {
            super(principal, dataSession);
            this.centro = centro;
            this.anyoInicio = null;
            this.anyoFin = null;            
        }
        public GetEstadisticasCentroArguments(Principal principal, DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin) {
            super(principal, dataSession);
            this.centro = centro;
            this.anyoInicio = anyoInicio;
            this.anyoFin = anyoFin;
        }        

    }

    public class GetEstadisticasEmpresaArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Empresa empresa;

        public GetEstadisticasEmpresaArguments() {
            this.empresa = null;
        }

        public GetEstadisticasEmpresaArguments(Principal principal, DataSession dataSession, Empresa empresa) {
            super(principal, dataSession);
            this.empresa = empresa;
        }

    }

    public class GetEstadisticasTituladoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Titulado titulado;

        public GetEstadisticasTituladoArguments() {
            this.titulado = null;
        }

        public GetEstadisticasTituladoArguments(Principal principal, DataSession dataSession, Titulado titulado) {
            super(principal, dataSession);
            this.titulado = titulado;
        }

    }

    public class GetEstadisticasPublicasArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public GetEstadisticasPublicasArguments() {
        }

        public GetEstadisticasPublicasArguments(Principal principal, DataSession dataSession) {
            super(principal, dataSession);
        }

    }
    
    public class GetEstadisticasFamiliaOfertasPublicasArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public GetEstadisticasFamiliaOfertasPublicasArguments() {
        }

        public GetEstadisticasFamiliaOfertasPublicasArguments(Principal principal, DataSession dataSession) {
            super(principal, dataSession);
        }

    }    

}
