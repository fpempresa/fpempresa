/*
 * FPempresa Copyright (C) 2023 Lorenzo González
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
package es.logongas.fpempresa.util.validators.impl;

import es.logongas.fpempresa.util.validators.NotUpperCase;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author logongas
 */
public class NotUpperCaseImpl implements ConstraintValidator<NotUpperCase, String> {

    @Override
    public void initialize(NotUpperCase notUpperCase) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        if (value!=null) {
            
            if (value.toUpperCase().equals(value)) {
                return false;
            }
            
        }
        
        return true;
        
    }
    
}

