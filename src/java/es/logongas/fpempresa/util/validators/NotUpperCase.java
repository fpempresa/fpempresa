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
package es.logongas.fpempresa.util.validators;

import es.logongas.fpempresa.util.validators.impl.NotUpperCaseImpl;
import es.logongas.ix3.core.annotations.Date;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author logongas
 */
@Documented
@Constraint(validatedBy = NotUpperCaseImpl.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NotUpperCase {

    String message() default "No puede estar en mayúsculas";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @Time} annotations on the same element.
     */
    @Target({ FIELD, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Date[] value();
    }
}
