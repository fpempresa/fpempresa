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
package es.logongas.fpempresa.util.validators;

import es.logongas.ix3.core.BusinessMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Valida si una contraseña cumple con la política de contraseñas.
 * @author logongas
 */
public class PasswordValidator {
    
    private static final String SPECIAL="ºª\\!\"|@·#$%&/()=?'¿¡<>,;.:-_'{}^`[*+]";
    private static final int MIN_SIZE=8;
    private static final int MAX_SIZE=30; 
    
    
    
    private final String password;
    private final List<BusinessMessage> businessMessages = new ArrayList<>();
    private final String propertyName;
    

    
    public PasswordValidator(String password) {
        this.password = password;
        this.propertyName=null;
        
        validate();
        
    }
    public PasswordValidator(String password,String propertyName) {
        this.password = password;
        this.propertyName = propertyName;
        
        validate();
        
    }    
    
    
    public boolean isValid() {
        return businessMessages.isEmpty();
    }
    
    public List<BusinessMessage> getBusinessMessages() {
        return businessMessages;
    }
    
    
    private void validate() {        
        
        if ((password==null) || password.trim().isEmpty()) {
            addBusinessMessage("No puede estar vacía");
            return;
        }
        
        if (password.length()<MIN_SIZE) {
            addBusinessMessage("La longitud mínima es de " + MIN_SIZE + " caracteres");
        } 
        
        if (password.length()>MAX_SIZE) {
            addBusinessMessage("La longitud máxima es de " + MAX_SIZE + " caracteres");
        }          
        
        if (!password.matches(".*[A-Z].*")) {
            addBusinessMessage("Debe contener alguna mayúscula");
        }
        
        if (!password.matches(".*[a-z].*")) {
            addBusinessMessage("Debe contener alguna minúscula");
        }         
        
        if (!password.matches(".*[0-9].*")) {
            addBusinessMessage("Debe contener algún número");
        }  
        
        if (!password.matches(getRegExpSpecial(SPECIAL))) {
            addBusinessMessage("Debe contener alguno de los siguientes carácteres: "+SPECIAL);
        }         

    }
    
    
    private void addBusinessMessage(String message) {
        if ((propertyName==null) || propertyName.trim().isEmpty()) {
            businessMessages.add(new BusinessMessage(message));
        } else {
            businessMessages.add(new BusinessMessage(propertyName, message));
        }
    }
    
    
    private  String getRegExpSpecial(String special) {
        StringBuilder sb=new StringBuilder(".*[");
        
        for(int i=0;i<special.length();i++) {
            sb.append("\\"+special.charAt(i));
        }
        
        sb.append("].*");
        
        return sb.toString();
    }
}
