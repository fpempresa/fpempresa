/**
 * FPempresa Copyright (C) 2015 Lorenzo González
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
package es.logongas.fpempresa.service.titulado.impl;

import es.logongas.fpempresa.dao.titulado.FormacionAcademicaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.fpempresa.service.titulado.FormacionAcademicaCRUDService;
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;

/**
 *
 * @author logongas
 */
public class FormacionAcademicaCRUDServiceImpl extends CRUDServiceImpl<FormacionAcademica, Integer> implements FormacionAcademicaCRUDService {

    @Override
    public FormacionAcademica insert(DataSession dataSession, FormacionAcademica formacionAcademica) throws BusinessException {

        formacionAcademica.setCertificadoTitulo(false);
        return super.insert(dataSession, formacionAcademica);
    }

    @Override
    public FormacionAcademica update(DataSession dataSession, FormacionAcademica formacionAcademica) throws BusinessException {
        FormacionAcademica formacionAcademicaOriginal=this.readOriginal(dataSession, formacionAcademica.getIdFormacionAcademica());
        
        if (isChangeInforForCertificadoTitulo(formacionAcademica,formacionAcademicaOriginal)) {
            formacionAcademica.setCertificadoTitulo(false);
        }
        
        
        return super.update(dataSession, formacionAcademica);
    }

    private boolean isChangeInforForCertificadoTitulo(FormacionAcademica formacionAcademica, FormacionAcademica formacionAcademicaOriginal) {
        if ((formacionAcademica.getCentro()==null) || (formacionAcademicaOriginal.getCentro()==null)) {
            return true;
        }
        
        if (formacionAcademica.getCentro().getIdCentro()!=formacionAcademicaOriginal.getCentro().getIdCentro()) {
            return true;
        }
        
        
        if (formacionAcademica.getTipoFormacionAcademica()!=formacionAcademicaOriginal.getTipoFormacionAcademica()) {
            return true;
        } 
        
        if ((formacionAcademica.getCiclo()==null) || (formacionAcademicaOriginal.getCiclo()==null)) {
            return true;
        }
        
        if (formacionAcademica.getCiclo().getIdCiclo()!=formacionAcademicaOriginal.getCiclo().getIdCiclo()) {
            return true;
        }        
        
        
        int anyo=DateUtil.get(formacionAcademica.getFecha(),DateUtil.Interval.YEAR);
        int anyoOriginal=DateUtil.get(formacionAcademicaOriginal.getFecha(),DateUtil.Interval.YEAR);
        
        if (anyo!=anyoOriginal) {
            return true;
        }          
        
        return false;
        
    }

}
