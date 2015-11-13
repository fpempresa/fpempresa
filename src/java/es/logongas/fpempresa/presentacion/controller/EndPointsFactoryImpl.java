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
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.comun.geo.Municipio;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.educacion.Grado;
import es.logongas.fpempresa.modelo.educacion.LeyEducativa;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.modelo.titulado.ExperienciaLaboral;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Idioma;
import es.logongas.fpempresa.modelo.titulado.NivelIdioma;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.modelo.titulado.TituloIdioma;
import es.logongas.fpempresa.modelo.titulado.configuracion.Configuracion;
import es.logongas.fpempresa.modelo.titulado.configuracion.NotificacionOferta;
import es.logongas.ix3.web.controllers.endpoint.EndPoint;
import es.logongas.ix3.web.controllers.endpoint.EndPointsFactory;
import es.logongas.ix3.web.json.beanmapper.BeanMapper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author logongas
 */
public class EndPointsFactoryImpl implements EndPointsFactory {

    List<EndPoint> endPoints = new ArrayList<EndPoint>();

    public EndPointsFactoryImpl() {

        addEndPointsNegocio(endPoints, "/titulado");

        addEndPointsNegocio(endPoints, "/administrador");
        endPoints.add(EndPoint.createEndPoint("/administrador/Estadisticas/**", null, new BeanMapper(Estadisticas.class,null,"*")));
        endPoints.add(EndPoint.createEndPoint("/administrador/$populate/**", null, null));
        
        addEndPointsNegocio(endPoints, "/centro");
        endPoints.add(EndPoint.createEndPoint("/centro/Estadisticas/**", null, new BeanMapper(Estadisticas.class,null,"*")));
        
        addEndPointsNegocio(endPoints, "/empresa");
        endPoints.add(EndPoint.createEndPoint("/empresa/Estadisticas/**", null, new BeanMapper(Estadisticas.class,null,"*")));
        
        addEndPointsNegocio(endPoints, "/site");

        //ix3
        endPoints.add(EndPoint.createEndPoint("/$echo/**", null, null));
        endPoints.add(EndPoint.createEndPoint("/session", null, new BeanMapper(Usuario.class, "foto,claveValidacionEmail,password,acl,memberOf,validadoEmail>,tipoUsuario>", null)));

    }

    @Override
    public List<EndPoint> getEndPoints() {
        return endPoints;
    }

    private void addEndPointsNegocio(List<EndPoint> endPoints, String path) {
        //Centro
        endPoints.add(EndPoint.createEndPointCrud(path, Centro.class));
        endPoints.add(EndPoint.createEndPointCrud(path, CertificadoTitulo.class));

        //comun
        endPoints.add(EndPoint.createEndPointCrud(path, Municipio.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Provincia.class));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Usuario.class, "foto,claveValidacionEmail,password,acl,memberOf,validadoEmail>,tipoUsuario>", null)));
        endPoints.add(EndPoint.createEndPoint(path + "/Usuario", "POST", new BeanMapper(Usuario.class, "foto,claveValidacionEmail,<password,acl,memberOf,validadoEmail>", null)));

        //educacion
        endPoints.add(EndPoint.createEndPointCrud(path, Ciclo.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Familia.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Grado.class));
        endPoints.add(EndPoint.createEndPointCrud(path, LeyEducativa.class));

        //empresa
        endPoints.add(EndPoint.createEndPointCrud(path, Candidato.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Empresa.class));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Oferta.class, null, "ciclos")));

        //administrador
        endPoints.add(EndPoint.createEndPointCrud(path, Configuracion.class));
        endPoints.add(EndPoint.createEndPointCrud(path, NotificacionOferta.class));
        endPoints.add(EndPoint.createEndPointCrud(path, ExperienciaLaboral.class));
        endPoints.add(EndPoint.createEndPointCrud(path, FormacionAcademica.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Idioma.class));
        endPoints.add(EndPoint.createEndPointCrud(path, NivelIdioma.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Titulado.class));
        endPoints.add(EndPoint.createEndPointCrud(path, TituloIdioma.class));
    }

}
