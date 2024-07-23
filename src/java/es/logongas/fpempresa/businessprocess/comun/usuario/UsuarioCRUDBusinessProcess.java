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
package es.logongas.fpempresa.businessprocess.comun.usuario;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;

/**
 *
 * @author logongas
 */
public interface UsuarioCRUDBusinessProcess extends CRUDBusinessProcess<Usuario, Integer> {

    void updatePassword(UpdatePasswordArguments updatePasswordArguments) throws BusinessException;
    void enviarMailResetearContrasenya(EnviarMailResetearContrasenyaArguments enviarMailResetearContrasenyaArguments) throws BusinessException;
    void resetearContrasenya(ResetearContrasenyaArguments resetearContrasenyaArguments) throws BusinessException;
    void validarEmail(ValidarEmailArguments validarEmailArguments) throws BusinessException;
    Usuario getUsuarioFromTitulado(GetUsuarioFromTituladoArguments getUsuarioFromTituladoArguments) throws BusinessException;
    byte[] getFoto(GetFotoArguments getFotoArguments) throws BusinessException;
    void updateFoto(UpdateFotoArguments updateFotoArguments) throws BusinessException;
    Usuario updateEstadoUsuario(UpdateEstadoUsuarioArguments updateEstadoUsuarioArguments) throws BusinessException;
    Usuario updateCentro(UpdateCentroArguments updateCentroArguments) throws BusinessException;
    byte[]  getCurriculum(GetCurriculumArguments getCurriculumArguments) throws BusinessException;
    void  enviarMensajeSoporte(EnviarMensajeSoporteArguments enviarMensajeSoporteArguments) throws BusinessException;
    void  notificarUsuarioInactivo(NotificarUsuarioInactivoArguments notificarUsuarioInactivoArguments) throws BusinessException;
    void  softDelete(SoftDeleteArguments softDeleteArguments) throws BusinessException;
    void cancelarSuscripcion(CancelarSuscripcionArguments cancelarSuscripcionArguments) throws BusinessException;    
    void enviarMailValidarEMail(EnviarMailValidarEMailArguments enviarMailValidarEMailArguments) throws BusinessException;
    
    public class UpdatePasswordArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public String currentPassword;
        final public String newPassword;

        public UpdatePasswordArguments(Principal principal, DataSession dataSession, Usuario usuario, String currentPassword, String newPassword) {
            super(principal, dataSession);
            this.usuario = usuario;

            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
        }

    }

    public class ResetearContrasenyaArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public String claveResetearContrasenya;
        final public String nuevaContrasenya;

        public ResetearContrasenyaArguments(Principal principal, DataSession dataSession,Usuario usuario, String claveResetearContrasenya, String nuevaContrasenya) {
            super(principal, dataSession);
            this.usuario = usuario;
            this.claveResetearContrasenya = claveResetearContrasenya;
            this.nuevaContrasenya = nuevaContrasenya;
        }
    }
    
    public class EnviarMailResetearContrasenyaArguments extends BusinessProcess.BusinessProcessArguments {

        final public String email;

        public EnviarMailResetearContrasenyaArguments(Principal principal, DataSession dataSession, String email) {
            super(principal, dataSession);
            this.email = email;
        }
    }

    public class ValidarEmailArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public String claveValidarEmail;

        public ValidarEmailArguments(Principal principal, DataSession dataSession,Usuario usuario, String claveValidarEmail) {
            super(principal, dataSession);
            this.usuario = usuario;
            this.claveValidarEmail = claveValidarEmail;
        }
    }

    public class CancelarSuscripcionArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public String publicToken;

        public CancelarSuscripcionArguments(Principal principal, DataSession dataSession,Usuario usuario, String publicToken) {
            super(principal, dataSession);
            this.usuario = usuario;
            this.publicToken = publicToken;
        }
    }    
    
    public class GetUsuarioFromTituladoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Titulado titulado;

        public GetUsuarioFromTituladoArguments() {
        }

        public GetUsuarioFromTituladoArguments(Principal principal, DataSession dataSession, Titulado titulado) {
            super(principal, dataSession);

            this.titulado = titulado;
        }

    }

    public class GetFotoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Usuario usuario;

        public GetFotoArguments() {
        }

        public GetFotoArguments(Principal principal, DataSession dataSession, Usuario usuario) {
            super(principal, dataSession);

            this.usuario = usuario;
        }

    }
    public class GetCurriculumArguments extends BusinessProcess.BusinessProcessArguments {

        public Usuario usuario;

        public GetCurriculumArguments() {
        }

        public GetCurriculumArguments(Principal principal, DataSession dataSession, Usuario usuario) {
            super(principal, dataSession);

            this.usuario = usuario;
        }

    }
    public class UpdateFotoArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public byte[] foto;

        public UpdateFotoArguments(Principal principal, DataSession dataSession, Usuario usuario, byte[] foto) {
            super(principal, dataSession);

            this.usuario = usuario;
            this.foto = foto;
        }

    }

    public class UpdateEstadoUsuarioArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public EstadoUsuario estadoUsuario;

        public UpdateEstadoUsuarioArguments(Principal principal, DataSession dataSession, Usuario usuario, EstadoUsuario estadoUsuario) {
            super(principal, dataSession);

            this.usuario = usuario;
            this.estadoUsuario = estadoUsuario;
        }

    }

    public class UpdateCentroArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public Centro centro;

        public UpdateCentroArguments(Principal principal, DataSession dataSession, Usuario usuario, Centro centro) {
            super(principal, dataSession);

            this.usuario = usuario;
            this.centro = centro;
        }

    }
    
    public class EnviarMensajeSoporteArguments extends BusinessProcess.BusinessProcessArguments {

        final public String nombre;
        final public String correo;
        final public String mensaje;

        public EnviarMensajeSoporteArguments(Principal principal, DataSession dataSession, String nombre, String correo,String mensaje) {
            super(principal, dataSession);
            this.nombre = nombre;
            this.correo = correo;
            this.mensaje = mensaje;
        }
    }
    

    public class NotificarUsuarioInactivoArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;

        public NotificarUsuarioInactivoArguments(Principal principal, DataSession dataSession,Usuario usuario) {
            super(principal, dataSession);
            this.usuario = usuario;
        }
    }
    
    public class SoftDeleteArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;

        public SoftDeleteArguments(Principal principal, DataSession dataSession,Usuario usuario) {
            super(principal, dataSession);
            this.usuario = usuario;
        }
    }  
    
    public class EnviarMailValidarEMailArguments extends BusinessProcess.BusinessProcessArguments {

        final public String email;
        final public String captchaWord;
        final public String keyCaptcha;

        public EnviarMailValidarEMailArguments(Principal principal, DataSession dataSession, String email, String captchaWord, String keyCaptcha) {
            super(principal, dataSession);
            this.email = email;
            this.captchaWord = captchaWord;
            this.keyCaptcha = keyCaptcha;

        }
    }    
    
    
}
