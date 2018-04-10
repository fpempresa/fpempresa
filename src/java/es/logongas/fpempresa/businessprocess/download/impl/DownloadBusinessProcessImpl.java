/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public void setEntityType(Class entityType) {
        this.entityType = entityType;
    }

    @Override
    public Class getEntityType() {
        return this.entityType;
    }    

    
}
