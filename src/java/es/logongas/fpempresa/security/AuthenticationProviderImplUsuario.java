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

import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.security.model.Identity;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.security.authentication.impl.CredentialImplLoginPassword;
import es.logongas.ix3.security.authentication.AuthenticationProvider;
import es.logongas.ix3.security.authentication.Credential;
import es.logongas.ix3.security.authentication.Principal;
import es.logongas.ix3.service.CRUDServiceFactory;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Autenticar a un usuario mediante el usuario y contraseña de moodle
 *
 * @author Lorenzo González
 */
public class AuthenticationProviderImplUsuario implements AuthenticationProvider {

    @Autowired
    CRUDServiceFactory crudServiceFactory;

    protected final Log log = LogFactory.getLog(getClass());

    
    @Override
    public Principal authenticate(Credential credential) throws BusinessException {

        if ((credential instanceof CredentialImplLoginPassword) == false) {
            return null;
        }
        CredentialImplLoginPassword credentialImplLoginPassword = (CredentialImplLoginPassword) credential;

        if ((credentialImplLoginPassword.getLogin() == null) || (credentialImplLoginPassword.getLogin().trim().isEmpty())) {
            return null;
        }

        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
        Usuario usuario = usuarioService.readOriginalByNaturalKey(credentialImplLoginPassword.getLogin());

        if (usuario != null) {
            String plainPassword = credentialImplLoginPassword.getPassword();

            if (usuarioService.checkPassword(usuario, plainPassword)) {

                switch (usuario.getTipoUsuario()) {
                    case ADMINISTRADOR:
                        
                        if (usuario.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                            throw new BusinessException("Debes estar 'Aceptado' para poder entrar, pero tu estado es:"+usuario.getEstadoUsuario());
                        }                        
                        
                        break;
                    case CENTRO:
                        
                        if ((usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION) && (usuario.getCentro()!=null)) {
                            throw new BusinessException("No puedes entrar ya que aun estás a la espera de ser aceptado o rechazado en el centro '" +  usuario.getCentro() + "'");
                        }
                        
                        if ((usuario.getCentro() != null) && (usuario.getCentro().getEstadoCentro() != EstadoCentro.PERTENECE_A_FPEMPRESA)) {
                            throw new BusinessException("Tu centro debe pertenecer a FPempresa para poder entrar");
                        }
                        
                        break;
                    case EMPRESA:
                        
                        if ((usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION) && (usuario.getEmpresa()!=null)) {
                            throw new BusinessException("No puedes entrar ya que aun estás a la espera de ser aceptado en la empresa '" +  usuario.getEmpresa() + "'");
                        }                        
                        
                        break;
                    case TITULADO:
                        
                        if (usuario.getEstadoUsuario() != EstadoUsuario.ACEPTADO) {
                            throw new BusinessException("Debes estar 'Aceptado' para poder entrar, pero tu estado es:"+usuario.getEstadoUsuario());
                        }
                        
                        break;                        
                }
                

                if ((usuario.getTipoUsuario() == TipoUsuario.CENTRO) && (usuario.getCentro() != null) && (usuario.getCentro().getEstadoCentro() != EstadoCentro.PERTENECE_A_FPEMPRESA)) {
                    throw new BusinessException("Tu centro debe pertenecer a FPempresa para poder entrar");
                }

                Principal principal = usuario;
                return principal;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    public Principal getPrincipalBySID(Serializable sid) throws BusinessException {
        Integer idIdentity = (Integer) sid;
        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);

        return usuarioService.readOriginal(idIdentity);
    }

    protected Principal getPrincipalByLogin(String login) throws BusinessException {
        UsuarioCRUDService usuarioService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
        Identity identity = usuarioService.readOriginalByNaturalKey(login);

        return identity;
    }

}
