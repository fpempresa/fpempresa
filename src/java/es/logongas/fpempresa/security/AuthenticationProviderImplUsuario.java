/*
 * Copyright 2013 Lorenzo González.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.logongas.fpempresa.security;

import es.logongas.fpempresa.modelo.comun.Usuario;
import es.logongas.fpempresa.persistencia.services.dao.comun.UsuarioDAO;
import es.logongas.ix3.model.Identity;
import es.logongas.ix3.persistence.services.dao.BusinessException;
import es.logongas.ix3.persistence.services.dao.DAOFactory;
import es.logongas.ix3.persistence.services.dao.GenericDAO;
import es.logongas.ix3.security.impl.authentication.CredentialImplLoginPassword;
import es.logongas.ix3.security.services.authentication.AuthenticationProvider;
import es.logongas.ix3.security.services.authentication.Credential;
import es.logongas.ix3.security.services.authentication.Principal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Autenticar a un usuario mediante el usuario y contraseña de moodle
 *
 * @author Lorenzo González
 */
public class AuthenticationProviderImplUsuario implements AuthenticationProvider {

    @Autowired
    DAOFactory daoFactory;

    protected final Log log = LogFactory.getLog(getClass());
    
    
    @Override
    public Principal authenticate(Credential credential) {
        
        try {
            if ((credential instanceof CredentialImplLoginPassword) == false) {
                return null;
            }
            CredentialImplLoginPassword credentialImplLoginPassword = (CredentialImplLoginPassword) credential;
            
            if ((credentialImplLoginPassword.getLogin()==null) ||(credentialImplLoginPassword.getLogin().trim().isEmpty())) {
                return null;
            }
            
            UsuarioDAO usuarioDAO = (UsuarioDAO)daoFactory.getDAO(Usuario.class);
            Usuario usuario = usuarioDAO.readByNaturalKey(credentialImplLoginPassword.getLogin());            
            
            if (usuario!=null) {
                String plainPassword=credentialImplLoginPassword.getPassword();
                
                if (usuarioDAO.checkPassword(usuario,plainPassword)) {
                    Principal principal=usuario;
                    return principal;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (BusinessException ex) {
            log.info(ex.getBusinessMessages().toString());
            return null;
        }
    }

    @Override
    public Principal getPrincipalBySID(Serializable sid) throws BusinessException {
        Integer idIdentity = (Integer) sid;
        GenericDAO<Identity, Integer> genericDAO = daoFactory.getDAO(Usuario.class);

        return genericDAO.read(idIdentity);
    }

    protected Principal getPrincipalByLogin(String login) throws BusinessException {
        GenericDAO<Identity, Integer> genericDAO = daoFactory.getDAO(Usuario.class);
        Identity identity = genericDAO.readByNaturalKey(login);

        return identity;
    }





}
