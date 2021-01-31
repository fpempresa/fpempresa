/**
 *   FPempresa
 *   Copyright (C) 2014  Lorenzo González
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
package es.logongas.fpempresa.dao.comun.usuario;

import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.GenericDAO;
import java.util.Date;

/**
 *
 * @author Lorenzo
 */
public interface UsuarioDAO extends GenericDAO<Usuario, Integer> {

    void updateEncryptedPassword(DataSession dataSession, Usuario usuario, String encriptedPassword);
    
    void updateSuccessfulLogin(DataSession dataSession, Usuario usuario);
    void updateFailedLogin(DataSession dataSession, Usuario usuario, Date lockedUntil, int numFailedLogins);
    void updateFechaEnvioCorreoAvisoBorrarUsuario(DataSession dataSession, Usuario usuario);

    String getEncryptedPassword(DataSession dataSession, Usuario usuario);

    Usuario getUsuarioPorEmail(DataSession dataSession, String email);
}
