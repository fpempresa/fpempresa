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
package es.logongas.fpempresa.modelo.comun;

import es.logongas.ix3.core.annotations.Label;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

/**
 * Datos e contatos de una empresa, centro o oferta
 * @author logongas
 */
public class Contacto {

    @URL
    @Label("URL")
    private String url;
    
    @Email
    @Label("Correo electrónico")
    private String email;
    
    @Label("Persona de contacto")
    private String persona;
    
    @Pattern(regexp = "[0-9]{9}| {0}")
    @Label("Teléfono")
    private String telefono;
    
    @Pattern(regexp = "[0-9]{9}| {0}")
    @Label("Fax")
    private String fax;
    
    @Label("Datos contacto")
    private String textoLibre;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the persona
     */
    public String getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(String persona) {
        this.persona = persona;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the textoLibre
     */
    public String getTextoLibre() {
        return textoLibre;
    }

    /**
     * @param textoLibre the textoLibre to set
     */
    public void setTextoLibre(String textoLibre) {
        this.textoLibre = textoLibre;
    }
}
