/**
 * FPempresa Copyright (C) 2020 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */



package es.logongas.fpempresa.businessprocess.estadisticas;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.ComunidadAutonoma;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.Estadistica;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.modelo.estadisticas.EstadisticasPrincipal;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaOfertasEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.GroupByEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.NombreEstadistica;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.Date;
import java.util.List;

/**
 *
 * @author logongas
 */
public interface EstadisticasBusinessProcess  extends BusinessProcess  {

    Estadistica getEstadisticasAdministrador(GetEstadisticasAdministradorArguments getEstadisticasAdministradorArguments) throws BusinessException;
    Estadisticas getEstadisticasCentro(GetEstadisticasCentroArguments getEstadisticasCentroArguments) throws BusinessException;
    Estadisticas getEstadisticasEmpresa(GetEstadisticasEmpresaArguments getEstadisticasEmpresaArguments) throws BusinessException;
    Estadisticas getEstadisticasPublicas(GetEstadisticasPublicasArguments getEstadisticasPublicasArguments) throws BusinessException;
    List<FamiliaOfertasEstadistica> getEstadisticasFamiliaOfertasPublicas(GetEstadisticasFamiliaOfertasPublicasArguments getEstadisticasFamiliaOfertasPublicasArguments) throws BusinessException;
    EstadisticasPrincipal getEstadisticasPrincipal(GetEstadisticasPrincipalArguments getEstadisticasPrincipalArguments);
    
    public class GetEstadisticasAdministradorArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public NombreEstadistica nombreEstadistica;
        public GroupByEstadistica groupByEstadistica;
        public Date filterDesde;
        public Date filterHasta;
        public ComunidadAutonoma filterComunidadAutonoma;
        public Provincia filterProvincia;
        public Familia filterFamilia;
        public Ciclo filterCiclo;

        
        public GetEstadisticasAdministradorArguments() {
        }

        public GetEstadisticasAdministradorArguments(Principal principal, DataSession dataSession,NombreEstadistica nombreEstadistica,GroupByEstadistica groupByEstadistica, Date filterDesde,Date filterHasta,ComunidadAutonoma filterComunidadAutonoma,Provincia filterProvincia,Familia filterFamilia,Ciclo filterCiclo) {
            super(principal, dataSession);
            this.nombreEstadistica=nombreEstadistica;
            this.groupByEstadistica=groupByEstadistica;
            this.filterDesde = filterDesde;
            this.filterHasta = filterHasta;
            this.filterComunidadAutonoma = filterComunidadAutonoma;            
            this.filterProvincia = filterProvincia;            
            this.filterFamilia = filterFamilia;            
            this.filterCiclo = filterCiclo;                      
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

    public class GetEstadisticasPrincipalArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public GetEstadisticasPrincipalArguments() {
        }

        public GetEstadisticasPrincipalArguments(Principal principal, DataSession dataSession) {
            super(principal, dataSession);
        }

    }   
       
}
