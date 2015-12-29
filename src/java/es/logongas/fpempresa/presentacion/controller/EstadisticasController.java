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
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.service.estadisticas.EstadisticasService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.web.controllers.helper.AbstractRestController;
import es.logongas.ix3.web.controllers.command.Command;
import es.logongas.ix3.web.controllers.command.CommandResult;
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
public class EstadisticasController extends AbstractRestController {

    private static final Log log = LogFactory.getLog(EstadisticasController.class);


    @Autowired 
    DAOFactory daoFactory;
    
    @Autowired
    private EstadisticasService estadisticasService;
    
    @RequestMapping(value = {"/{path}/Estadisticas/centro/{idCentro}"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasCentro(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, @PathVariable("idCentro") int idCentro) {
        
        restMethod(httpServletRequest, httpServletResponse,"getEstadisticasCentro",null, new Command() {

            public int idCentro;
            
            public Command inicialize(int idCentro) {
                this.idCentro=idCentro;
                return this;
            }
            
            @Override
            public CommandResult run() throws Exception, BusinessException {
                Centro centro=daoFactory.getDAO(Centro.class).read(idCentro);
                
                if (centro==null) {
                    throw new BusinessException("No existe el centro");
                }                
                
                Estadisticas estadisticas=estadisticasService.getEstadisticas(centro);
                
                return new CommandResult(estadisticas);
            }
        }.inicialize(idCentro));
    }
    @RequestMapping(value = {"/{path}/Estadisticas/empresa/{idEmpresa}"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasEmpresa(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, @PathVariable("idEmpresa") int idEmpresa) {

        restMethod(httpServletRequest, httpServletResponse,"getEstadisticasEmpresa",null, new Command() {
            public int idEmpresa;
            
            public Command inicialize(int idEmpresa) {
                this.idEmpresa=idEmpresa;
                return this;
            }
            @Override
            public CommandResult run() throws Exception, BusinessException {
                
                Empresa empresa=daoFactory.getDAO(Empresa.class).read(idEmpresa);
                
                if (empresa==null) {
                    throw new BusinessException("No existe la empresa");
                }                
                
                Estadisticas estadisticas=estadisticasService.getEstadisticas(empresa);
                
                return new CommandResult(estadisticas);
            }
        }.inicialize(idEmpresa));
    }  
    
    @RequestMapping(value = {"/{path}/Estadisticas/administrador"}, method = RequestMethod.GET, produces = "application/json")
    public void getEstadisticasAdministrador(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {

        restMethod(httpServletRequest, httpServletResponse,"getEstadisticasAdministrador",null, new Command() {

            @Override
            public CommandResult run() throws Exception, BusinessException {
                
                Estadisticas estadisticas=estadisticasService.getEstadisticas();
                
                return new CommandResult(estadisticas);
            }
        });
    }     
}
