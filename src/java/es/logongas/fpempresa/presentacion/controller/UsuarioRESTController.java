/**
 * FPempresa Copyright (C) 2015 Lorenzo González
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
package es.logongas.fpempresa.presentacion.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.metadata.MetaData;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.security.authentication.Principal;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.controllers.AbstractRESTController;
import es.logongas.ix3.web.controllers.Command;
import es.logongas.ix3.web.controllers.CommandResult;
import es.logongas.ix3.web.json.JsonReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author logongas
 */
@Controller
public class UsuarioRESTController  extends AbstractRESTController {
    @Autowired
    private MetaDataFactory metaDataFactory;
    
    @Autowired
    private Conversion conversion;
    
    @Autowired
    private CRUDServiceFactory crudServiceFactory;     
    
    
    @RequestMapping(value = {"/Usuario/{idUsuario}/estadoUsuario/{estadoUsuario}"}, method = RequestMethod.PATCH,produces = "application/json")
    public void estadoUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,final @PathVariable("idUsuario") int idUsuario, final @PathVariable("estadoUsuario") EstadoUsuario estadoUsuario) {

        restMethod(httpServletRequest, httpServletResponse, null, new Command() {

            @Override
            public CommandResult run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map<String, Object> arguments) throws Exception, BusinessException {
                MetaData metaData = metaDataFactory.getMetaData("Usuario");
                if (metaData == null) {
                    throw new BusinessException("No existe la entidad 'Usuario'");
                }
                UsuarioCRUDService usuarioCrudService=(UsuarioCRUDService)crudServiceFactory.getService(metaData.getType());
               
                Usuario usuario=usuarioCrudService.read(idUsuario);
                usuario.setEstadoUsuario(estadoUsuario);
                usuarioCrudService.update(usuario);

                return new CommandResult(metaData.getType(), usuario);

            }
        });

    }
    
    
    @RequestMapping(value = {"/Usuario/{idUsuario}/updatePassword"}, method = RequestMethod.PATCH,produces = "application/json")
    public void updatePassword(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,final @PathVariable("idUsuario") int idUsuario,final @RequestBody String jsonIn) {

        restMethod(httpServletRequest, httpServletResponse, null, new Command() {

            @Override
            public CommandResult run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map<String, Object> arguments) throws Exception, BusinessException {
                MetaData metaData = metaDataFactory.getMetaData("Usuario");
                if (metaData == null) {
                    throw new BusinessException("No existe la entidad 'Usuario'");
                }
                UsuarioCRUDService usuarioCrudService=(UsuarioCRUDService)crudServiceFactory.getService(metaData.getType());
                JsonReader jsonReader = jsonFactory.getJsonReader(ChangePassword.class);
                ChangePassword changePassword = (ChangePassword) jsonReader.fromJson(jsonIn);
                Usuario usuario=usuarioCrudService.read(idUsuario);
                usuarioCrudService.updatePassword(usuario, changePassword.getCurrentPassword(), changePassword.getNewPassword());
                
                return null;

            }
        });

    }    
    
    
    private static class ChangePassword {
        private String currentPassword;
        private String newPassword;

        public ChangePassword() {
        }

        /**
         * @return the currentPassword
         */
        public String getCurrentPassword() {
            return currentPassword;
        }

        /**
         * @param currentPassword the currentPassword to set
         */
        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        /**
         * @return the newPassword
         */
        public String getNewPassword() {
            return newPassword;
        }

        /**
         * @param newPassword the newPassword to set
         */
        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
        
        
    }
    
}
