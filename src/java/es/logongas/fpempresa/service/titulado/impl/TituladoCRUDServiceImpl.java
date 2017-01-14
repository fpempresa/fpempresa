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
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.io.IOException;
import java.io.InputStream;
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
        try {
            InputStream inputStream = multipartFile.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(List.class, new ImportarTituladosJsonDeserializer(dataSession, daoFactory));
            mapper.registerModule(module);
            listadoUsuarios = mapper.readValue(inputStream, TypeFactory.defaultInstance().constructCollectionLikeType(List.class, Usuario.class));
        } catch (IOException exception) {
            throw new BusinessException("Error al leer el archivo json", exception.toString());
        }
        if (listadoUsuarios != null) {
            for (Usuario usuario : listadoUsuarios) {
                this.serviceFactory.getService(Titulado.class).insert(dataSession, usuario.getTitulado());
                this.serviceFactory.getService(Usuario.class).insert(dataSession, usuario);
            }
        }
    }

    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }

}
