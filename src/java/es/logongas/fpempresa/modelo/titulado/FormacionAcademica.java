/**
 *   FPempresa
 *   Copyright (C) 2015  Lorenzo González
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
package es.logongas.fpempresa.modelo.titulado;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.ix3.core.annotations.Label;
import es.logongas.ix3.core.annotations.ValuesList;
import java.util.Date;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 *
 * @author Lorenzo
 */
public class FormacionAcademica {

    private int idFormacionAcademica;

    @Label("Tipo del título")
    @NotNull
    private TipoFormacionAcademica tipoFormacionAcademica;

    @ValuesList(shortLength = true)
    @Label("Centro educativo")
    private Centro centro;

    @ValuesList(shortLength = true)
    private Ciclo ciclo;

    @Label("Nombre centro")
    private String otroCentro;

    @Label("Titulo")
    private String otroTitulo;

    @NotNull
    private Titulado titulado;
    
    @Past
    @NotNull
    @Label("Fecha de obtención del título")
    private Date fecha;
    
    private boolean certificadoTitulo;
    
    public FormacionAcademica() {
    }

    @Override
    public String toString() {
        return getNombreTitulo() + " en " + getNombreCentro();
    }

    @AssertTrue(message = "No puede estar vacio")
    @Label("Centro")
    private boolean getValidateCentro() {
        switch (tipoFormacionAcademica) {
            case CICLO_FORMATIVO:
                if (centro == null) {
                    return false;
                } else {
                    return true;
                }
            case TITULO_UNIVERSITARIO:
                return true;
            case OTRO_TITULO:
                return true;
            default:
                throw new RuntimeException("El tipoFormacionAcademica no es válido:" + tipoFormacionAcademica);
        }
    }

    @AssertTrue(message = "No puede estar vacio")
    @Label("Ciclo")
    private boolean getValidateCiclo() {
        switch (tipoFormacionAcademica) {
            case CICLO_FORMATIVO:
                if (ciclo == null) {
                    return false;
                } else {
                    return true;
                }
            case TITULO_UNIVERSITARIO:
                return true;
            case OTRO_TITULO:
                return true;
            default:
                throw new RuntimeException("El tipoFormacionAcademica no es válido:" + tipoFormacionAcademica);
        }
    }

    @AssertTrue(message = "El nombre del centro no puede estar vacio")
    @Label("Nombre centro")
    private boolean getValidateOtroCentro() {
        switch (tipoFormacionAcademica) {
            case CICLO_FORMATIVO:
                if ((centro != null) && (centro.getIdCentro()< 0)) {
                    //Si el IdCentro es negativo es que es un centro desconocido
                    if ((otroCentro == null) || otroCentro.trim().isEmpty()) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            case TITULO_UNIVERSITARIO:
                if ((otroCentro == null) || otroCentro.trim().isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            case OTRO_TITULO:
                if ((otroCentro == null) || otroCentro.trim().isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            default:
                throw new RuntimeException("El tipoFormacionAcademica no es válido:" + tipoFormacionAcademica);
        }
    }

    @AssertTrue(message = "El titulo no puede estar vacio")
    @Label("Titulo")
    private boolean getValidateOtroTitulo() {
        switch (tipoFormacionAcademica) {
            case CICLO_FORMATIVO:
                return true;
            case TITULO_UNIVERSITARIO:
                if ((otroTitulo == null) || otroTitulo.trim().isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            case OTRO_TITULO:
                if ((otroTitulo == null) || otroTitulo.trim().isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            default:
                throw new RuntimeException("El tipoFormacionAcademica no es válido:" + tipoFormacionAcademica);
        }
    }

    public String getNombreCentro() {
        if (tipoFormacionAcademica == TipoFormacionAcademica.CICLO_FORMATIVO) {
            if (centro!=null) {
                if (centro.getIdCentro() < 0) {
                    return otroCentro;
                } else {
                    return centro.toString();
                }
            } else {
                return "";
            }
        } else {
            return otroCentro;
        }
    }

    public String getNombreTitulo() {
        if (tipoFormacionAcademica == TipoFormacionAcademica.CICLO_FORMATIVO) {
            return ciclo + "";
        } else {
            return otroTitulo;
        }
    }

    /**
     * @return the idFormacionAcademica
     */
    public int getIdFormacionAcademica() {
        return idFormacionAcademica;
    }

    /**
     * @param idFormacionAcademica the idFormacionAcademica to set
     */
    public void setIdFormacionAcademica(int idFormacionAcademica) {
        this.idFormacionAcademica = idFormacionAcademica;
    }

    /**
     * @return the tipoFormacionAcademica
     */
    public TipoFormacionAcademica getTipoFormacionAcademica() {
        return tipoFormacionAcademica;
    }

    /**
     * @param tipoFormacionAcademica the tipoFormacionAcademica to set
     */
    public void setTipoFormacionAcademica(TipoFormacionAcademica tipoFormacionAcademica) {
        this.tipoFormacionAcademica = tipoFormacionAcademica;
    }

    /**
     * @return the centro
     */
    public Centro getCentro() {
        return centro;
    }

    /**
     * @param centro the centro to set
     */
    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    /**
     * @return the ciclo
     */
    public Ciclo getCiclo() {
        return ciclo;
    }

    /**
     * @param ciclo the ciclo to set
     */
    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * @return the otroCentro
     */
    public String getOtroCentro() {
        return otroCentro;
    }

    /**
     * @param otroCentro the otroCentro to set
     */
    public void setOtroCentro(String otroCentro) {
        this.otroCentro = otroCentro;
    }

    /**
     * @return the otroTitulo
     */
    public String getOtroTitulo() {
        return otroTitulo;
    }

    /**
     * @param otroTitulo the otroTitulo to set
     */
    public void setOtroTitulo(String otroTitulo) {
        this.otroTitulo = otroTitulo;
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
     * @return the certificadoTitulo
     */
    public boolean isCertificadoTitulo() {
        return certificadoTitulo;
    }

    /**
     * @param certificadoTitulo the certificadoTitulo to set
     */
    public void setCertificadoTitulo(boolean certificadoTitulo) {
        this.certificadoTitulo = certificadoTitulo;
    }
}
