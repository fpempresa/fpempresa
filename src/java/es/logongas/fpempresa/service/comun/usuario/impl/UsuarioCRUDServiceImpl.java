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
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.security.SecureKeyGenerator;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 *
 * @author logongas
 */
public class UsuarioCRUDServiceImpl extends CRUDServiceImpl<Usuario, Integer> implements UsuarioCRUDService {

    private UsuarioDAO getUsuarioDAO() {
        return (UsuarioDAO) getDAO();
    }

    private Usuario getPrincipal() {
        return (Usuario) principal;
    }

    @Override
    public void updatePassword(Usuario usuario, String currentPassword, String newPassword) throws BusinessException {

        if (usuario==null) {
            throw new BusinessException(new BusinessMessage(null, "No hay usuario al que cambiar la contraseña"));
        } else if (getPrincipal()==null) {
            throw new BusinessException(new BusinessMessage(null, "Debes haber entrado para cambiar la contraseña"));
        } else if (usuario == getPrincipal()) {
            if (checkPassword(usuario, currentPassword)) {
                getUsuarioDAO().updateEncryptedPassword(usuario, getEncryptedPasswordFromPlainPassword(newPassword));
            } else {
                throw new BusinessException(new BusinessMessage(null, "La contraseña actual no es válida"));
            }
        } else if (getPrincipal().getTipoUsuario()==TipoUsuario.ADMINISTRADOR) {
            //Si eres administrador no necesitas la contraseña actual
            getUsuarioDAO().updateEncryptedPassword(usuario, getEncryptedPasswordFromPlainPassword(newPassword));
        } else  {
            throw new BusinessException(new BusinessMessage(null, "Solo un Administrador o el propio usuario puede cambiar la contraseña"));
        }
    }

    @Override
    public boolean checkPassword(Usuario usuario, String password) throws BusinessException {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = getUsuarioDAO().getEncryptedPassword(usuario);

        boolean checkOK = passwordEncryptor.checkPassword(password, encryptedPassword);

        return checkOK;
    }

    @Override
    public void insert(Usuario usuario) throws BusinessException {

        if ((usuario.getTipoUsuario() != TipoUsuario.CENTRO) && (usuario.getTipoUsuario() != TipoUsuario.EMPRESA)) {
            //Al insertar un usuario de tipo ADMINISTRADOR o TITULADO siempre se inserta como Aceptado
            usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        } else {
            if (getPrincipal().getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
                usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
            }
        }

        usuario.setPassword(getEncryptedPasswordFromPlainPassword(usuario.getPassword()));
        usuario.setValidadoEmail(false);
        usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
        getUsuarioDAO().insert(usuario);
        usuario.setPassword(null);
    }

    @Override
    public boolean update(Usuario usuario) throws BusinessException {
        Usuario usuarioOriginal = getUsuarioDAO().readOriginal(usuario.getIdIdentity());

        if (usuarioOriginal.getEstadoUsuario() != usuario.getEstadoUsuario()) {

            if (getPrincipal().getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                //Si el usuario no está aceptado no le dejamos cambiar el estado
                throw new BusinessException(new BusinessMessage(null, "No es posible modificar el estado del usuario ya que TU no estás aceptado"));
            }

            switch (getPrincipal().getTipoUsuario()) {
                case ADMINISTRADOR:
                    //No es necesario hace nada pq el administrador siempre puede modificarlo
                    break;
                case CENTRO:
                    //Solo puede si no es el mismo 
                    if (usuario.getIdIdentity() == getPrincipal().getIdIdentity()) {
                        throw new BusinessException(new BusinessMessage(null, "Tu mismo no te puedes modificar el estado"));
                    }

                    if (usuario.getTipoUsuario() != TipoUsuario.CENTRO) {
                        throw new BusinessException(new BusinessMessage(null, "No puedes modificar el estado de usuarios que no sean de tipo CENTRO"));
                    }

                    if (usuario.getCentro() != getPrincipal().getCentro()) {
                        throw new BusinessException(new BusinessMessage(null, "No puedes modificar el estado de usuarios que sean de centros distintos al tuyo"));
                    }

                    break;
                case EMPRESA:
                    //Solo puede si no es el mismo 
                    if (usuario.getIdIdentity() == getPrincipal().getIdIdentity()) {
                        throw new BusinessException(new BusinessMessage(null, "Tu mismo no te puedes modificar el estado"));
                    }

                    if (usuario.getTipoUsuario() != TipoUsuario.EMPRESA) {
                        throw new BusinessException(new BusinessMessage(null, "No puedes modificar el estado de usuarios que no sean de tipo EMPRESA"));
                    }

                    if (usuario.getEmpresa() != getPrincipal().getEmpresa()) {
                        throw new BusinessException(new BusinessMessage(null, "No puedes modificar el estado de usuarios que sean de empresas distintas a la tuya"));
                    }

                    break;
                case TITULADO:
                    throw new BusinessException(new BusinessMessage(null, "No es posible modificar el estado de un titulado , ya que siempre es ACEPTADO"));
                default:
                    throw new BusinessException(new BusinessMessage(null, "No es posible modificar el estado del usuario"));
            }

        }

        return super.update(usuario);
    }

    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }
}
