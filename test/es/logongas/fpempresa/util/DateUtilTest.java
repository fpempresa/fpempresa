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
package es.logongas.fpempresa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author logongas
 */
public class DateUtilTest {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public DateUtilTest() {
    }

    @Test
    public void testAdd7days() throws ParseException {
        System.out.println("testAdd7days");
        Date date = simpleDateFormat.parse("02/03/2024 06:07:08");
        DateUtil.Interval interval = DateUtil.Interval.DAY;
        int amount = 7;
        Date expResult = simpleDateFormat.parse("09/03/2024 06:07:08");;
        Date result = DateUtil.add(date, interval, amount);
        assertEquals(expResult, result);

    }
    @Test
    public void testAdd7Hour() throws ParseException {
        System.out.println("testAdd7Hour");
        Date date = simpleDateFormat.parse("02/03/2024 06:07:08");
        DateUtil.Interval interval = DateUtil.Interval.HOUR;
        int amount = 7;
        Date expResult = simpleDateFormat.parse("02/03/2024 13:07:08");;
        Date result = DateUtil.add(date, interval, amount);
        assertEquals(expResult, result);

    }    
    @Test
    public void testAdd7Minute() throws ParseException {
        System.out.println("testAdd7Minute");
        Date date = simpleDateFormat.parse("02/03/2024 06:07:08");
        DateUtil.Interval interval = DateUtil.Interval.MINUTE;
        int amount = 7;
        Date expResult = simpleDateFormat.parse("02/03/2024 06:14:08");;
        Date result = DateUtil.add(date, interval, amount);
        assertEquals(expResult, result);
    }
    @Test
    public void testAdd7Month() throws ParseException {
        System.out.println("testAdd7Month");
        Date date = simpleDateFormat.parse("02/03/2024 06:07:08");
        DateUtil.Interval interval = DateUtil.Interval.MONTH;
        int amount = 7;
        Date expResult = simpleDateFormat.parse("02/10/2024 06:07:08");;
        Date result = DateUtil.add(date, interval, amount);
        assertEquals(expResult, result);
    }  
    @Test
    public void testAdd7Year() throws ParseException {
        System.out.println("testAdd7Year");
        Date date = simpleDateFormat.parse("02/03/2024 06:07:08");
        DateUtil.Interval interval = DateUtil.Interval.YEAR;
        int amount = 7;
        Date expResult = simpleDateFormat.parse("02/03/2031 06:07:08");;
        Date result = DateUtil.add(date, interval, amount);
        assertEquals(expResult, result);
    }     
    @Test
    public void testAdd23HourDay() throws ParseException {
        System.out.println("testAdd23HourDay");
        Date date = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Interval interval = DateUtil.Interval.HOUR_OF_DAY;
        int amount = 23;
        Date expResult = simpleDateFormat.parse("03/03/2024 22:07:08");;
        Date result = DateUtil.add(date, interval, amount);
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testGetDay() throws ParseException {
        System.out.println("getDay");
        Date date = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Interval interval = DateUtil.Interval.DAY;
        int expResult = 2;
        int result = DateUtil.get(date, interval);
        assertEquals(expResult, result);
    }
    @Test
    public void testGetHour() throws ParseException {
        System.out.println("testGetHour");
        Date date = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Interval interval = DateUtil.Interval.HOUR;
        int expResult = 11;
        int result = DateUtil.get(date, interval);
        assertEquals(expResult, result);
    }  
    @Test
    public void testGetHourOfDay() throws ParseException {
        System.out.println("testGetHourOfDay");
        Date date = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Interval interval = DateUtil.Interval.HOUR_OF_DAY;
        int expResult = 23;
        int result = DateUtil.get(date, interval);
        assertEquals(expResult, result);
    }    
    @Test
    public void testGetMinute() throws ParseException {
        System.out.println("testGetMinute");
        Date date = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Interval interval = DateUtil.Interval.MINUTE;
        int expResult = 7;
        int result = DateUtil.get(date, interval);
        assertEquals(expResult, result);
    }   
    @Test
    public void testGetMonth() throws ParseException {
        System.out.println("testGetMonth");
        Date date = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Interval interval = DateUtil.Interval.MONTH;
        int expResult = 2; //El número de mes empieza en 0
        int result = DateUtil.get(date, interval);
        assertEquals(expResult, result);
    }       
    @Test
    public void testGetYear() throws ParseException {
        System.out.println("testGetYear");
        Date date = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Interval interval = DateUtil.Interval.YEAR;
        int expResult = 2024;
        int result = DateUtil.get(date, interval);
        assertEquals(expResult, result);
    }      

    @Test
    public void testGetDateInterval0() throws ParseException {
        System.out.println("getDateInterval0");
        Date upperDate = simpleDateFormat.parse("02/03/2024 23:07:08");
        Date lowerDate = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Duration expResult = new DateUtil.Duration(0, DateUtil.Interval.MINUTE);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);               
    }
    @Test
    public void testGetDateInterval20min() throws ParseException {
        System.out.println("getDateInterval20min");
        Date upperDate = simpleDateFormat.parse("02/03/2024 23:27:08");
        Date lowerDate = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Duration expResult = new DateUtil.Duration(20, DateUtil.Interval.MINUTE);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }   
    
    @Test
    public void testGetDateInterval2horas() throws ParseException {
        System.out.println("getDateInterval2horas");
        Date upperDate = simpleDateFormat.parse("01/01/2024 02:00:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(2, DateUtil.Interval.HOUR_OF_DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }  
    @Test
    public void testGetDateInterval2horasy59minutos() throws ParseException {
        System.out.println("testGetDateInterval2horasy59minutos");
        Date upperDate = simpleDateFormat.parse("01/01/2024 02:59:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(3, DateUtil.Interval.HOUR_OF_DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }
    @Test
    public void testGetDateInterval2horasy31minutos() throws ParseException {
        System.out.println("testGetDateInterval2horasy31minutos");
        Date upperDate = simpleDateFormat.parse("01/01/2024 02:31:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(3, DateUtil.Interval.HOUR_OF_DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    } 
    @Test
    public void testGetDateInterval2horasy30minutos() throws ParseException {
        System.out.println("testGetDateInterval2horasy30minutos");
        Date upperDate = simpleDateFormat.parse("01/01/2024 02:30:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(3, DateUtil.Interval.HOUR_OF_DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }  
    @Test
    public void testGetDateInterval2horasy29minutos() throws ParseException {
        System.out.println("testGetDateInterval2horasy29minutos");
        Date upperDate = simpleDateFormat.parse("01/01/2024 02:29:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(2, DateUtil.Interval.HOUR_OF_DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }      
    
    @Test
    public void testGetDateInterval3dias() throws ParseException {
        System.out.println("getDateInterval3dias");
        Date upperDate = simpleDateFormat.parse("03/03/2024 01:07:08");
        Date lowerDate = simpleDateFormat.parse("02/03/2024 23:07:08");
        DateUtil.Duration expResult = new DateUtil.Duration(2, DateUtil.Interval.HOUR_OF_DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }    
    @Test
    public void testGetDateInterval3diasy11horas() throws ParseException {
        System.out.println("testGetDateInterval3diasy11horas");
        Date upperDate = simpleDateFormat.parse("04/01/2024 11:00:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(3, DateUtil.Interval.DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    } 
    @Test
    public void testGetDateInterval3diasy12horas() throws ParseException {
        System.out.println("testGetDateInterval3diasy12horas");
        Date upperDate = simpleDateFormat.parse("04/01/2024 12:00:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(4, DateUtil.Interval.DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    } 
    @Test
    public void testGetDateInterval3diasy13horas() throws ParseException {
        System.out.println("testGetDateInterval3diasy13horas");
        Date upperDate = simpleDateFormat.parse("04/01/2024 13:00:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(4, DateUtil.Interval.DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }    
    @Test
    public void testGetDateInterval3diasy23horas() throws ParseException {
        System.out.println("testGetDateInterval3diasy23horas");
        Date upperDate = simpleDateFormat.parse("04/01/2024 23:00:00");
        Date lowerDate = simpleDateFormat.parse("01/01/2024 00:00:00");
        DateUtil.Duration expResult = new DateUtil.Duration(4, DateUtil.Interval.DAY);
        DateUtil.Duration result = DateUtil.getDateInterval(upperDate, lowerDate);
        assertEquals(expResult.interval, result.interval);
        assertEquals(expResult.value, result.value);        
    }  
    
    @Test
    public void testDurationToString0minutos()  {
        System.out.println("testDurationToString0minutos");
        String expResult = "0 minutos";
        String result = new DateUtil.Duration(0,DateUtil.Interval.MINUTE).toString();
        assertEquals(expResult, result);      
    } 
    @Test
    public void testDurationToString0horas()  {
        System.out.println("testDurationToString0horas");
        String expResult = "0 horas";
        String result = new DateUtil.Duration(0,DateUtil.Interval.HOUR_OF_DAY).toString();
        assertEquals(expResult, result);      
    }
    @Test
    public void testDurationToString0dias()  {
        System.out.println("testDurationToString0dias");
        String expResult = "0 dias";
        String result = new DateUtil.Duration(0,DateUtil.Interval.DAY).toString();
        assertEquals(expResult, result);      
    }     
    @Test
    public void testDurationToString1minuto()  {
        System.out.println("testDurationToString1minuto");
        String expResult = "1 minuto";
        String result = new DateUtil.Duration(1,DateUtil.Interval.MINUTE).toString();
        assertEquals(expResult, result);      
    } 
    @Test
    public void testDurationToString1hora()  {
        System.out.println("testDurationToString1hora");
        String expResult = "1 hora";
        String result = new DateUtil.Duration(1,DateUtil.Interval.HOUR_OF_DAY).toString();
        assertEquals(expResult, result);      
    }
    @Test
    public void testDurationToString1dia()  {
        System.out.println("testDurationToString1dia");
        String expResult = "1 dia";
        String result = new DateUtil.Duration(1,DateUtil.Interval.DAY).toString();
        assertEquals(expResult, result);      
    } 
    @Test
    public void testDurationToString7minutos()  {
        System.out.println("testDurationToString7minutos");
        String expResult = "7 minutos";
        String result = new DateUtil.Duration(7,DateUtil.Interval.MINUTE).toString();
        assertEquals(expResult, result);      
    } 
    @Test
    public void testDurationToString7horas()  {
        System.out.println("testDurationToString7horas");
        String expResult = "7 horas";
        String result = new DateUtil.Duration(7,DateUtil.Interval.HOUR_OF_DAY).toString();
        assertEquals(expResult, result);      
    }
    @Test
    public void testDurationToString7dias()  {
        System.out.println("testDurationToString7dias");
        String expResult = "7 dias";
        String result = new DateUtil.Duration(7,DateUtil.Interval.DAY).toString();
        assertEquals(expResult, result);      
    }      
    
    
}
