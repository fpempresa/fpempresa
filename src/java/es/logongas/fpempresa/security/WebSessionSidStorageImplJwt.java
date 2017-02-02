/*
 * Copyright 2015 logongas.
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

import es.logongas.fpempresa.dao.comun.usuario.UsuarioDAO;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.web.security.impl.WebSessionSidStorageImplAbstractJws;
import java.io.Serializable;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class WebSessionSidStorageImplJwt extends WebSessionSidStorageImplAbstractJws {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    DataSessionFactory dataSessionFactory;

    @Override
    protected byte[] getSecretKey(Serializable sid) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            UsuarioDAO usuarioDAO = (UsuarioDAO) daoFactory.getDAO(Usuario.class);
            Usuario usuario = usuarioDAO.readOriginal(dataSession, (Integer) sid);

            if (usuario == null) {
                return null;
            }

            String encryptedPassword = usuarioDAO.getEncryptedPassword(dataSession, usuario);

            byte[] secretKey;
            if (encryptedPassword != null) {
                secretKey = encryptedPassword.getBytes(Charset.forName("utf-8"));
            } else {
                secretKey = new byte[0];
            }

            return secretKey;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

}
