/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.estadisticas.impl;

import es.logongas.fpempresa.businessprocess.estadisticas.EstadisticasBusinessProcess;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.service.estadisticas.EstadisticasService;
import es.logongas.ix3.core.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class EstadisticasBusinessProcessImpl implements EstadisticasBusinessProcess {

    @Autowired
    EstadisticasService estadisticasService;

    @Override
    public Estadisticas getEstadisticasAdministrador(GetEstadisticasAdministradorArguments getEstadisticasAdministradorArguments) throws BusinessException {
        return estadisticasService.getEstadisticasAdministrador(getEstadisticasAdministradorArguments.dataSession);
    }

    @Override
    public Estadisticas getEstadisticasCentro(GetEstadisticasCentroArguments getEstadisticasCentroArguments) throws BusinessException {
        return estadisticasService.getEstadisticasCentro(getEstadisticasCentroArguments.dataSession, getEstadisticasCentroArguments.centro);
    }

    @Override
    public Estadisticas getEstadisticasEmpresa(GetEstadisticasEmpresaArguments getEstadisticasEmpresaArguments) throws BusinessException {
        return estadisticasService.getEstadisticasEmpresa(getEstadisticasEmpresaArguments.dataSession, getEstadisticasEmpresaArguments.empresa);
    }

}
