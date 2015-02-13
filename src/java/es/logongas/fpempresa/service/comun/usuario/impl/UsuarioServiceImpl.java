/**
 * FPempresa Copyright (C) 2015 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.service.comun.usuario.impl;

import es.logongas.fpempresa.dao.comun.usuario.UsuarioDAO;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuarioCentro;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.service.impl.GenericServiceImpl;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 *
 * @author logongas
 */
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Integer> implements UsuarioService {

    private UsuarioDAO getUsuarioDAO() {
        return (UsuarioDAO) getDAO();
    }

    @Override
    public void updatePassword(Usuario usuario, String plainPassword) {
        getUsuarioDAO().updateEncryptedPassword(usuario, getEncryptedPasswordFromPlainPassword(plainPassword));
    }
    
    @Override
    public boolean checkPassword(Usuario usuario, String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = getUsuarioDAO().getEncryptedPassword(usuario);

        boolean checkOK = passwordEncryptor.checkPassword(plainPassword, encryptedPassword);

        return checkOK;
    }

    @Override
    public void insert(Usuario usuario) throws BusinessException {

        Usuario usuarioActual = (Usuario) this.principal;

        if (usuario.getTipoUsuario() == TipoUsuario.CENTRO) {
            if ((usuarioActual == null) || (usuarioActual.getTipoUsuario() != TipoUsuario.ADMINISTRADOR)) {
                //Solo los administradores pueden elegir el estado del usuario para el resto solo se permite que ete pediente de aceptación
                usuario.setEstadoUsuarioCentro(EstadoUsuarioCentro.PENDIENTE_ACEPTACION_CENTRO);
            }
        }
        usuario.setPassword(getEncryptedPasswordFromPlainPassword(usuario.getPassword()));
        getUsuarioDAO().update(usuario);
        usuario.setPassword(null);
    }

    @Override
    public boolean update(Usuario usuario) throws BusinessException {
        usuario.setPassword(getUsuarioDAO().getEncryptedPassword(usuario));
        boolean result = getUsuarioDAO().update(usuario);
        usuario.setPassword(null);

        return result;
    }


    
    
    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }
}
