/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import es.logongas.fpempresa.businessprocess.comun.usuario.UsuarioCRUDBusinessProcess;
import es.logongas.fpempresa.businessprocess.titulado.TituladoCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.json.JsonFactory;
import es.logongas.ix3.web.json.JsonReader;
import es.logongas.ix3.web.util.ControllerHelper;
import es.logongas.ix3.web.util.HttpResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GnommoStudios
 */
@Controller
public class TituladoController {

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

    @RequestMapping(value = {"/{path}/importar-csv"}, method = RequestMethod.POST, produces = "application/json")
    public void importarTituladosCSV(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("file") final MultipartFile multipartFile) {
        Usuario[] listaUsuarios;
        try (DataSession dataSession = dataSessionFactory.getDataSession()) {
            try {
            InputStream inputStream = multipartFile.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String textJson="";
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                textJson +=line;
            }
            bufferedReader.close();
            ObjectMapper mapper = new ObjectMapper();
            listaUsuarios = mapper.readValue(textJson, TypeFactory.defaultInstance().constructArrayType(Usuario.class));

        } catch (IOException ex) {
            throw new RuntimeException("Error al leer el archivo csv", ex);
        }
            Principal principal = controllerHelper.getPrincipal(httpServletRequest, httpServletResponse, dataSession);
           
            TituladoCRUDBusinessProcess tituladoCRUDBusinessProcess = (TituladoCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Titulado.class);
            tituladoCRUDBusinessProcess.importarTituladosCSV(new TituladoCRUDBusinessProcess.ImportarTituladosCSVArguments(principal, dataSession, listaUsuarios));
            UsuarioCRUDBusinessProcess usuarioCRUDBusinessProcess = (UsuarioCRUDBusinessProcess) crudBusinessProcessFactory.getBusinessProcess(Usuario.class);
            usuarioCRUDBusinessProcess.importarTituladosCSV(new UsuarioCRUDBusinessProcess.ImportarTituladosCSVArguments(principal, dataSession, listaUsuarios));
            controllerHelper.objectToHttpResponse(new HttpResult(null), httpServletRequest, httpServletResponse);
        } catch(Exception ex){
            controllerHelper.exceptionToHttpResponse(ex, httpServletResponse);
        }
    }
}
