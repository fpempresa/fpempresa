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
package es.logongas.fpempresa.service.notification.impl;

import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.ix3.web.util.ExceptionNotify;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Notificación de que ha habido una excepción
 * @author logongas
 */
public class ExceptionNotifyImpl implements ExceptionNotify {

    @Autowired
    Notification notification;
    
    @Override
    public void notify(String title, String body, Throwable throwable) {
        notification.exception(title, body, throwable);
    }
    
}
