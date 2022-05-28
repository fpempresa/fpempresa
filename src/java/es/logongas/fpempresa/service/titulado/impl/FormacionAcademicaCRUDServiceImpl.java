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
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.fpempresa.service.titulado.FormacionAcademicaCRUDService;
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.List;

/**
 *
 * @author logongas
 */
public class FormacionAcademicaCRUDServiceImpl extends CRUDServiceImpl<FormacionAcademica, Integer> implements FormacionAcademicaCRUDService {

    private FormacionAcademicaDAO getFormacionAcademicaDAO() {
        return (FormacionAcademicaDAO) getDAO();
    }    
    
    @Override
    public FormacionAcademica insert(DataSession dataSession, FormacionAcademica formacionAcademica) throws BusinessException {

        if (formacionAcademica.getTipoFormacionAcademica() == TipoFormacionAcademica.CICLO_FORMATIVO) {
            Filters filters=new Filters();
            filters.add(new Filter("titulado.idTitulado", formacionAcademica.getTitulado().getIdTitulado()));
            filters.add(new Filter("tipoFormacionAcademica", TipoFormacionAcademica.CICLO_FORMATIVO));
            
            filters.add(new Filter("ciclo.idCiclo", formacionAcademica.getCiclo().getIdCiclo()));
            List<FormacionAcademica> formacionesAcademicas=getFormacionAcademicaDAO().search(dataSession, filters, null, null);
            if (formacionesAcademicas.size()>0) {
                throw new BusinessException("Ya posees el título de '" + formacionAcademica.getCiclo().getDescripcion() + "'");
            }
        }
        
        formacionAcademica.setCertificadoTitulo(false);
        return super.insert(dataSession, formacionAcademica);
    }

    @Override
    public FormacionAcademica update(DataSession dataSession, FormacionAcademica formacionAcademica) throws BusinessException {
        FormacionAcademica formacionAcademicaOriginal=this.readOriginal(dataSession, formacionAcademica.getIdFormacionAcademica());
        
        if (formacionAcademica.getTipoFormacionAcademica() == TipoFormacionAcademica.CICLO_FORMATIVO) {
            Filters filters=new Filters();
            filters.add(new Filter("titulado.idTitulado", formacionAcademica.getTitulado().getIdTitulado()));
            filters.add(new Filter("tipoFormacionAcademica", TipoFormacionAcademica.CICLO_FORMATIVO));

            filters.add(new Filter("ciclo.idCiclo", formacionAcademica.getCiclo().getIdCiclo()));
            filters.add(new Filter("idFormacionAcademica", formacionAcademicaOriginal.getIdFormacionAcademica(),FilterOperator.ne));
            List<FormacionAcademica> formacionesAcademicas=getFormacionAcademicaDAO().search(dataSession, filters, null, null);
            if (formacionesAcademicas.size()>0) {
                throw new BusinessException("Ya posees el título de '" + formacionAcademica.getCiclo().getDescripcion() + "'");
            }
        }
        
        
        
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

    @Override
    public void softDelete(DataSession dataSession, FormacionAcademica formacionAcademica) throws BusinessException {
        getFormacionAcademicaDAO().softDelete(dataSession, formacionAcademica.getIdFormacionAcademica());
    }

}
