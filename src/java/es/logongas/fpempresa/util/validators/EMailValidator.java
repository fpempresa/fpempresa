/*
 * FPempresa Copyright (C) 2021 Lorenzo González
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
package es.logongas.fpempresa.util.validators;

import org.apache.commons.validator.routines.EmailValidator;




/**
 *
 * @author logongas
 */
public class EMailValidator {
    
    String eMail;
    

    public EMailValidator(String eMail) {
        this.eMail = eMail;
    }
    
    public boolean isValid() {
        return EMailValidator.isValid(this.eMail);
    }
    
    public static  boolean isValid(String eMail) {
        return EmailValidator.getInstance().isValid(eMail);
    }
    
}
