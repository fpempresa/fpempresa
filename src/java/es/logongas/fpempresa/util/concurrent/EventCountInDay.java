/**
 *   FPempresa
 *   Copyright (C) 2024  Lorenzo González
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
package es.logongas.fpempresa.util.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class EventCountInDay    {

    private final int threshold;   
    private final ConcurrentHashMap<String, DayState> concurrentHashMap;

    public EventCountInDay(int threshold) {
        this.threshold = threshold;
        this.concurrentHashMap = new ConcurrentHashMap<>(); 
    }
    
    public boolean isSafe(Notifier eventCountInDayNotifier) {
        String key=getKey();
        DayState currentDayState=incCount(concurrentHashMap,key);
        int currentValue=currentDayState.atomicInteger.get();
        
        if (currentValue<this.threshold) {
            return true;
        } else {
            if (currentDayState.notified.compareAndSet(false, true)) {
                if (eventCountInDayNotifier!=null)  {
                    eventCountInDayNotifier.notify(this.threshold,currentValue);
                }
            }

            return false;
        }
    }

    
    private String getKey() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String key = dateFormat.format(new Date());
        
        return key;
    }
    
    private DayState incCount(ConcurrentHashMap<String, DayState> concurrentHashMap,String key) {
        DayState emptyDayState=new DayState();
        emptyDayState.atomicInteger=new AtomicInteger(0);
        emptyDayState.notified = new AtomicBoolean(false);
        
        
        concurrentHashMap.putIfAbsent(key, emptyDayState);
        
        DayState currentDayState=concurrentHashMap.get(key);
        
        currentDayState.atomicInteger.addAndGet(1);
        
        return currentDayState;

    }    

    
    private class DayState {
        AtomicInteger atomicInteger;
        AtomicBoolean notified;
        
        
    }
    
    public interface Notifier {
        void notify(int threshold,int currentValue);
    }

}
