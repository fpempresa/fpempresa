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
package es.logongas.fpempresa.service.centro.impl;

import es.logongas.fpempresa.dao.centro.CertificadoDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoAnyo;
import es.logongas.fpempresa.modelo.centro.CertificadoCiclo;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.fpempresa.service.centro.CertificadoService;
import es.logongas.fpempresa.service.titulado.FormacionAcademicaCRUDService;
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDServiceFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class CertificadoServiceImpl implements CertificadoService {

    @Autowired
    CertificadoDAO certificadoDAO;
    
    @Autowired
    CRUDServiceFactory crudServiceFactory;   
    
    @Override
    public List<CertificadoAnyo> getCertificadosAnyoCentro(DataSession dataSession, Centro centro) throws BusinessException {
        return certificadoDAO.getCertificadosAnyoCentro(dataSession, centro);
    }

    @Override
    public List<CertificadoCiclo> getCertificadosCicloCentro(DataSession dataSession, Centro centro, int anyo) throws BusinessException {
        return certificadoDAO.getCertificadosCicloCentro(dataSession, centro, anyo);
    }

    @Override
    public List<CertificadoTitulo> getCertificadosTituloCentro(DataSession dataSession, Centro centro, int anyo, Ciclo ciclo) throws BusinessException {
        return certificadoDAO.getCertificadosTituloCentro(dataSession, centro, anyo, ciclo);
    }

    @Override
    public void certificarTituloCentro(DataSession dataSession, Centro centro, int anyo, Ciclo ciclo,TipoDocumento tipoDocumento, String numeroDocumento,boolean certificadoTitulo, FormacionAcademica formacionAcademica) throws BusinessException {
        FormacionAcademicaCRUDService formacionAcademicaCRUDService=(FormacionAcademicaCRUDService)crudServiceFactory.getService(FormacionAcademica.class);
        
        if (formacionAcademica==null) {
            throw new BusinessException("No existe esa formacion Académica");
        }
        if (centro==null) {
            throw new BusinessException("No existe ese centro");
        }   
        if (ciclo==null) {
            throw new BusinessException("No existe ese ciclo");
        } 
        if (tipoDocumento==null) {
            throw new BusinessException("No existe ese tipoDocumento");
        }         
        
        if (formacionAcademica.getTipoFormacionAcademica()!=TipoFormacionAcademica.CICLO_FORMATIVO) {
            throw new BusinessException("No es válido el TipoFormacionAcademica");
        }        
        
        if (formacionAcademica.getCentro().getIdCentro()!=centro.getIdCentro()) {
            throw new BusinessException("No es válido el centro");
        }
        
        if (formacionAcademica.getCiclo().getIdCiclo()!=ciclo.getIdCiclo()) {
            throw new BusinessException("No es válido el centro");
        }        
        
        if (DateUtil.get(formacionAcademica.getFecha(),DateUtil.Interval.YEAR)!=anyo) {
            throw new BusinessException("No es válido el año");
        } 
        
        if (formacionAcademica.getTitulado().getTipoDocumento()!=tipoDocumento) {
            throw new BusinessException("No es válido el TipoDocumento");
        }    
        
        if (formacionAcademica.getTitulado().getNumeroDocumento().equalsIgnoreCase(numeroDocumento)==false) {
            throw new BusinessException("No es válido el numeroDocumento");
        }           
        
        
        formacionAcademica.setCertificadoTitulo(certificadoTitulo);
        formacionAcademicaCRUDService.update(dataSession, formacionAcademica);
        
    }
    
}
