/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
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
package es.logongas.fpempresa.service.hojacalculo;

import java.util.List;

/**
 *
 * @author logongas
 */
public interface HojaCalculoService {

    /**
     * Obtiene una hoja de calculo
     *
     * @param rows Los datos a mostrar
     * @param properties Cada una de las propiedades de cada objeto a mostrar
     * separadas por comas
     * @return La hoja de cálculo
     */
    byte[] getHojaCalculo(List<? extends Object> dataRows, String properties, String labels);

}
