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
package es.logongas.fpempresa.modelo.educacion;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lorenzo
 */
public class Familia {

    private int idFamilia;

    @NotBlank
    private String descripcion;

    public Familia() {
    }

    public Familia(int idFamilia, String descripcion) {
        this.idFamilia = idFamilia;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    /**
     * @return the idFamilia
     */
    public int getIdFamilia() {
        return idFamilia;
    }

    /**
     * @param idFamilia the idFamilia to set
     */
    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
