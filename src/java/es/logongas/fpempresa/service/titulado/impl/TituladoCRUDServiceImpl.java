/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
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
package es.logongas.fpempresa.service.titulado.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.fpempresa.dao.titulado.TituladoDAO;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.titulado.ImportarTituladosJsonDeserializer;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import es.logongas.ix3.util.ExceptionUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GnommoStudios
 */
public class TituladoCRUDServiceImpl extends CRUDServiceImpl<Titulado, Integer> implements TituladoCRUDService, CRUDService<Titulado, Integer> {

    @Autowired
    protected CRUDServiceFactory serviceFactory;

    @Autowired
    DAOFactory daoFactory;

    private TituladoDAO getTituladoDAO() {
        return (TituladoDAO) this.getDAO();
    }

    @Override
    public Titulado update(DataSession dataSession, Titulado titulado) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);

        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            Titulado tituladoOriginal = getTituladoDAO().readOriginal(dataSession, titulado.getIdTitulado());

            if (titulado.getTipoDocumento() != tituladoOriginal.getTipoDocumento() || (titulado.getNumeroDocumento().equalsIgnoreCase(tituladoOriginal.getNumeroDocumento()) == false)) {
                //Al cambiar el DNI se borrar todos los certificados de sus títulos
                borrarTodosCertificadosEnFormacionAcademica(dataSession, titulado);
            }

            Titulado updatedTitulado = super.update(dataSession, titulado);

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            return updatedTitulado;
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }

    }

    @Override
    public List<Titulado> getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(DataSession dataSession, Oferta oferta) {
        return getTituladoDAO().getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(dataSession, oferta);
    }

    @Override
    public void importarTitulados(DataSession dataSession, MultipartFile multipartFile) throws BusinessException {
        List<Usuario> listadoUsuarios = null;
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException ex) {
            throw new BusinessException("Error al leer el archivo json:" + ex.getLocalizedMessage());
        }
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(List.class, new ImportarTituladosJsonDeserializer(dataSession, daoFactory));
        mapper.registerModule(module);
        try {
            listadoUsuarios = mapper.readValue(inputStream, TypeFactory.defaultInstance().constructCollectionLikeType(List.class, Usuario.class));
        } catch (IOException ex) {
            throw new BusinessException("Error al leer el archivo json:" + ex.getLocalizedMessage());
        }
        if (listadoUsuarios != null) {
            List<BusinessMessage> businessMessages = new ArrayList();
            for (Usuario usuario : listadoUsuarios) {
                try {
                    this.serviceFactory.getService(Titulado.class).insert(dataSession, usuario.getTitulado());
                    this.serviceFactory.getService(Usuario.class).insert(dataSession, usuario);
                } catch (Throwable throwable) {
                    BusinessException businessException = ExceptionUtil.getBusinessExceptionFromThrowable(throwable);
                    if (businessException != null) {
                        businessMessages.addAll(ExceptionUtil.getBusinessExceptionFromThrowable(throwable).getBusinessMessages());
                    }

                }
            }
            if (!businessMessages.isEmpty()) {
                throw new BusinessException(businessMessages);
            }
        }
    }

    private void borrarTodosCertificadosEnFormacionAcademica(DataSession dataSession, Titulado titulado) throws BusinessException{
        CRUDService<FormacionAcademica,Integer> formacionAcademicaCRUDService = (CRUDService) serviceFactory.getService(FormacionAcademica.class);

        Filters filters = new Filters();
        filters.add(new Filter("titulado.idTitulado", titulado.getIdTitulado(), FilterOperator.eq));

        List<FormacionAcademica> formacionesAcademicas = formacionAcademicaCRUDService.search(dataSession, filters, null, null);
        for (FormacionAcademica formacionAcademica : formacionesAcademicas) {
            formacionAcademica.setCertificadoTitulo(false);
            formacionAcademicaCRUDService.update(dataSession, formacionAcademica);
        }

    }

}
