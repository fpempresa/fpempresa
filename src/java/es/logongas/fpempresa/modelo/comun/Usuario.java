/**
 * FPempresa Copyright (C) 2014 Lorenzo González
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
package es.logongas.fpempresa.modelo.comun;

import es.logongas.ix3.security.model.User;
import es.logongas.ix3.core.annotations.Label;
import es.logongas.ix3.security.authentication.Principal;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lorenzo
 */
public class Usuario extends User  implements PostLoadEventListener,Principal {
    
    @Email
    @NotBlank
    @Label("Correo electrónico")
    private String eMail;
    
    @NotBlank
    private String nombre;
    
    @Label("1º Apellido")
    @NotBlank
    private String ape1;
    
    @Label("2º Apellido")
    private String ape2;
    
    private byte[] foto;
    
    @Label("Contraseña")
    private String password;

    @NotNull
    @Label("Tipo de usuario")
    private TipoUsuario tipoUsuario;
    
    
    public Usuario() {
        this.tipoUsuario=TipoUsuario.TITULADO;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return the eMail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * @param eMail the eMail to set
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
        this.login=this.eMail;
    }
    /**
     * @return the eMail
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * @param eMail the eMail to set
     */
    public void setEMail(String eMail) {
        this.eMail = eMail;
        this.login=this.eMail;
    }
       
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.name=getCalculateName();
    }

    /**
     * @return the ape1
     */
    public String getApe1() {
        return ape1;
    }

    /**
     * @param ape1 the ape1 to set
     */
    public void setApe1(String ape1) {
        this.ape1 = ape1;
        this.name=getCalculateName();        
    }

    /**
     * @return the ape2
     */
    public String getApe2() {
        return ape2;
    }

    /**
     * @param ape2 the ape2 to set
     */
    public void setApe2(String ape2) {
        this.ape2 = ape2;
        this.name=getCalculateName();        
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the tipoUsuario
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
    private String getCalculateName() {
        return nombre + " " + ape1 + (ape2 != null ? " " + ape2 : "");
    }
    
    @Override
    public void onPostLoad(PostLoadEvent ple) {
        Usuario usuario=(Usuario)ple.getEntity();
        //Nunca se retorna el Hash de la contraseña
        usuario.setPassword(null);

    }   
    
    
    /*
    @AssertTrue(message = "El registro está deshabilitado")
    @Label("")
    private boolean isProhibidoNuevoUsuario() {
        return false;
    }
    
    @AssertTrue(message = "Solo se permite registrar titulados")
    @Label("")
    private boolean isSoloPermitidoTitulados() {
        if (this.tipoUsuario==TipoUsuario.TITULADO) {
            return true;
        } else {
            return false;
        }
    } 
    */
    
}
