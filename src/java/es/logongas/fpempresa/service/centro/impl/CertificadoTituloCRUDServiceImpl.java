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

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
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
public class CertificadoTituloCRUDServiceImpl extends CRUDServiceImpl<CertificadoTitulo, Integer> implements CRUDService<CertificadoTitulo, Integer> {

    @Override
    public CertificadoTitulo insert(DataSession dataSession, CertificadoTitulo certificadoTitulo) throws BusinessException {
        GenericDAO<FormacionAcademica, Integer> formacionAcademicaDAO = daoFactory.getDAO(FormacionAcademica.class);
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);

        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            List<FormacionAcademica> formacionesAcademicas = getFormacionAcademicaFromCertificadoTitulo(dataSession, certificadoTitulo);

            for (FormacionAcademica formacionAcademica : formacionesAcademicas) {
                formacionAcademica.setCertificadoTitulo(true);
                formacionAcademicaDAO.update(dataSession, formacionAcademica);
            }

            CertificadoTitulo certificadoTitulo1 = daoFactory.getDAO(CertificadoTitulo.class).insert(dataSession, certificadoTitulo);

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            generarIncidencias(dataSession, certificadoTitulo);
            return certificadoTitulo;
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }

    }

    @Override
    public CertificadoTitulo update(DataSession dataSession, CertificadoTitulo certificadoTitulo) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);

        try {
            GenericDAO<FormacionAcademica, Integer> formacionAcademicaDAO = daoFactory.getDAO(FormacionAcademica.class);

            CertificadoTitulo certificadoTituloOriginal = getDAO().readOriginal(dataSession, certificadoTitulo.getIdCertificadoTitulo());

            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            List<FormacionAcademica> formacionesAcademicasOriginales = getFormacionAcademicaFromCertificadoTitulo(dataSession, certificadoTituloOriginal);

            for (FormacionAcademica formacionAcademica : formacionesAcademicasOriginales) {
                formacionAcademica.setCertificadoTitulo(false);
                formacionAcademicaDAO.update(dataSession, formacionAcademica);
            }

            List<FormacionAcademica> formacionesAcademicas = getFormacionAcademicaFromCertificadoTitulo(dataSession, certificadoTitulo);

            for (FormacionAcademica formacionAcademica : formacionesAcademicas) {
                formacionAcademica.setCertificadoTitulo(true);
                formacionAcademicaDAO.update(dataSession, formacionAcademica);
            }

            CertificadoTitulo update = daoFactory.getDAO(CertificadoTitulo.class).update(dataSession, certificadoTitulo);

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            generarIncidencias(dataSession, update);
            return update;

        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }

    }

    @Override
    public CertificadoTitulo read(DataSession dataSession, Integer primaryKey) throws BusinessException {
        CertificadoTitulo certificadoTitulo = super.read(dataSession, primaryKey);
        generarIncidencias(dataSession, certificadoTitulo);
        return certificadoTitulo;
    }

    @Override
    public CertificadoTitulo readByNaturalKey(DataSession dataSession, Object value) throws BusinessException {
        CertificadoTitulo certificadoTitulo = super.readByNaturalKey(dataSession, value);
        generarIncidencias(dataSession, certificadoTitulo);
        return certificadoTitulo;
    }

    @Override
    public CertificadoTitulo readOriginal(DataSession dataSession, Integer primaryKey) throws BusinessException {
        CertificadoTitulo certificadoTitulo = super.readOriginal(dataSession, primaryKey);
        generarIncidencias(dataSession, certificadoTitulo);
        return certificadoTitulo;
    }

    @Override
    public CertificadoTitulo readOriginalByNaturalKey(DataSession dataSession, Object value) throws BusinessException {
        CertificadoTitulo certificadoTitulo = super.readOriginalByNaturalKey(dataSession, value);
        generarIncidencias(dataSession, certificadoTitulo);
        return certificadoTitulo;
    }

    private void generarIncidencias(DataSession dataSession, CertificadoTitulo certificadoTitulo) throws BusinessException {
        StringBuilder incidencias = new StringBuilder();

        List<FormacionAcademica> formacionesAcademicas = getFormacionAcademicaFromCentroCiclo(dataSession, certificadoTitulo.getCiclo(), certificadoTitulo.getCentro());

        for (String nif : certificadoTitulo.getNifnies()) {
            List<FormacionAcademica> formacionesAcademicasNIF = filterByNIF(formacionesAcademicas, nif);

            if (formacionesAcademicasNIF.isEmpty()) {
                //Esta información por RGPD no la mostramos.
                //incidencias.append(nif + ":No existe el titulado con el ciclo en este centro\n");
            } else if (existsAnyo(formacionesAcademicasNIF, certificadoTitulo.getAnyo())==false) {
                incidencias.append(nif + " : No coincide el Año\n");
            }
        }

        certificadoTitulo.setIncidencias(incidencias.toString());
    }

    private List<FormacionAcademica> filterByNIF(List<FormacionAcademica> formacionesAcademicas, String nifTitulado) {
        List<FormacionAcademica> formacionesAcademicasFilter = new ArrayList<>();

        if ((nifTitulado == null) || (nifTitulado.trim().isEmpty())) {
            return formacionesAcademicasFilter;
        }

        for (FormacionAcademica formacionAcademica : formacionesAcademicas) {

            if ((formacionAcademica != null) && (formacionAcademica.getTitulado() != null)) {
                String nif = formacionAcademica.getTitulado().getNumeroDocumento();
                if (nifTitulado.equalsIgnoreCase(formacionAcademica.getTitulado().getNumeroDocumento())) {
                    formacionesAcademicasFilter.add(formacionAcademica);
                }
            }

        }

        return formacionesAcademicasFilter;

    }

    private boolean existsAnyo(List<FormacionAcademica> formacionesAcademicas, int anyo) {

        Calendar calendar = Calendar.getInstance();

        for (FormacionAcademica formacionAcademica : formacionesAcademicas) {

            calendar.setTime(formacionAcademica.getFecha());
            int anyoFormacionAcademica = calendar.get(Calendar.YEAR);
            if (anyoFormacionAcademica == anyo) {
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean delete(DataSession dataSession, CertificadoTitulo certificadoTitulo) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);

        try {
            GenericDAO<FormacionAcademica, Integer> formacionAcademicaDAO = daoFactory.getDAO(FormacionAcademica.class);

            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            List<FormacionAcademica> formacionesAcademicasOriginales = getFormacionAcademicaFromCertificadoTitulo(dataSession, certificadoTitulo);

            for (FormacionAcademica formacionAcademica : formacionesAcademicasOriginales) {
                formacionAcademica.setCertificadoTitulo(false);
                formacionAcademicaDAO.update(dataSession, formacionAcademica);
            }

            boolean delete = daoFactory.getDAO(CertificadoTitulo.class).delete(dataSession, certificadoTitulo);

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            return delete;
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }
    }

    private List<FormacionAcademica> getFormacionAcademicaFromCertificadoTitulo(DataSession dataSession, CertificadoTitulo certificadoTitulo) throws BusinessException {
        List<FormacionAcademica> formacionesAcademicas = new ArrayList<FormacionAcademica>();
        Calendar calendar = new GregorianCalendar();

        GenericDAO<FormacionAcademica, Integer> formacionAcademicaDAO = daoFactory.getDAO(FormacionAcademica.class);

        Filters filters = new Filters();
        filters.add(new Filter("titulado.tipoDocumento", TipoDocumento.NIF_NIE));
        filters.add(new Filter("titulado.numeroDocumento", certificadoTitulo.getNifnies()));
        filters.add(new Filter("centro.idCentro", certificadoTitulo.getCentro().getIdCentro()));
        filters.add(new Filter("ciclo.idCiclo", certificadoTitulo.getCiclo().getIdCiclo()));

        List<FormacionAcademica> formacionesAcademicasRaw = formacionAcademicaDAO.search(dataSession, filters, null, null);
        for (FormacionAcademica formacionAcademica : formacionesAcademicasRaw) {
            calendar.setTime(formacionAcademica.getFecha());
            int anyo = calendar.get(Calendar.YEAR);

            if (anyo == certificadoTitulo.getAnyo()) {
                formacionesAcademicas.add(formacionAcademica);
            }

        }

        return formacionesAcademicas;
    }

    private List<FormacionAcademica> getFormacionAcademicaFromCentroCiclo(DataSession dataSession, Ciclo ciclo, Centro centro) throws BusinessException {
        GenericDAO<FormacionAcademica, Integer> formacionAcademicaDAO = daoFactory.getDAO(FormacionAcademica.class);

        Filters filters = new Filters();
        filters.add(new Filter("titulado.tipoDocumento", TipoDocumento.NIF_NIE));
        filters.add(new Filter("centro.idCentro", centro.getIdCentro()));
        filters.add(new Filter("ciclo.idCiclo", ciclo.getIdCiclo()));

        List<FormacionAcademica> formacionesAcademicas = formacionAcademicaDAO.search(dataSession, filters, null, null);

        return formacionesAcademicas;
    }

}
