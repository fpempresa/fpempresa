/*
 * FPempresa Copyright (C) 2025 Lorenzo González
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
package es.logongas.fpempresa.presentacion.controller.endpoints;

import es.logongas.ix3.web.json.beanmapper.PropertyNameList;

/**
 *
 * @author logongas
 */
public class BeanMapperDefault {
    
    private static final String USUARIO_DELETE_PROPERTIES="acl,memberOf,captchaWord,claveResetearContrasenya,claveValidacionEmail,fecha,fechaClaveResetearContrasenya,fechaEnvioCorreoAvisoBorrarUsuario,fechaUltimoAcceso,fechaUltimoEnvioCorreoValidacionEmail,keyCaptcha,lockedUntil,numEnviosCorreoValidacionEmail,numFailedLogins,password,secretToken,validadoEmail,foto";
    
    public static PropertyNameList getPropertyNameListUsuarioDeleteProperties() {
        return new PropertyNameList(USUARIO_DELETE_PROPERTIES);
    }
    public static PropertyNameList getPropertyNameListUsuarioDeleteProperties(String prefix) {
        return new PropertyNameList(USUARIO_DELETE_PROPERTIES,prefix);
    }  
    
}
