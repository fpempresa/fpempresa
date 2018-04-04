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

    @Autowired
    DownloadService downloadService;


    @Override
    public byte[] getHojaCalculoOfertasAdministrador(GetHojaCalculoOfertasAdministradorArguments getHojaCalculoOfertasAdministradorArguments) throws BusinessException {
        return downloadService.getHojaCalculoOfertas(getHojaCalculoOfertasAdministradorArguments.dataSession,getHojaCalculoOfertasAdministradorArguments.fechaInicio,getHojaCalculoOfertasAdministradorArguments.fechaFin);
    }
    
}
