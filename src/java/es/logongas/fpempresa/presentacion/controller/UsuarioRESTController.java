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

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.metadata.MetaData;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.controllers.helper.AbstractRestController;
import es.logongas.ix3.web.controllers.command.Command;
import es.logongas.ix3.web.controllers.command.CommandResult;
import es.logongas.ix3.web.controllers.command.MimeType;
import es.logongas.ix3.web.json.JsonReader;
import es.logongas.ix3.web.security.WebSessionSidStorage;
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

    @Autowired
    WebSessionSidStorage webSessionSidStorage;

    @RequestMapping(value = {"{path}/Usuario/{idUsuario}/estadoUsuario/{estadoUsuario}"}, method = RequestMethod.PATCH, produces = "application/json")
    public void updateEstadoUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @PathVariable("estadoUsuario") EstadoUsuario estadoUsuario) {

        restMethod(httpServletRequest, httpServletResponse, "estadoUsuario", null, new Command() {

            public int idUsuario;
            public EstadoUsuario estadoUsuario;

            public Command inicialize(int idUsuario, EstadoUsuario estadoUsuario) {
                this.idUsuario = idUsuario;
                this.estadoUsuario = estadoUsuario;

                return this;
            }

            @Override
            public CommandResult run() throws Exception, BusinessException {
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
        }.inicialize(idUsuario, estadoUsuario));

    }

    @RequestMapping(value = {"{path}/Usuario/{idUsuario}/centro/{idCentro}"}, method = RequestMethod.PATCH, produces = "application/json")
    public void updateCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @PathVariable("idCentro") int idCentro) {

        restMethod(httpServletRequest, httpServletResponse, "updateCentro", null, new Command() {

            public int idUsuario;
            public int idCentro;

            public Command inicialize(int idUsuario, int idCentro) {
                this.idUsuario = idUsuario;
                this.idCentro = idCentro;

                return this;
            }

            @Override
            public CommandResult run() throws Exception, BusinessException {
                MetaData metaData = metaDataFactory.getMetaData("Usuario");
                if (metaData == null) {
                    throw new BusinessException("No existe la entidad 'Usuario'");
                }
                UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(metaData.getType());
                CRUDService<Centro, Integer> centroCrudService = (CRUDService<Centro, Integer>) crudServiceFactory.getService(Centro.class);

                Centro centro = centroCrudService.read(idCentro);
                if (centro == null) {
                    throw new RuntimeException("No existe el centro:" + idCentro);
                }

                Usuario usuario = usuarioCrudService.read(idUsuario);
                usuario.setCentro(centro);
                usuarioCrudService.update(usuario);

                return new CommandResult(metaData.getType(), usuario);

            }
        }.inicialize(idUsuario, idCentro));

    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/updatePassword"}, method = RequestMethod.PATCH, produces = "application/json")
    public void updatePassword(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @RequestBody String jsonIn) throws BusinessException {
        MetaData metaData = metaDataFactory.getMetaData("Usuario");
        if (metaData == null) {
            throw new BusinessException("No existe la entidad 'Usuario'");
        }
        UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(metaData.getType());
        JsonReader jsonReader = jsonFactory.getJsonReader(ChangePassword.class);
        ChangePassword changePassword = (ChangePassword) jsonReader.fromJson(jsonIn);
        Usuario usuario = usuarioCrudService.read(idUsuario);

        restMethod(httpServletRequest, httpServletResponse, "updatePassword", null, new Command() {

            public int idUsuario;
            public ChangePassword changePassword;
            public Usuario usuario;

            public Command inicialize(int idUsuario, ChangePassword changePassword, Usuario usuario) {
                this.idUsuario = idUsuario;
                this.changePassword = changePassword;
                this.usuario = usuario;

                return this;
            }

            @Override
            public CommandResult run() throws Exception, BusinessException {
                MetaData metaData = metaDataFactory.getMetaData("Usuario");
                if (metaData == null) {
                    throw new BusinessException("No existe la entidad 'Usuario'");
                }
                UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(metaData.getType());

                usuarioCrudService.updatePassword(usuario, changePassword.getCurrentPassword(), changePassword.getNewPassword());

                //Esto es necesario ya que puede que al cambiar el password se pierda la sesión. Así que aqui la volvemos a guardar
                //Realmente es pq la sesión depende del password.
                webSessionSidStorage.setSid(httpServletRequest, httpServletResponse, usuario.getSid());

                return new CommandResult();

            }
        }.inicialize(idUsuario, changePassword, usuario));

    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/foto"}, method = RequestMethod.GET)
    public void getFoto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario) throws BusinessException {

        UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
        Usuario usuario = usuarioCrudService.read(idUsuario);

        restMethod(httpServletRequest, httpServletResponse, "getFoto", null, new Command() {
            public int idUsuario;
            public Usuario usuario;

            public Command inicialize(int idUsuario, Usuario usuario) {
                this.idUsuario = idUsuario;
                this.usuario = usuario;
                return this;
            }

            @Override
            public CommandResult run() throws Exception, BusinessException {
                return new CommandResult(null, usuario.getFoto(), 200, false, null, MimeType.OCTET_STREAM);
            }
        }.inicialize(idUsuario, usuario));
    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/foto"}, method = RequestMethod.POST, produces = "application/json")
    public void updateFoto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @RequestParam("file") MultipartFile file) {

        restMethod(httpServletRequest, httpServletResponse, "updateFoto", null, new Command() {
            public int idUsuario;
            public MultipartFile file;

            public Command inicialize(int idUsuario, MultipartFile file) {
                this.idUsuario = idUsuario;
                this.file = file;

                return this;
            }

            @Override
            public CommandResult run() throws Exception, BusinessException {
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();

                    UsuarioCRUDService usuarioCrudService = (UsuarioCRUDService) crudServiceFactory.getService(Usuario.class);
                    Usuario usuario = usuarioCrudService.read(idUsuario);

                    usuario.setFoto(bytes);

                    usuarioCrudService.update(usuario);

                    return new CommandResult(null);
                } else {
                    throw new RuntimeException("No has subido ningún fichero");
                }
            }
        }.inicialize(idUsuario, file));
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
