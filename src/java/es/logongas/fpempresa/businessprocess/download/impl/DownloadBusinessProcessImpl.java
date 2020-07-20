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
package es.logongas.fpempresa.businessprocess.download.impl;

import es.logongas.fpempresa.businessprocess.download.DownloadBusinessProcess;
import es.logongas.fpempresa.service.download.DownloadService;
import es.logongas.ix3.core.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class DownloadBusinessProcessImpl implements DownloadBusinessProcess {
    
    private Class entityType;
    
    @Autowired
    DownloadService downloadService;


    @Override
    public byte[] getHojaCalculoOfertasNoCentro(GetHojaCalculoOfertasNoCentroArguments getHojaCalculoOfertasNoCentroArguments) throws BusinessException {
        return downloadService.getHojaCalculoOfertasNoCentro(getHojaCalculoOfertasNoCentroArguments.dataSession,getHojaCalculoOfertasNoCentroArguments.fechaInicio,getHojaCalculoOfertasNoCentroArguments.fechaFin);
    }
    
    @Override
    public byte[] getHojaCalculoOfertasCentro(GetHojaCalculoOfertasCentroArguments getHojaCalculoOfertasCentroArguments) throws BusinessException {
        return downloadService.getHojaCalculoOfertasCentro(getHojaCalculoOfertasCentroArguments.dataSession,getHojaCalculoOfertasCentroArguments.centro, getHojaCalculoOfertasCentroArguments.fechaInicio,getHojaCalculoOfertasCentroArguments.fechaFin);
    }    
    

    @Override
    public byte[] getHojaCalculoEmpresasNoCentro(GetHojaCalculoEmpresasNoCentroArguments getHojaCalculoEmpresasNoCentroArguments) throws BusinessException {
        return downloadService.getHojaCalculoEmpresasNoCentro(getHojaCalculoEmpresasNoCentroArguments.dataSession,getHojaCalculoEmpresasNoCentroArguments.fechaInicio,getHojaCalculoEmpresasNoCentroArguments.fechaFin);

    }

    @Override
    public byte[] getHojaCalculoEmpresasCentro(GetHojaCalculoEmpresasCentroArguments getHojaCalculoEmpresasCentroArguments) throws BusinessException {
        return downloadService.getHojaCalculoEmpresasCentro(getHojaCalculoEmpresasCentroArguments.dataSession,getHojaCalculoEmpresasCentroArguments.centro, getHojaCalculoEmpresasCentroArguments.fechaInicio,getHojaCalculoEmpresasCentroArguments.fechaFin);    
    }
    
    @Override
    public byte[] getHojaCalculoUsuariosTituladosCentro(GetHojaCalculoUsuariosTituladosCentroArguments hojaCalculoUsuariosTituladosCentroArguments) throws BusinessException {
        return downloadService.getHojaCalculoUsuariosTituladosCentro(hojaCalculoUsuariosTituladosCentroArguments.dataSession,hojaCalculoUsuariosTituladosCentroArguments.centro,hojaCalculoUsuariosTituladosCentroArguments.familia, hojaCalculoUsuariosTituladosCentroArguments.ciclo, hojaCalculoUsuariosTituladosCentroArguments.fechaInicio,hojaCalculoUsuariosTituladosCentroArguments.fechaFin);      
    }
    
    @Override
    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    @Override
    public Class getEntityType() {
        return this.entityType;
    }    



    
}
