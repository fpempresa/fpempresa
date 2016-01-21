/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.api;

import org.junit.Test;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.fpempresa.modelo.comun.geo.Municipio;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.presentacion.controller.UsuarioRESTController;
import es.logongas.fpempresa.service.populate.GeneradorDatosAleatorios;
import es.logongas.ix3.util.ReflectionUtil;
import java.util.Map;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;

/**
 *
 * @author logongas
 */
public class AdministradorTest {

    static Map<String, String> cookies;

    @BeforeClass
    public static void setUp() {
        Response response = given().contentType("application/json").accept("application/json").queryParam("login", "administrador@fpempresa.net").queryParam("password", "administrador").when().post(GenericTestCRUD.getApiBase() + "/api/session");
        cookies = response.getCookies();
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testEstadisticasAdministradorProhibidasSinLogin() {
        given().
                when().get(GenericTestCRUD.getApiBase() + "/api/administrador/Estadisticas/administrador").
                then().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void testEstadisticasAdministrador() {
        given().
                cookies(cookies).
                when().
                get(GenericTestCRUD.getApiBase() + "/api/administrador/Estadisticas/administrador").
                then().
                statusCode(HttpStatus.SC_OK).
                body("tituladosPorFamilia", not(nullValue())).
                body("ofertasPorFamilia", not(nullValue())).
                body("candidatosPorFamilia", not(nullValue())).
                body("numeroCandidatos", not(nullValue())).
                body("numeroTitulados", not(nullValue())).
                body("numeroOfertas", not(nullValue()));
    }

    @Test
    public void testCentro() {
        Centro centro = GeneradorDatosAleatorios.createCentroAleatorio();
        GenericTestCRUD.testCRUD(Centro.class, true, 0, cookies, centro, "idCentro");
    }

    @Test
    public void testCentroProhibido() {
        Centro centro = GeneradorDatosAleatorios.createCentroAleatorio();
        GenericTestCRUD.testCRUD(Centro.class, false, HttpStatus.SC_FORBIDDEN, null, centro, "idCentro");
    }

    @Test
    public void testEmpresa() {
        Empresa empresa = GeneradorDatosAleatorios.createEmpresaAleatoria(null);
        GenericTestCRUD.testCRUD(Empresa.class, true, 0, cookies, empresa, "idEmpresa");
    }

    @Test
    public void testEmpresaProhibido() {
        Empresa empresa = GeneradorDatosAleatorios.createEmpresaAleatoria(null);
        GenericTestCRUD.testCRUD(Empresa.class, false, HttpStatus.SC_FORBIDDEN, null, empresa, "idEmpresa");
    }

    @Test
    public void testUsuarioAdministrador() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.ADMINISTRADOR);

        GenericTestCRUD.testCRUD(Usuario.class, true, 0, cookies, usuario, "idIdentity");
    }

    @Test
    public void testUsuarioAdministradorProhibido() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.ADMINISTRADOR);

        GenericTestCRUD.testCRUD(Usuario.class, false, HttpStatus.SC_FORBIDDEN, null, usuario, "idIdentity");
    }

    @Test
    public void testUsuarioProfesor() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.CENTRO);
        usuario.setCentro(GeneradorDatosAleatorios.createCentroAleatorio());

        GenericTestCRUD.testCRUD(Usuario.class, true, 0, cookies, usuario, "idIdentity");
    }

    @Test
    public void testUsuarioEmpresa() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.EMPRESA);
        usuario.setEmpresa(GeneradorDatosAleatorios.createEmpresaAleatoria(null));
        GenericTestCRUD.testCRUD(Usuario.class, true, 0, cookies, usuario, "idIdentity");
    }

    @Test
    public void testUsuarioTitulado() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.TITULADO);

        GenericTestCRUD.testCRUD(Usuario.class, true, 0, cookies, usuario, "idIdentity");
    }

    @Test
    public void testUpdatePassword() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.EMPRESA);
        String currentPassword = usuario.getPassword();
        int idIdentity = GenericTestCRUD.testInsert(Usuario.class, true, 0, cookies, usuario, "idIdentity");
        
        
        given().
                contentType("application/json").
                cookies(cookies).
                body(new UsuarioRESTController.ChangePassword(currentPassword, currentPassword + "aaa")).
                when().
                patch(GenericTestCRUD.getApiBase() + "/api/administrador/Usuario/{id}/updatePassword", idIdentity)
                .then().statusCode(HttpStatus.SC_OK);
        
        given().
                contentType("application/json").
                body(new UsuarioRESTController.ChangePassword(currentPassword, currentPassword + "aaa")).
                when().
                patch(GenericTestCRUD.getApiBase() + "/api/administrador/Usuario/{id}/updatePassword", idIdentity)
                .then().statusCode(HttpStatus.SC_FORBIDDEN);        
        
        GenericTestCRUD.testDelete(Usuario.class, true, 0, cookies, idIdentity);

    }
}
