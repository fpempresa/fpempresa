/**
 * FPempresa Copyright (C) 2019 Lorenzo González
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
package es.logongas.fpempresa.service.template.impl;

import com.x5.template.Chunk;
import com.x5.template.filters.ChunkFilter;
import com.x5.template.filters.FilterArgs;
import com.x5.util.ObjectDataMap;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author logongas
 */
public class ChunkDateFilter implements ChunkFilter {

    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    @Override
    public Object applyFilter(Chunk chunk, String text, FilterArgs args) {
        return text;
    }

    @Override
    public Object applyFilter(Chunk chunk, Object obj, FilterArgs args) {
        Object realObject;

        if (obj instanceof ObjectDataMap) {
            ObjectDataMap objectDataMap = (ObjectDataMap) obj;
            realObject = objectDataMap.unwrap();
        } else {
            realObject = obj;
        }

        if (obj == null) {
            return "";
        }

        if (realObject instanceof Date) {
            return format((Date) realObject, args);
        } else {
            return realObject.toString();
        }

    }

    private String format(Date date, FilterArgs args) {
        String format = args.getUnparsedArgs();
        if ((format == null) || (format.trim().isEmpty())) {
            format = DEFAULT_DATE_FORMAT;
        }

        return new SimpleDateFormat(format).format(date);

    }

    @Override
    public String getFilterName() {
        return "date";
    }

    @Override
    public String[] getFilterAliases() {
        return new String[]{};
    }

}
