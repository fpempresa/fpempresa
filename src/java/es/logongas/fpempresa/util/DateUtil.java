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
    
    public static Duration getDateInterval(Date upperDate,Date lowerDate) {
        final double milisegundosMinuto=60*1000;
        final double milisegundosHora=60*60*1000;        
        final double milisegundosDia=60*60*24*1000;
        
        long diffInMilisegundos = upperDate.getTime() - lowerDate.getTime();
        long diffInMinutos = Math.round((double)diffInMilisegundos/milisegundosMinuto);
        long diffInHoras = Math.round((double)diffInMilisegundos/milisegundosHora);
        long diffInDias = Math.round((double)diffInMilisegundos/milisegundosDia);
        
        if (diffInMinutos<60) {
            return new Duration(diffInMinutos, Interval.MINUTE);
        } else if (diffInHoras<24) {
            return new Duration(diffInHoras, Interval.HOUR_OF_DAY);
        } else {
           return new Duration(diffInDias, Interval.DAY);     
        }
        
    }
    
    
    static public class Duration {
        long value;
        Interval interval;

        public Duration(long value, Interval interval) {
            this.value = value;
            this.interval = interval;
        }

        @Override
        public String toString() {
            String unidades;
            switch (this.interval) {
                case MINUTE:
                    unidades="minutos";
                    if (this.value==1) {
                        unidades="minuto";
                    }
                    
                    break;
                case HOUR_OF_DAY:
                    unidades="horas";
                    if (this.value==1) {
                        unidades="hora";
                    }                    
                    break;
                case DAY:
                    unidades="dias";
                    if (this.value==1) {
                        unidades="dia";
                    }                    
                    break;
                default:
                    throw new RuntimeException("El tipo de intervalo es desconocido:"+this.interval);
            }
            
            
            return this.value + " " + unidades;
        }
  
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
