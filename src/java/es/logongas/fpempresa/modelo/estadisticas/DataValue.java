/*
 * FPempresa Copyright (C) 2024 Lorenzo González
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
public class DataValue {
    private final String data;
    private final int value;

    public DataValue(String data, int value) {
        this.data = data;
        this.value = value;
    }

    @Override
    public String toString() {
        return data;
    }

    
    
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }


    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

}
