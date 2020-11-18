/**
 * FPempresa Copyright (C) 2020 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */



package es.logongas.fpempresa.security;

import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.security.model.Identity;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.security.authentication.impl.CredentialImplLoginPassword;
import es.logongas.ix3.security.authentication.AuthenticationProvider;
import es.logongas.ix3.security.authentication.Credential;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.service.CRUDServiceFactory;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Autenticar a un usuario mediante el usuario y contraseña de moodle
 *
 * @author Lorenzo González
 */
public class AuthenticationProviderImplUsuario implements AuthenticationProvider {

    @Autowired
    CRUDServiceFactory crudServiceFactory;

    protected final Logger log = LogManager.getLogger(getClass());

    @Override
    public Principal authenticate(Credential credential, DataSession dataSession) throws BusinessException {

        if ((credential instanceof CredentialImplLoginPassword) == false) {
            return null;
        }
        CredentialImplLoginPassword credentialImplLoginPassword = (CredentialImplLoginPassword) credential;

        if ((credentialImplLoginPassword.getLogin() == null) || (credentialImplLoginPassword.getLogin().trim().isEmpty())) {
            return null;
        }

        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
        Usuario usuario = usuarioService.readOriginalByNaturalKey(dataSession, credentialImplLoginPassword.getLogin());

        if (usuario != null) {
            String plainPassword = credentialImplLoginPassword.getPassword();

            if (usuarioService.checkPassword(dataSession, usuario, plainPassword)) {

                if (!usuario.isValidadoEmail()) {
                    throw new BusinessException("Tu cuenta no está validada. Si no has recibido un correo para validarla, ponte en contacto con el soporte de empleaFP.");
                }

                switch (usuario.getTipoUsuario()) {
                    case ADMINISTRADOR:

                        if (usuario.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                            throw new BusinessException("Debes estar 'Aceptado' para poder entrar, pero tu estado es:" + usuario.getEstadoUsuario());
                        }

                        break;
                    case CENTRO:

                        if ((usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION) && (usuario.getCentro() != null)) {
                            throw new BusinessException("No puedes entrar ya que aun estás a la espera de ser aceptado o rechazado en el centro '" + usuario.getCentro() + "'");
                        }

                        if ((usuario.getCentro() != null) && (usuario.getCentro().getEstadoCentro() != EstadoCentro.PERTENECE_A_FPEMPRESA)) {
                            throw new BusinessException("Tu centro debe pertenecer a FPempresa para poder entrar");
                        }

                        break;
                    case EMPRESA:

                        if ((usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION) && (usuario.getEmpresa() != null)) {
                            throw new BusinessException("No puedes entrar ya que aun estás a la espera de ser aceptado en la empresa '" + usuario.getEmpresa() + "'");
                        }

                        break;
                    case TITULADO:

                        if (usuario.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                            throw new BusinessException("Debes estar 'Aceptado' para poder entrar, pero tu estado es:" + usuario.getEstadoUsuario());
                        }

                        break;
                }

                if ((usuario.getTipoUsuario() == TipoUsuario.CENTRO) && (usuario.getCentro() != null) && (usuario.getCentro().getEstadoCentro() != EstadoCentro.PERTENECE_A_FPEMPRESA)) {
                    throw new BusinessException("Tu centro debe pertenecer a FPempresa para poder entrar");
                }

                
                
                updateFechaUltimoAcceso(dataSession,usuario);

                
                Principal principal = usuario;
                return principal;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private void updateFechaUltimoAcceso(DataSession dataSession,Usuario usuario) throws BusinessException {
        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);

        usuarioService.updateFechaUltimoAcceso(dataSession, usuario);    
    }
    
    
    @Override
    public Principal getPrincipalBySID(Serializable sid, DataSession dataSession) throws BusinessException {
        Integer idIdentity = (Integer) sid;
        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);

        return usuarioService.readOriginal(dataSession, idIdentity);
    }

    protected Principal getPrincipalByLogin(String login, DataSession dataSession) throws BusinessException {
        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
        Identity identity = usuarioService.readOriginalByNaturalKey(dataSession, login);

        return identity;
    }

}
