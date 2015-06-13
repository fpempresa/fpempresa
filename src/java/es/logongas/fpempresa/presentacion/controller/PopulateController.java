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

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.populate.PopulateService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.controllers.AbstractRESTController;
import es.logongas.ix3.web.controllers.Command;
import es.logongas.ix3.web.controllers.CommandResult;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author logongas
 */
@Controller
public class PopulateController extends AbstractRESTController {

    private static final Log log = LogFactory.getLog(PopulateController.class);

    @Autowired
    private PopulateService populateService;
    @Autowired
    private CRUDServiceFactory crudServiceFactory;

    @RequestMapping(value = {"/Centro/$populate/{numCentros}"}, method = RequestMethod.GET, produces = "application/json")
    public void crearCentrosAleatorios(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, final @PathVariable("numCentros") int numCentros) {

        restMethod(httpServletRequest, httpServletResponse, null, new Command() {

            @Override
            public CommandResult run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map<String, Object> arguments) throws Exception, BusinessException {

                CRUDService<Centro, Integer> centroService = crudServiceFactory.getService(Centro.class);

                int num = 0;
                for (int i = 0; i < numCentros; i++) {
                    Centro centro = populateService.createCentroAleatorio();

                    centroService.insert(centro);
                    num++;

                }

                Map<String, Integer> result = new HashMap<String, Integer>();
                result.put("num", num);
                return new CommandResult(num);
            }
        });
    }

    @RequestMapping(value = {"/Empresa/$populate/{numEmpresas}"}, method = RequestMethod.GET, produces = "application/json")
    public void crearEmpresasAleatorias(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, final @PathVariable("numEmpresas") int numEmpresas) {

        restMethod(httpServletRequest, httpServletResponse, null, new Command() {

            @Override
            public CommandResult run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map<String, Object> arguments) throws Exception, BusinessException {

                CRUDService<Empresa, Integer> empresaService = crudServiceFactory.getService(Empresa.class);

                int num = 0;
                for (int i = 0; i < numEmpresas; i++) {
                    Empresa empresa = populateService.createEmpresaAleatoria();

                    empresaService.insert(empresa);
                    num++;

                }

                Map<String, Integer> result = new HashMap<String, Integer>();
                result.put("num", num);
                return new CommandResult(result);
            }
        });
    }

    @RequestMapping(value = {"/Usuario/$populate/{numUsuarios}"}, method = RequestMethod.GET, produces = "application/json")
    public void crearUsuariosAleatorios(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, final @PathVariable("numUsuarios") int numUsuarios) {

        restMethod(httpServletRequest, httpServletResponse, null, new Command() {

            @Override
            public CommandResult run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map<String, Object> arguments) throws Exception, BusinessException {

                CRUDService<Usuario, Integer> usuarioService = crudServiceFactory.getService(Usuario.class);
                CRUDService<Titulado, Integer> tituladoService = crudServiceFactory.getService(Titulado.class);

                int num = 0;
                for (int i = 0; i < numUsuarios; i++) {
                    Usuario usuario = populateService.createUsuarioAleatorio();

                    if (usuario.getTipoUsuario()==TipoUsuario.TITULADO) {
                        tituladoService.insert(usuario.getTitulado());
                    }
                    usuarioService.insert(usuario);
                    num++;
                }

                Map<String, Integer> result = new HashMap<String, Integer>();
                result.put("num", num);
                return new CommandResult(result);
            }
        });
    }    
    
}
