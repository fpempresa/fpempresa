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
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.service.ParameterSearch;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class UsuarioCRUDServiceImpl extends CRUDServiceImpl<Usuario, Integer> implements UsuarioCRUDService {

    @Autowired
    MailService mailService;

    private UsuarioDAO getUsuarioDAO() {
        return (UsuarioDAO) getDAO();
    }

    private Usuario getPrincipal() {
        return (Usuario) principalLocator.getPrincipal();
    }

    @Override
    public void updatePassword(Usuario usuario, String currentPassword, String newPassword) throws BusinessException {

        if (usuario == null) {
            throw new BusinessException("No hay usuario al que cambiar la contraseña");
        } else if (getPrincipal() == null) {
            throw new BusinessException("Debes haber entrado para cambiar la contraseña");
        } else if (usuario.getIdIdentity() == getPrincipal().getIdIdentity()) {
            if (checkPassword(usuario, currentPassword)) {
                getUsuarioDAO().updateEncryptedPassword(usuario, getEncryptedPasswordFromPlainPassword(newPassword));
            } else {
                throw new BusinessException("La contraseña actual no es válida");
            }
        } else if (getPrincipal().getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            //Si eres administrador no necesitas la contraseña actual
            getUsuarioDAO().updateEncryptedPassword(usuario, getEncryptedPasswordFromPlainPassword(newPassword));
        } else {
            throw new BusinessException("Solo un Administrador o el propio usuario puede cambiar la contraseña");
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

        InputStream inputStream = UsuarioCRUDServiceImpl.class.getResourceAsStream("fotoDefecto.png");
        
        try {
            byte[] foto=IOUtils.toByteArray(inputStream);
            usuario.setFoto(foto);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        usuario.setPassword(getEncryptedPasswordFromPlainPassword(usuario.getPassword()));
        usuario.setValidadoEmail(false);
        usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
        sendMailValidacionEMail(usuario);
        getUsuarioDAO().insert(usuario);
        usuario.setPassword(null);
    }

    @Override
    public boolean update(Usuario usuario) throws BusinessException {
        Usuario usuarioOriginal = getUsuarioDAO().readOriginal(usuario.getIdIdentity());

        if (usuario.getIdIdentity() == getPrincipal().getIdIdentity()) {
            if (usuarioOriginal.getEstadoUsuario() != usuario.getEstadoUsuario()) {
                if (usuarioOriginal.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
                    throw new BusinessException("Tu mismo no te puedes modificar el estado");
                }
            }
        }

        
        //REGLA NEGOCIO:Si cambiamos de empresa hay que volver a poner el usuario como pendiente de aceptación
        if (getPrincipal().getTipoUsuario() != TipoUsuario.ADMINISTRADOR) {
            if (usuario.getTipoUsuario() == TipoUsuario.EMPRESA) {
                if (((usuarioOriginal.getEmpresa() == null) && (usuario.getEmpresa() != null))
                        || ((usuarioOriginal.getEmpresa() != null) && (usuario.getEmpresa() == null))
                        || (usuarioOriginal.getEmpresa().getIdEmpresa() != usuario.getEmpresa().getIdEmpresa())) {

                    usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);
                }
            }
        }        
        
        
        //REGLA NEGOCIO:Si cambiamos el EMail hay que volver a verificar la nueva dirección
        if (!usuarioOriginal.getEmail()
                .equals(usuario.getEmail())) {
            usuario.setValidadoEmail(false);
            usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
            sendMailValidacionEMail(usuario);
        }

        return super.update(usuario);
    }

    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }

    
    @ParameterSearch(parameterNames = "idTitulado")
    public Usuario getUsuarioFromTitulado(int idTitulado) throws BusinessException  {
        List<Filter> filters=new ArrayList<>();
        filters.add(new Filter("titulado.idTitulado", idTitulado));
        List<Usuario> usuarios=this.getDAO().search(filters);
        
        if (usuarios.size()==1) {
            return usuarios.get(0);
        } else if (usuarios.size()==0) {
            return null;
        } else {
            throw new RuntimeException("La consulta retornó mas de un elemento:" + usuarios.size());
        }
        
    };
    
    private void sendMailValidacionEMail(Usuario usuario) {
        //Enviar el Mail de Verificación

    }
}
