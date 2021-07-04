/*
 * FPempresa Copyright (C) 2021 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.businessprocess.centro.impl;

import es.logongas.fpempresa.businessprocess.centro.CertificadoBusinessProcess;
import es.logongas.fpempresa.modelo.centro.CertificadoAnyo;
import es.logongas.fpempresa.modelo.centro.CertificadoCiclo;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.service.centro.CertificadoService;
import es.logongas.ix3.core.BusinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class CertificadoBusinessProcessImpl implements CertificadoBusinessProcess {

    private Class entityType=null;
    
    @Autowired
    CertificadoService certificadoService;
    

    @Override
    public List<CertificadoAnyo> getCertificadosAnyoCentro(GetCertificadosAnyoCentroArguments getCertificadosAnyoCentroArguments) throws BusinessException {
        return certificadoService.getCertificadosAnyoCentro(getCertificadosAnyoCentroArguments.dataSession, getCertificadosAnyoCentroArguments.centro);
    }

    @Override
    public List<CertificadoCiclo> getCertificadosCicloCentro(GetCertificadosCicloCentroArguments getCertificadosCicloCentroArguments) throws BusinessException {
        return certificadoService.getCertificadosCicloCentro(getCertificadosCicloCentroArguments.dataSession, getCertificadosCicloCentroArguments.centro, getCertificadosCicloCentroArguments.anyo);
    }

    @Override
    public List<CertificadoTitulo> getCertificadosTituloCentro(GetCertificadosTituloCentroArguments getCertificadosTituloCentroArguments) throws BusinessException {
        return certificadoService.getCertificadosTituloCentro(getCertificadosTituloCentroArguments.dataSession, getCertificadosTituloCentroArguments.centro, getCertificadosTituloCentroArguments.anyo, getCertificadosTituloCentroArguments.ciclo);
    }

    @Override
    public void certificarTituloCentro(CertificarTituloCentroArguments certificarTituloCentroArguments) throws BusinessException {
        certificadoService.certificarTituloCentro(certificarTituloCentroArguments.dataSession, certificarTituloCentroArguments.centro, certificarTituloCentroArguments.anyo, certificarTituloCentroArguments.ciclo, certificarTituloCentroArguments.tipoDocumento,certificarTituloCentroArguments.numeroDocumento,certificarTituloCentroArguments.certificadoTitulo,certificarTituloCentroArguments.formacionAcademica);
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
