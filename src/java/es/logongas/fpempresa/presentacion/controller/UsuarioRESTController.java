/**
 * FPempresa Copyright (C) 2015 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.businessprocess.comun.usuario.UsuarioCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.json.JsonFactory;
import es.logongas.ix3.web.util.HttpResult;
import es.logongas.ix3.web.util.MimeType;
import es.logongas.ix3.web.json.JsonReader;
import es.logongas.ix3.web.security.WebSessionSidStorage;
import es.logongas.ix3.web.util.ControllerHelper;
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
public class UsuarioRESTController {

    @Autowired
    private MetaDataFactory metaDataFactory;

    @Autowired
    private CRUDServiceFactory crudServiceFactory;
    @Autowired
    private CRUDBusinessProcessFactory crudBusinessProcessFactory;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private JsonFactory jsonFactory;

    @Autowired
    WebSessionSidStorage webSessionSidStorage;

    @RequestMapping(value = {"{path}/Usuario/{idUsuario}/estadoUsuario/{estadoUsuario}"}, method = RequestMethod.PATCH, produces = "application/json")
    public void updateEstadoUsuario(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @PathVariable("estadoUsuario") EstadoUsuario estadoUsuario) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            CRUDService<Usuario, Integer> usuarioCrudService = crudServiceFactory.getService(Usuario.class);
            Usuario usuario = usuarioCrudService.read(dataSession, idUsuario);

            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);

            usuario = usuarioCRUDBusinessProcess.updateEstadoUsuario(new UsuarioCRUDBusinessProcess.UpdateEstadoUsuarioArguments(principal, dataSession, usuario, estadoUsuario));

            controllerHelper.objectToHttpResponse(new HttpResult(usuario), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }

    }

    @RequestMapping(value = {"{path}/Usuario/{idUsuario}/centro/{idCentro}"}, method = RequestMethod.PATCH, produces = "application/json")
    public void updateCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @PathVariable("idCentro") int idCentro) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            CRUDService<Usuario, Integer> usuarioCrudService = crudServiceFactory.getService(Usuario.class);
            Usuario usuario = usuarioCrudService.read(dataSession, idUsuario);

            CRUDService<Centro, Integer> centroCrudService = crudServiceFactory.getService(Centro.class);
            Centro centro = centroCrudService.read(dataSession, idCentro);
            if (centro == null) {
                throw new RuntimeException("No existe el centro:" + idCentro);
            }

            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);

            usuario = usuarioCRUDBusinessProcess.updateCentro(new UsuarioCRUDBusinessProcess.UpdateCentroArguments(principal, dataSession, usuario, centro));

            controllerHelper.objectToHttpResponse(new HttpResult(usuario), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }

    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/updatePassword"}, method = RequestMethod.PATCH, produces = "application/json")
    public void updatePassword(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @RequestBody String jsonIn) throws BusinessException {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            JsonReader jsonReader = jsonFactory.getJsonReader(ChangePassword.class);
            ChangePassword changePassword = (ChangePassword) jsonReader.fromJson(jsonIn, dataSession);

            CRUDService<Usuario, Integer> usuarioCrudService = crudServiceFactory.getService(Usuario.class);
            Usuario usuario = usuarioCrudService.read(dataSession, idUsuario);
            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);
            usuarioCRUDBusinessProcess.updatePassword(new UsuarioCRUDBusinessProcess.UpdatePasswordArguments(principal, dataSession, usuario, changePassword.currentPassword, changePassword.newPassword));

            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }

    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/foto"}, method = RequestMethod.GET)
    public void getFoto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario) throws BusinessException {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            CRUDService<Usuario, Integer> usuarioCrudService = crudServiceFactory.getService(Usuario.class);
            Usuario usuario = usuarioCrudService.read(dataSession, idUsuario);

            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);

            byte[] foto = usuarioCRUDBusinessProcess.getFoto(new UsuarioCRUDBusinessProcess.GetFotoArguments(principal, dataSession, usuario));

            controllerHelper.objectToHttpResponse(new HttpResult(null, foto, 200, false, null, MimeType.OCTET_STREAM), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }

    @RequestMapping(value = {"/{path}/Usuario/{idUsuario}/foto"}, method = RequestMethod.POST, produces = "application/json")
    public void updateFoto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idUsuario") int idUsuario, @RequestParam("file") MultipartFile file) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            if (file.isEmpty()) {
                throw new BusinessException("No has subido ningún fichero");
            }

            byte[] foto = file.getBytes();
            CRUDService<Usuario, Integer> usuarioCrudService = crudServiceFactory.getService(Usuario.class);
            Usuario usuario = usuarioCrudService.read(dataSession, idUsuario);
            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);
            usuarioCRUDBusinessProcess.updateFoto(new UsuarioCRUDBusinessProcess.UpdateFotoArguments(principal, dataSession, usuario, foto));

            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }

    }

    @RequestMapping(value = {"/{path}/Usuario/enviarMailResetearContrasenya/{email:.+}"}, method = RequestMethod.POST)
    public void enviarMailResetearPassword(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("email") String email) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);
            usuarioCRUDBusinessProcess.enviarMailResetearContrasenya(new UsuarioCRUDBusinessProcess.EnviarMailResetearContrasenyaArguments(principal, dataSession, email));
            controllerHelper.objectToHttpResponse(new HttpResult(email), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }

    @RequestMapping(value = {"/{path}/Usuario/resetearContrasenya"}, method = RequestMethod.POST)
    public void resetearContrasenya(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonIn) {
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
            JsonReader jsonReader = jsonFactory.getJsonReader(ResetPassword.class);
            ResetPassword resetPassword = (ResetPassword) jsonReader.fromJson(jsonIn, dataSession);
            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);
            usuarioCRUDBusinessProcess.resetearContrasenya(new UsuarioCRUDBusinessProcess.ResetearContrasenyaArguments(principal, dataSession, resetPassword.claveResetearContrasenya, resetPassword.nuevaContrasenya));
            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }
    
    @RequestMapping(value = {"/{path}/Usuario/importador-csv"}, method = RequestMethod.POST, consumes = "text/csv")
    public void importadorUsuarios(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody String jsonIn) {
        List<CompositeRequirement> allRecords = compReqServ.getFullDataSet((String) session.getAttribute("currentProject"));
        CSVReader pepe = new CSVReader();
      return new CsvResponse(allRecords, "yourData.csv");
    }

    public static class ResetPassword {

        private String claveResetearContrasenya;
        private String nuevaContrasenya;

        public ResetPassword() {
        }

        public ResetPassword(String claveResetearContrasenya, String nuevaContrasenya) {
            this.claveResetearContrasenya = claveResetearContrasenya;
            this.nuevaContrasenya = nuevaContrasenya;
        }

        public String getClaveResetearContrasenya() {
            return claveResetearContrasenya;
        }

        public void setClaveResetearContrasenya(String claveResetearContrasenya) {
            this.claveResetearContrasenya = claveResetearContrasenya;
        }

        public String getNuevaContrasenya() {
            return nuevaContrasenya;
        }

        public void setNuevaContrasenya(String nuevaContrasenya) {
            this.nuevaContrasenya = nuevaContrasenya;
        }

    }

    public static class ChangePassword {

        private String currentPassword;
        private String newPassword;

        public ChangePassword() {
        }

        public ChangePassword(String currentPassword, String newPassword) {
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
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
