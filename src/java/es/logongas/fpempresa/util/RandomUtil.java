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

import java.util.Random;

/**
 *
 * @author logongas
 */
public class RandomUtil {
    
    private static final String CHARS_PASSWORS_MAYUSCULAS="QAZXSWEDCVFRTGBNHYUJMKIOLP";
    private static final String CHARS_PASSWORS_MINUSCULAS="qazxswedcvfrtgbnhyujmkiolp";
    private static final String CHARS_PASSWORS_NUMEROS="1234567890";
    private static final String CHARS_PASSWORS_ESPACIALES="!$%&/()+";
    private static final String[] CHARS_PASSWORS=new String[]{CHARS_PASSWORS_MAYUSCULAS,CHARS_PASSWORS_MINUSCULAS,CHARS_PASSWORS_NUMEROS,CHARS_PASSWORS_ESPACIALES};
    
    public static String createRandomPaswword(int length) {
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        
        
        if (length<CHARS_PASSWORS.length) {
            throw new RuntimeException("El tamaño mínimo debe ser 4");
        }
        
        int seed=random.nextInt(CHARS_PASSWORS.length);
        for(int i=0;i<length;i++) {
            int pos=(seed+i) % CHARS_PASSWORS.length;
            
            sb.append(getRandomChar(random,CHARS_PASSWORS[pos]));
            
        }
        
        return sb.toString();
    }
    
    private static char getRandomChar(Random random,String s) {
        return s.charAt(random.nextInt(s.length()));
    }
    
    
}
