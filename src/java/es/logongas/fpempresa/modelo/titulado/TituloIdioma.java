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
package es.logongas.fpempresa.modelo.titulado;

import es.logongas.ix3.core.annotations.Label;
import java.util.Date;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 *
 * @author Lorenzo
 */
public class TituloIdioma {

    private int idTituloIdioma;

    @NotNull
    @Label("Nivel")
    private NivelIdioma nivelIdioma;

    @NotNull
    private Idioma idioma;

    @Label("Otro Idioma")
    private String otroIdioma;

    @Past
    @Label("Fecha de obtención del título")
    private Date fecha;

    @NotNull
    private Titulado titulado;

    /**
     * @return the idTituloIdioma
     */
    public int getIdTituloIdioma() {
        return idTituloIdioma;
    }

    /**
     * @param idTituloIdioma the idTituloIdioma to set
     */
    public void setIdTituloIdioma(int idTituloIdioma) {
        this.idTituloIdioma = idTituloIdioma;
    }

    /**
     * @return the nivelIdioma
     */
    public NivelIdioma getNivelIdioma() {
        return nivelIdioma;
    }

    /**
     * @param nivelIdioma the nivelIdioma to set
     */
    public void setNivelIdioma(NivelIdioma nivelIdioma) {
        this.nivelIdioma = nivelIdioma;
    }

    /**
     * @return the idioma
     */
    public Idioma getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the titulado
     */
    public Titulado getTitulado() {
        return titulado;
    }

    /**
     * @param titulado the titulado to set
     */
    public void setTitulado(Titulado titulado) {
        this.titulado = titulado;
    }

    /**
     * @return the otroIdioma
     */
    public String getOtroIdioma() {
        return otroIdioma;
    }

    /**
     * @param otroIdioma the otroIdioma to set
     */
    public void setOtroIdioma(String otroIdioma) {
        this.otroIdioma = otroIdioma;
    }

    @AssertTrue(message = "Debe indicar el nombre del idioma")
    private boolean isOtroIdioma() {
        if (idioma == Idioma.OTRO) {
            if ((otroIdioma == null) || (otroIdioma.trim().isEmpty())) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        if (idioma==null) {
            return "";
        } else {
            if (idioma==Idioma.OTRO) {
                return otroIdioma;
            } else {
                return idioma.text;
            }
        }
    }
    
    
}
