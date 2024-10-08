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
package es.logongas.fpempresa.presentacion.task;

import es.logongas.fpempresa.businessprocess.comun.usuario.UsuarioCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.security.authentication.AuthenticationManager;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author logongas
 */
@Component
public class NotificarUsuariosInactivosTask implements Runnable {
    private static final Logger logException = LogManager.getLogger(Exception.class);     
    
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private CRUDBusinessProcessFactory crudBusinessProcessFactory;
    @Autowired
    Notification notification;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    private static final int SID_PRINCIPAL_ADMINISTRADOR=30;    
    
    public void run() {        
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal=authenticationManager.getPrincipalBySID(SID_PRINCIPAL_ADMINISTRADOR, dataSession);
            
            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);
            usuarioCRUDBusinessProcess.notificarUsuariosInactivos(new UsuarioCRUDBusinessProcess.NotificarUsuariosInactivosArguments(principal, dataSession));
            
        } catch (Exception ex) {
            logException.error("No ha sido posible ejecutar la tarea NotificarUsuariosInactivosTask",ex);
            notification.exceptionToAdministrador("Falló la tarea:"+this.getClass().getSimpleName()+ "--"+ex.getLocalizedMessage(), "No se ha podido ejecutar la tarea " + this.getClass().getName() + " a las " + new Date(), ex);
        }
    }
 
}
