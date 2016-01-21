/**
 *   FPempresa
 *   Copyright (C) 2015  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.service.centro.impl;

import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.dao.GenericDAO;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author logongas
 */
public class CertificadoTituloCRUDServiceImpl  extends CRUDServiceImpl<CertificadoTitulo, Integer> implements  CRUDService<CertificadoTitulo, Integer>  {

    @Override
    public CertificadoTitulo insert(DataSession dataSession,CertificadoTitulo certificadoTitulo) throws BusinessException {
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        transactionManager.begin(dataSession);

        List<FormacionAcademica> formacionesAcademicas=getFormacionAcademicaFromCertificadoTitulo(dataSession,certificadoTitulo);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicas) {
            formacionAcademica.setCertificadoTitulo(true);
            formacionAcademicaDAO.update(dataSession,formacionAcademica);
        }
    
        CertificadoTitulo certificadoTitulo1= daoFactory.getDAO(CertificadoTitulo.class).insert(dataSession,certificadoTitulo);

        transactionManager.commit(dataSession);
        
        return certificadoTitulo;
    }

    @Override
    public CertificadoTitulo update(DataSession dataSession,CertificadoTitulo certificadoTitulo) throws BusinessException {
        
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        CertificadoTitulo certificadoTituloOriginal=getDAO().readOriginal(dataSession,certificadoTitulo.getIdCertificadoTitulo());
        
        transactionManager.begin(dataSession);

        List<FormacionAcademica> formacionesAcademicasOriginales=getFormacionAcademicaFromCertificadoTitulo(dataSession,certificadoTituloOriginal);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicasOriginales) {
            formacionAcademica.setCertificadoTitulo(false);
            formacionAcademicaDAO.update(dataSession,formacionAcademica);
        }        
        
        List<FormacionAcademica> formacionesAcademicas=getFormacionAcademicaFromCertificadoTitulo(dataSession,certificadoTitulo);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicas) {
            formacionAcademica.setCertificadoTitulo(true);
            formacionAcademicaDAO.update(dataSession,formacionAcademica);
        }
    
        CertificadoTitulo update=daoFactory.getDAO(CertificadoTitulo.class).update(dataSession,certificadoTitulo);

        transactionManager.commit(dataSession);        
        
        
        return update;
    }

    @Override
    public boolean delete(DataSession dataSession,CertificadoTitulo certificadoTitulo) throws BusinessException {
        
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        transactionManager.begin(dataSession);

        List<FormacionAcademica> formacionesAcademicasOriginales=getFormacionAcademicaFromCertificadoTitulo(dataSession,certificadoTitulo);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicasOriginales) {
            formacionAcademica.setCertificadoTitulo(false);
            formacionAcademicaDAO.update(dataSession,formacionAcademica);
        }        
    
        boolean delete=daoFactory.getDAO(CertificadoTitulo.class).delete(dataSession,certificadoTitulo);

        transactionManager.commit(dataSession);        
        
        
        return delete; 
    }
    
    
    private List<FormacionAcademica> getFormacionAcademicaFromCertificadoTitulo(DataSession dataSession,CertificadoTitulo certificadoTitulo) throws BusinessException {
        List<FormacionAcademica> formacionesAcademicas=new ArrayList<FormacionAcademica>();
        Calendar calendar = new GregorianCalendar();
            
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        Filters filters=new Filters();
        filters.add(new Filter("titulado.tipoDocumento",TipoDocumento.NIF_NIE));
        filters.add(new Filter("titulado.numeroDocumento",certificadoTitulo.getNifnies()));
        filters.add(new Filter("centro.idCentro",certificadoTitulo.getCentro().getIdCentro()));
        filters.add(new Filter("ciclo.idCiclo",certificadoTitulo.getCiclo().getIdCiclo()));
        
        
        List<FormacionAcademica> formacionesAcademicasRaw=formacionAcademicaDAO.search(dataSession,filters,null,null);
        for(FormacionAcademica formacionAcademica:formacionesAcademicasRaw) {
            calendar.setTime(formacionAcademica.getFecha());
            int anyo=calendar.get(Calendar.YEAR);
            
            if (anyo==certificadoTitulo.getAnyo()) {
                formacionesAcademicas.add(formacionAcademica);
            }
            
        }
        
        return formacionesAcademicas;
    }
    
}
