/**
 * FPempresa Copyright (C) 2020 Lorenzo González
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
package es.logongas.fpempresa.modelo.estadisticas;

/**
 *
 * @author logongas
 */
public class EstadisticasPrincipal {
    
    private long numeroOfertas;
    private long numeroTitulados;

    public EstadisticasPrincipal(long numeroOfertas, long numeroTitulados) {
        this.numeroOfertas = numeroOfertas;
        this.numeroTitulados = numeroTitulados;
    }

    /**
     * @return the numeroOfertas
     */
    public long getNumeroOfertas() {
        return numeroOfertas;
    }

    /**
     * @param numeroOfertas the numeroOfertas to set
     */
    public void setNumeroOfertas(long numeroOfertas) {
        this.numeroOfertas = numeroOfertas;
    }

    /**
     * @return the numeroTitulados
     */
    public long getNumeroTitulados() {
        return numeroTitulados;
    }

    /**
     * @param numeroTitulados the numeroTitulados to set
     */
    public void setNumeroTitulados(long numeroTitulados) {
        this.numeroTitulados = numeroTitulados;
    }
    
    
    
}