/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.titulado.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.fpempresa.dao.titulado.TituladoDAO;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.titulado.ImportarTituladosJsonDeserializer;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import es.logongas.ix3.util.ExceptionUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.jasypt.util.password.StrongPasswordEncryptor;
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
            throw new BusinessException("Error al leer el archivo json");
        }
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(List.class, new ImportarTituladosJsonDeserializer(dataSession, daoFactory));
        mapper.registerModule(module);
        try {
            listadoUsuarios = mapper.readValue(inputStream, TypeFactory.defaultInstance().constructCollectionLikeType(List.class, Usuario.class));
        } catch (IOException ex) {
            throw new BusinessException("Error al leer el archivo json");
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

    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }

}
