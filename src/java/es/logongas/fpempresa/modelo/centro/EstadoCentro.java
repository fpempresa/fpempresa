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

package es.logongas.fpempresa.modelo.centro;

/**
 *
 * @author Lorenzo
 */
public enum EstadoCentro {
    PERTENECE_A_FPEMPRESA("Pertenece a FPempresa"),
    BAJA_EN_FPEMPRESA("Baja en FPempresa"),
    NO_PERTENCE_A_FPEMPRESA("NO pertenece a FPempresa");
    
    String text;

    private EstadoCentro(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
