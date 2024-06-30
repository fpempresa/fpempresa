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

import java.util.List;

/**
 *
 * @author logongas
 */
public class Estadistica {
    
    private final String title;
    private final String xLabel;
    private final String yLabel;    
    
    private final List<DataValue> dataValues;

    public Estadistica(String title, String xLabel, String yLabel, List<DataValue> dataValues) {
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.dataValues = dataValues;
    }

    @Override
    public String toString() {
        return title;
    }
    
    
    
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the xLabel
     */
    public String getxLabel() {
        return xLabel;
    }

    /**
     * @return the yLabel
     */
    public String getyLabel() {
        return yLabel;
    }

    /**
     * @return the dataValues
     */
    public List<DataValue> getDataValues() {
        return dataValues;
    }


  
    
}
