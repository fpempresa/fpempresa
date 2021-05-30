/*
 * FPempresa Copyright (C) 2021 Lorenzo González
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

import es.logongas.fpempresa.businessprocess.centro.CertificadoBusinessProcess;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoAnyo;
import es.logongas.fpempresa.modelo.centro.CertificadoCiclo;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import es.logongas.ix3.web.util.exception.ExceptionHelper;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class CentroController {

    private static final Logger log = LogManager.getLogger(CentroController.class);

    @Autowired
    CRUDServiceFactory crudServiceFactory;
    @Autowired
    private CertificadoBusinessProcess certificadoBusinessProcess;
    @Autowired
    private DataSessionFactory dataSessionFactory;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private ExceptionHelper exceptionHelper;
    @Autowired
    private Conversion conversion;

    @RequestMapping(value = {"/{path}/Centro/{idCentro}/Certificado/Anyo"}, method = RequestMethod.GET, produces = "application/json")
    public void getCertificadosAnyoCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCentro") int idCentro) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            Centro centro = crudServiceFactory.getService(Centro.class).read(dataSession, idCentro);

            List<CertificadoAnyo> certificadosAnyo = certificadoBusinessProcess.getCertificadosAnyoCentro(new CertificadoBusinessProcess.GetCertificadosAnyoCentroArguments(principal, dataSession, centro));
            controllerHelper.objectToHttpResponse(new HttpResult(certificadosAnyo), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }

    }

    @RequestMapping(value = {"/{path}/Centro/{idCentro}/Certificado/Anyo/{anyo}"}, method = RequestMethod.GET, produces = "application/json")
    public void getCertificadosCicloCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCentro") int idCentro, @PathVariable("anyo") int anyo) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            Centro centro = crudServiceFactory.getService(Centro.class).read(dataSession, idCentro);

            List<CertificadoCiclo> certificadosCiclo = certificadoBusinessProcess.getCertificadosCicloCentro(new CertificadoBusinessProcess.GetCertificadosCicloCentroArguments(principal, dataSession, centro, anyo));
            controllerHelper.objectToHttpResponse(new HttpResult(certificadosCiclo), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }

    }

    @RequestMapping(value = {"/{path}/Centro/{idCentro}/Certificado/Anyo/{anyo}/Ciclo/{idCiclo}"}, method = RequestMethod.GET, produces = "application/json")
    public void getCertificadosTituloCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCentro") int idCentro, @PathVariable("anyo") int anyo, @PathVariable("idCiclo") int idCiclo) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            Centro centro = crudServiceFactory.getService(Centro.class).read(dataSession, idCentro);
            Ciclo ciclo = crudServiceFactory.getService(Ciclo.class).read(dataSession, idCiclo);

            List<CertificadoTitulo> certificadosTitulo = certificadoBusinessProcess.getCertificadosTituloCentro(new CertificadoBusinessProcess.GetCertificadosTituloCentroArguments(principal, dataSession, centro, anyo, ciclo));
            controllerHelper.objectToHttpResponse(new HttpResult(certificadosTitulo), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }

    }
    
    @RequestMapping(value = {"/{path}/Centro/{idCentro}/Certificado/Anyo/{anyo}/Ciclo/{idCiclo}/identificacion/{tipoDocumento}/{numeroDocumento}/{certificadoTitulo}"}, method = RequestMethod.PATCH)
    public void certificarTituloCentro(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("idCentro") int idCentro, @PathVariable("anyo") int anyo, @PathVariable("idCiclo") int idCiclo,@PathVariable("tipoDocumento") TipoDocumento tipoDocumento, @PathVariable("numeroDocumento") String numeroDocumento,@PathVariable("certificadoTitulo") String sCertificadoTitulo) {

        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);

            Centro centro = crudServiceFactory.getService(Centro.class).read(dataSession, idCentro);
            Ciclo ciclo = crudServiceFactory.getService(Ciclo.class).read(dataSession, idCiclo);
            boolean certificadoTitulo=(Boolean)conversion.convertFromString(sCertificadoTitulo, Boolean.class);
            
            certificadoBusinessProcess.certificarTituloCentro(new CertificadoBusinessProcess.CertificarTituloCentroArguments(principal, dataSession, centro, anyo, ciclo,tipoDocumento,numeroDocumento,certificadoTitulo));
            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            exceptionHelper.exceptionToHttpResponse(ex, httpServletRequest, httpServletResponse);
        }
    }
}
