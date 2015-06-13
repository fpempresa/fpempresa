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
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.Filter;
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
    public void insert(CertificadoTitulo certificadoTitulo) throws BusinessException {
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        transactionManager.begin();

        List<FormacionAcademica> formacionesAcademicas=getFormacionAcademicaFromCertificadoTitulo(certificadoTitulo);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicas) {
            formacionAcademica.setCertificadoTitulo(true);
            formacionAcademicaDAO.update(formacionAcademica);
        }
    
        daoFactory.getDAO(CertificadoTitulo.class).insert(certificadoTitulo);

        transactionManager.commit();
    }

    @Override
    public boolean update(CertificadoTitulo certificadoTitulo) throws BusinessException {
        
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        CertificadoTitulo certificadoTituloOriginal=getDAO().readOriginal(certificadoTitulo.getIdCertificadoTitulo());
        
        transactionManager.begin();

        List<FormacionAcademica> formacionesAcademicasOriginales=getFormacionAcademicaFromCertificadoTitulo(certificadoTituloOriginal);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicasOriginales) {
            formacionAcademica.setCertificadoTitulo(false);
            formacionAcademicaDAO.update(formacionAcademica);
        }        
        
        List<FormacionAcademica> formacionesAcademicas=getFormacionAcademicaFromCertificadoTitulo(certificadoTitulo);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicas) {
            formacionAcademica.setCertificadoTitulo(true);
            formacionAcademicaDAO.update(formacionAcademica);
        }
    
        boolean update=daoFactory.getDAO(CertificadoTitulo.class).update(certificadoTitulo);

        transactionManager.commit();        
        
        
        return update;
    }

    @Override
    public boolean delete(Integer idCertificadoTitulo) throws BusinessException {
        
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        CertificadoTitulo certificadoTituloOriginal=getDAO().readOriginal(idCertificadoTitulo);
        
        transactionManager.begin();

        List<FormacionAcademica> formacionesAcademicasOriginales=getFormacionAcademicaFromCertificadoTitulo(certificadoTituloOriginal);
     
        for(FormacionAcademica formacionAcademica:formacionesAcademicasOriginales) {
            formacionAcademica.setCertificadoTitulo(false);
            formacionAcademicaDAO.update(formacionAcademica);
        }        
    
        boolean delete=daoFactory.getDAO(CertificadoTitulo.class).delete(idCertificadoTitulo);

        transactionManager.commit();        
        
        
        return delete; 
    }
    
    
    private List<FormacionAcademica> getFormacionAcademicaFromCertificadoTitulo(CertificadoTitulo certificadoTitulo) throws BusinessException {
        List<FormacionAcademica> formacionesAcademicas=new ArrayList<FormacionAcademica>();
        Calendar calendar = new GregorianCalendar();
            
        GenericDAO<FormacionAcademica,Integer> formacionAcademicaDAO=daoFactory.getDAO(FormacionAcademica.class);
        
        List<Filter> filters=new ArrayList<Filter>();
        filters.add(new Filter("titulado.tipoDocumento",TipoDocumento.NIF_NIE));
        filters.add(new Filter("titulado.numeroDocumento",certificadoTitulo.getNifnies()));
        filters.add(new Filter("centro.idCentro",certificadoTitulo.getCentro().getIdCentro()));
        filters.add(new Filter("ciclo.idCiclo",certificadoTitulo.getCiclo().getIdCiclo()));
        
        
        List<FormacionAcademica> formacionesAcademicasRaw=formacionAcademicaDAO.search(filters);
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
