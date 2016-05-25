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
package es.logongas.fpempresa.modelo.centro;

import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.ix3.core.annotations.Label;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * La certificación de un centro de que un titulado tiene el título
 *
 * @author logongas
 */
public class CertificadoTitulo {

    private int idCertificadoTitulo;

    @NotNull
    private Centro centro;

    @Min(1900)
    @Label("Año")
    private int anyo;

    @NotNull
    private Ciclo ciclo;

    @NotBlank
    @Label("NIF/NIE")
    private String nifnie;
   
    
    public boolean isCertificadoNifNie(String nifnie) {
        
        if (nifnie==null) {
            return false;
        }
        
        if (this.getNifnies().contains(nifnie.toUpperCase())) {
            return true;
        } else{
            return false;
        }
        
    }
    
    
    public Set<String> getNifnies() {
        Set<String> nifnies = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        
        String[] rawNifnies = this.nifnie.split("[,\\s]");
        

        for (String currentNifnie : rawNifnies) {
            String realNifnie = currentNifnie.trim();

            if (realNifnie.length() > 0) {
                nifnies.add(realNifnie.toUpperCase());
            }
        }

        return nifnies;
    }

    public CertificadoTitulo() {
        this.anyo = Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * @return the idCertificadoTitulo
     */
    public int getIdCertificadoTitulo() {
        return idCertificadoTitulo;
    }

    /**
     * @param idCertificadoTitulo the idCertificadoTitulo to set
     */
    public void setIdCertificadoTitulo(int idCertificadoTitulo) {
        this.idCertificadoTitulo = idCertificadoTitulo;
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
     * @return the anyo
     */
    public int getAnyo() {
        return anyo;
    }

    /**
     * @param anyo the anyo to set
     */
    public void setAnyo(int anyo) {
        this.anyo = anyo;
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
     * @return the nifnie
     */
    public String getNifnie() {
        return nifnie;
    }

    /**
     * @param nifnie the nifnie to set
     */
    public void setNifnie(String nifnie) {
        this.nifnie = nifnie;
    }

}
