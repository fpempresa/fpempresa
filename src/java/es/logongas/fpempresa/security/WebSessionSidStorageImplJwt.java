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
