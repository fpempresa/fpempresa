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



package es.logongas.fpempresa.businessprocess.estadisticas.impl;

import es.logongas.fpempresa.businessprocess.estadisticas.EstadisticasBusinessProcess;
import es.logongas.fpempresa.modelo.comun.geo.ComunidadAutonoma;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.estadisticas.Estadistica;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.modelo.estadisticas.EstadisticasPrincipal;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaOfertasEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.GroupByEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.NombreEstadistica;
import es.logongas.fpempresa.service.estadisticas.EstadisticasService;
import es.logongas.ix3.core.BusinessException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class EstadisticasBusinessProcessImpl implements EstadisticasBusinessProcess {
    
    private Class entityType;
    
    @Autowired
    EstadisticasService estadisticasService;

    @Override
    public Estadistica getEstadisticasAdministrador(GetEstadisticasAdministradorArguments getEstadisticasAdministradorArguments) throws BusinessException {        
        NombreEstadistica nombreEstadistica=getEstadisticasAdministradorArguments.nombreEstadistica;
        GroupByEstadistica groupByEstadistica=getEstadisticasAdministradorArguments.groupByEstadistica;
        Date filterDesde=getEstadisticasAdministradorArguments.filterDesde;
        Date filterHasta=getEstadisticasAdministradorArguments.filterHasta;
        ComunidadAutonoma filterComunidadAutonoma=getEstadisticasAdministradorArguments.filterComunidadAutonoma;
        Provincia filterProvincia=getEstadisticasAdministradorArguments.filterProvincia;
        Familia filterFamilia=getEstadisticasAdministradorArguments.filterFamilia;
        Ciclo filterCiclo=getEstadisticasAdministradorArguments.filterCiclo;
        Estadistica estadistica;
        
        switch (nombreEstadistica) {
            case Ofertas:
                estadistica=estadisticasService.getEstadisticaOfertas(getEstadisticasAdministradorArguments.dataSession,groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);                
                break;
            case Candidatos:
                estadistica=estadisticasService.getEstadisticaCandidatos(getEstadisticasAdministradorArguments.dataSession,groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);                                
                break;
            case Empresas:
                estadistica=estadisticasService.getEstadisticaEmpresas(getEstadisticasAdministradorArguments.dataSession,groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);                                                
                break; 
            default:
                throw new RuntimeException("El valor de nombreEstadistica es desconocido:" + nombreEstadistica);
        }

        
        return estadistica;       
        
    }

    @Override
    public Estadisticas getEstadisticasCentro(GetEstadisticasCentroArguments getEstadisticasCentroArguments) throws BusinessException {
        return estadisticasService.getEstadisticasCentro(getEstadisticasCentroArguments.dataSession, getEstadisticasCentroArguments.centro,getEstadisticasCentroArguments.anyoInicio,getEstadisticasCentroArguments.anyoFin);
    }

    @Override
    public Estadisticas getEstadisticasEmpresa(GetEstadisticasEmpresaArguments getEstadisticasEmpresaArguments) throws BusinessException {
        return estadisticasService.getEstadisticasEmpresa(getEstadisticasEmpresaArguments.dataSession, getEstadisticasEmpresaArguments.empresa);
    }
    

    @Override
    public Estadisticas getEstadisticasPublicas(GetEstadisticasPublicasArguments getEstadisticasPublicasArguments) throws BusinessException {
        return estadisticasService.getEstadisticasPublicas(getEstadisticasPublicasArguments.dataSession);
    }
    
    @Override
    public List<FamiliaOfertasEstadistica> getEstadisticasFamiliaOfertasPublicas(GetEstadisticasFamiliaOfertasPublicasArguments getEstadisticasFamiliaOfertasPublicasArguments) throws BusinessException {
        return estadisticasService.getEstadisticasFamiliaOfertasPublicas(getEstadisticasFamiliaOfertasPublicasArguments.dataSession);
    }
    
    @Override
    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    @Override
    public Class getEntityType() {
        return this.entityType;
    }

    @Override
    public EstadisticasPrincipal getEstadisticasPrincipal(GetEstadisticasPrincipalArguments getEstadisticasPrincipalArguments) {
        return estadisticasService.getEstadisticasPrincipal(getEstadisticasPrincipalArguments.dataSession);
    }

}
