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
package es.logongas.fpempresa.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author logongas
 */
public class DateUtil {

    public static Date add(Date date, Interval interval, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(interval.getField(), amount);
        Date result = calendar.getTime();

        return result;
    }

    public static int get(Date date, Interval interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int part=calendar.get(interval.getField());
        
        return part;
    }    
    
    public enum Interval {
        MINUTE(Calendar.MINUTE),
        HOUR(Calendar.HOUR),
        HOUR_OF_DAY(Calendar.HOUR_OF_DAY),        
        DAY(Calendar.DATE),
        MONTH(Calendar.MONTH),
        YEAR(Calendar.YEAR);
        
        private int field;

        private Interval(int field) {
            this.field = field;
        }
        
        public int getField() {
            return this.field;
        }
        
        
    }
    
}
