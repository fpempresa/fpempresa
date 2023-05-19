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

import es.logongas.fpempresa.modelo.captcha.Captcha;
import es.logongas.fpempresa.modelo.centro.Centro;
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
import es.logongas.fpempresa.modelo.estadisticas.FamiliaOfertasEstadistica;
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

    List<EndPoint> endPoints;

    public EndPointsFactoryImpl() {

        endPoints = new ArrayList<EndPoint>();

        addIx3EndPoints(endPoints);

        addCommonEndPoints(endPoints, "/site");
        addSiteEndPoints(endPoints, "/site");

        addCommonEndPoints(endPoints, "/administrador");
        addAdministradorEndPoints(endPoints, "/administrador");

        addCommonEndPoints(endPoints, "/titulado");
        addTituladoEndPoints(endPoints, "/titulado");

        addCommonEndPoints(endPoints, "/centro");
        addCentroEndPoints(endPoints, "/centro");

        addCommonEndPoints(endPoints, "/empresa");
        addEmpresaEndPoints(endPoints, "/empresa");

    }

    @Override
    public List<EndPoint> getEndPoints() {
        return endPoints;
    }

    private void addIx3EndPoints(List<EndPoint> endPoints) {
        endPoints.add(EndPoint.createEndPoint("/$echo/**", null, null));
        endPoints.add(EndPoint.createEndPoint("/$log/**", null, null));
        endPoints.add(EndPoint.createEndPoint("/session", null, new BeanMapper(Usuario.class, "foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));
    }

    private void addCommonEndPoints(List<EndPoint> endPoints, String path) {

        //comun        
        endPoints.add(EndPoint.createEndPointCrud(path, Municipio.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Provincia.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Captcha.class));

        //educacion
        endPoints.add(EndPoint.createEndPointCrud(path, Ciclo.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Familia.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Grado.class));
        endPoints.add(EndPoint.createEndPointCrud(path, LeyEducativa.class));
        
        

    }

    private void addSiteEndPoints(List<EndPoint> endPoints, String path) {
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Usuario.class, "foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));
        endPoints.add(EndPoint.createEndPoint(path + "/Usuario", "POST", new BeanMapper(Usuario.class, "foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,<password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));
        endPoints.add(EndPoint.createEndPoint(path + "/Estadisticas/familiasOfertas", "GET", new BeanMapper(FamiliaOfertasEstadistica.class, null, "*")));
        endPoints.add(EndPoint.createEndPoint(path + "/Estadisticas/**", "GET", new BeanMapper(Estadisticas.class, null, "*")));

        //endPoints.add(EndPoint.createEndPoint(path + "/Usuario/olvidoPassword/*", "POST", null));

    }

    private void addAdministradorEndPoints(List<EndPoint> endPoints, String path) {
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Usuario.class, "foto,claveValidacionEmail>,secretToken,password,acl,memberOf", null)));
        endPoints.add(EndPoint.createEndPoint(path + "/Usuario", "POST", new BeanMapper(Usuario.class, "foto,claveValidacionEmail,secretToken,<password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));

        endPoints.add(EndPoint.createEndPoint(path + "/Estadisticas/**", "GET", new BeanMapper(Estadisticas.class, null, "*")));

        endPoints.add(EndPoint.createEndPointCrud(path, Empresa.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Candidato.class));
        endPoints.add(EndPoint.createEndPointCrud(path, Titulado.class));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Oferta.class, null, "ciclos")));

        //Centro
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Centro.class)));
        
        endPoints.add(EndPoint.createEndPoint(path + "/download/**", "GET", null));
   
    }

    private void addTituladoEndPoints(List<EndPoint> endPoints, String path) {
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Usuario.class, "keyCaptcha,captchaWord,foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));

        //Centro
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Centro.class, "contacto", null)));

        //empresa
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Empresa.class, "numOfertasPublicadas,contacto,centro.contacto", null)));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Oferta.class, "secretToken,empresa.contacto,empresa.centro.contacto,empresa.numOfertasPublicadas", "ciclos")));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Candidato.class, "usuario.titulado.direccion.datosDireccion,borrado,rechazado,oferta.secretToken,oferta.empresa.contacto,oferta.empresa.centro.contacto,oferta.empresa.numOfertasPublicadas", null)));

        //titulado
        endPoints.add(EndPoint.createEndPointCrud(path, Configuracion.class));
        endPoints.add(EndPoint.createEndPointCrud(path, NotificacionOferta.class));
        endPoints.add(EndPoint.createEndPointCrud(path, ExperienciaLaboral.class));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(FormacionAcademica.class, "certificadoTitulo>", null)));
        endPoints.add(EndPoint.createEndPointCrud(path, Idioma.class));
        endPoints.add(EndPoint.createEndPointCrud(path, NivelIdioma.class));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Titulado.class, null, "configuracion.notificacionOferta.provincias>")));
        endPoints.add(EndPoint.createEndPointCrud(path, TituloIdioma.class));

    }

    private void addCentroEndPoints(List<EndPoint> endPoints, String path) {
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Usuario.class, "keyCaptcha,captchaWord,foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));
        endPoints.add(EndPoint.createEndPoint(path + "/Usuario", "POST", new BeanMapper(Usuario.class, "foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,<password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));

        endPoints.add(EndPoint.createEndPoint(path + "/Estadisticas/**", "GET", new BeanMapper(Estadisticas.class, null, "*")));

        endPoints.add(EndPoint.createEndPointCrud(path, Centro.class));
        endPoints.add(EndPoint.createEndPoint(path + "/Centro", "GET", new BeanMapper(Centro.class, "contacto", null)));

        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Empresa.class,"numOfertasPublicadas",null)));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Oferta.class, "secretToken,empresa.numOfertasPublicadas", "ciclos")));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Candidato.class, "usuario.titulado.direccion.datosDireccion,oferta.secretToken,borrado,rechazado,usuario.foto,usuario.claveValidacionEmail,usuario.secretToken,usuario.lockedUntil,usuario.numFailedLogins,usuario.password,usuario.acl,usuario.memberOf,usuario.validadoEmail,usuario.tipoUsuario,usuario.titulado.configuracion,usuario.fechaUltimoAcceso,usuario.fechaEnvioCorreoAvisoBorrarUsuario,usuario.centro,usuario.email,usuario.login,usuario.titulado,usuario.fecha,usuario.claveResetearContrasenya,usuario.fechaClaveResetearContrasenya,usuario.idIdentity,usuario.empresa,usuario.estadoUsuario,usuario.name", "usuario.nombre,usuario.apellidos,oferta.empresa.numOfertasPublicadas")));

        endPoints.add(EndPoint.createEndPointCrud(path, Titulado.class));

        endPoints.add(EndPoint.createEndPoint(path + "/download/**", "GET", null));         
    }

    private void addEmpresaEndPoints(List<EndPoint> endPoints, String path) {
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Usuario.class, "keyCaptcha,captchaWord,captchaWord,foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));
        endPoints.add(EndPoint.createEndPoint(path + "/Usuario", "POST", new BeanMapper(Usuario.class, "foto,claveValidacionEmail,secretToken,lockedUntil,numFailedLogins,<password,acl,memberOf,validadoEmail>,fechaUltimoAcceso>,fechaEnvioCorreoAvisoBorrarUsuario>", null)));

        endPoints.add(EndPoint.createEndPoint(path + "/Estadisticas/**", "GET", new BeanMapper(Estadisticas.class, null, "*")));

        endPoints.add(EndPoint.createEndPointCrud(path, Centro.class));

        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Empresa.class,"numOfertasPublicadas",null)));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Oferta.class, "secretToken,empresa.numOfertasPublicadas", "ciclos")));
        endPoints.add(EndPoint.createEndPointCrud(path, new BeanMapper(Candidato.class, "usuario.titulado.direccion.datosDireccion,borrado,oferta.secretToken,usuario.foto,usuario.claveValidacionEmail,usuario.secretToken,usuario.lockedUntil,usuario.numFailedLogins,usuario.password,usuario.acl,usuario.memberOf,usuario.validadoEmail>,usuario.tipoUsuario>,usuario.titulado.configuracion,usuario.fechaUltimoAcceso>,usuario.fechaEnvioCorreoAvisoBorrarUsuario>,oferta.empresa.numOfertasPublicadas", null)));

        endPoints.add(EndPoint.createEndPointCrud(path, Titulado.class));

    }

}
