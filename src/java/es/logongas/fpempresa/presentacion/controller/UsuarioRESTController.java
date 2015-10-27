/**
 * FPempresa Copyright (C) 2015 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.metadata.MetaData;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.controllers.AbstractRestController;
import es.logongas.ix3.web.controllers.Command;
import es.logongas.ix3.web.controllers.CommandResult;
import es.logongas.ix3.web.controllers.endpoint.EndPoint;
import es.logongas.ix3.web.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author logongas
 */
@Controller
public class UsuarioRESTController extends AbstractRestController {

    @Autowired
    private MetaDataFactory metaDataFactory;

    @Autowired
    private CRUDServiceFactory crudServiceFactory;

    @RequestMapping(value = {"{path}/Usuario/{idUsuario}/estadoUsuario/{estadoUsuario}"}, method = RequestMethod.PATCH, produces = "application/json")
    public void estadoUsuario(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final @PathVariable("idUsuario") int idUsuario, final @PathVariable("estadoUsuario") EstadoUsuario estadoUsuario) {

        restMethod(httpServletRequest, httpServletResponse, new Command() {

            @Override
            public CommandResult run(EndPoint endPoint) throws Exception, BusinessException {
                MetaData metaData = metaDataFactory.getMetaData("Usuario");
                if (metaData == null) {
                    throw new BusinessException("No existe la entidad 'Usuario'");
                }
                UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(metaData.getType());

                Usuario usuario = usuarioCrudService.read(idUsuario);
                usuario.setEstadoUsuario(estadoUsuario);
                usuarioCrudService.update(usuario);

                return new CommandResult(metaData.getType(), usuario);

            }
        });

    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/updatePassword"}, method = RequestMethod.PATCH, produces = "application/json")
    public void updatePassword(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final @PathVariable("idUsuario") int idUsuario, final @RequestBody String jsonIn) {

        restMethod(httpServletRequest, httpServletResponse, new Command() {

            @Override
            public CommandResult run(EndPoint endPoint) throws Exception, BusinessException {
                MetaData metaData = metaDataFactory.getMetaData("Usuario");
                if (metaData == null) {
                    throw new BusinessException("No existe la entidad 'Usuario'");
                }
                UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(metaData.getType());
                JsonReader jsonReader = jsonFactory.getJsonReader(ChangePassword.class);
                ChangePassword changePassword = (ChangePassword) jsonReader.fromJson(jsonIn);
                Usuario usuario = usuarioCrudService.read(idUsuario);
                usuarioCrudService.updatePassword(usuario, changePassword.getCurrentPassword(), changePassword.getNewPassword());

                return new CommandResult(null);

            }
        });

    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/foto"}, method = RequestMethod.GET)
    public void getFoto(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final @PathVariable("idUsuario") int idUsuario) {

        final UsuarioRESTController usuarioRESTController=this; //Esto se hace para poder usarlo desde la inner class "Command".
        
        restMethod(httpServletRequest, httpServletResponse, new Command() {

            @Override
            public CommandResult run(EndPoint endPoint) throws Exception, BusinessException {
                UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
                Usuario usuario = usuarioCrudService.read(idUsuario);

                usuarioRESTController.noCache(httpServletResponse);
                httpServletResponse.setContentType("application/octet-stream");
                httpServletResponse.getOutputStream().write(usuario.getFoto());

                return null;
            }
        });
    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/foto"}, method = RequestMethod.POST, produces = "application/json")
    public void updateFoto(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final @PathVariable("idUsuario") int idUsuario, final @RequestParam("file") MultipartFile file) {

        restMethod(httpServletRequest, httpServletResponse, new Command() {
            
            @Override
            public CommandResult run(EndPoint endPoint) throws Exception, BusinessException {
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();

                    UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
                    Usuario usuario = usuarioCrudService.read(idUsuario);

                    usuario.setFoto(bytes);

                    usuarioCrudService.update(usuario);

                    return new CommandResult(null);
                } else {
                    throw new RuntimeException("No has usbido ningún parámetro");
                }
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
