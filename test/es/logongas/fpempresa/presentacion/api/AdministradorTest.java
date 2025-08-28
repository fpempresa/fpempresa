/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.api;

import org.junit.Test;
import static com.jayway.restassured.RestAssured.*;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.service.populate.GeneradorDatosAleatorios;
import java.util.Map;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author logongas
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdministradorTest {

    static Map<String, String> cookies;
    static Centro centro;
    static Empresa empresa;

    @BeforeClass
    public static void setUpClass() {
        cookies = TestUtil.login("administrador@fpempresa.net", "administrador");
    }

    @Test
    public void test_01_EstadisticasAdministradorProhibidasSinLogin() {
        given().
                when().get("/api/administrador/Estadisticas/administrador").
                then().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void test_02_EstadisticasAdministrador() {
        given().
                cookies(cookies).
                when().
                get("/api/administrador/Estadisticas/administrador").
                then().
                statusCode(HttpStatus.SC_OK).
                body("titulosFPPorFamilia", not(nullValue())).
                body("ofertasPorFamilia", not(nullValue())).
                body("candidatosPorFamilia", not(nullValue())).
                body("numeroCandidatos", not(nullValue())).
                body("numeroTitulosFP", not(nullValue())).
                body("numeroOfertas", not(nullValue()));
    }

    @Test
    public void test_03_Centro() {
        Centro centro = GeneradorDatosAleatorios.createCentroAleatorio();

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Centro.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = centro;
        crudTestConfiguration.primaryKeyName = "idCentro";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);
    }

    @Test
    public void test_04_CentroProhibido() {
        Centro centro = GeneradorDatosAleatorios.createCentroAleatorio();

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Centro.class;
        crudTestConfiguration.success = false;
        crudTestConfiguration.httpStatus = HttpStatus.SC_FORBIDDEN;
        crudTestConfiguration.cookies = null;
        crudTestConfiguration.entity = centro;
        crudTestConfiguration.primaryKeyName = "idCentro";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);

    }

    @Test
    public void test_05_Empresa() {
        Empresa empresa = GeneradorDatosAleatorios.createEmpresaAleatoria(null);

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Empresa.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = empresa;
        crudTestConfiguration.primaryKeyName = "idEmpresa";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);

    }

    @Test
    public void test_06_EmpresaProhibido() {
        Empresa empresa = GeneradorDatosAleatorios.createEmpresaAleatoria(null);

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Empresa.class;
        crudTestConfiguration.success = false;
        crudTestConfiguration.httpStatus = HttpStatus.SC_FORBIDDEN;
        crudTestConfiguration.cookies = null;
        crudTestConfiguration.entity = empresa;
        crudTestConfiguration.primaryKeyName = "idEmpresa";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);
    }

    @Test
    public void test_07_UsuarioAdministrador() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.ADMINISTRADOR);

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Usuario.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = usuario;
        crudTestConfiguration.primaryKeyName = "idIdentity";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);

    }

    @Test
    public void test_08_UsuarioAdministradorProhibido() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.ADMINISTRADOR);

        TestUtil.testInsert("administrador", Usuario.class, false, HttpStatus.SC_FORBIDDEN, null, usuario, "idIdentity",null);
    }
    
    @Test
    public void test_081_insertar_centro() {
        centro = GeneradorDatosAleatorios.createCentroAleatorio();
        int idCentro=TestUtil.testInsert("administrador", Centro.class, true, 0, cookies, centro, "idCentro", null);
        centro.setIdCentro(idCentro);
    }    

    
    @Test
    public void test_09_UsuarioProfesor() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.CENTRO);
        usuario.setCentro(centro);

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Usuario.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = usuario;
        crudTestConfiguration.primaryKeyName = "idIdentity";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);
    }

    @Test
    public void test_091_insertar_empresa() {
        empresa=GeneradorDatosAleatorios.createEmpresaAleatoria(null);
        int idEmpresa=TestUtil.testInsert("administrador", Empresa.class, true, 0, cookies, empresa, "idEmpresa", null);
        empresa.setIdEmpresa(idEmpresa);
    }    
    
    @Test
    public void test_10_UsuarioEmpresa() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.EMPRESA);
        usuario.setEmpresa(empresa);

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Usuario.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = usuario;
        crudTestConfiguration.primaryKeyName = "idIdentity";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);
    }

    @Test
    public void test_11_UsuarioTitulado() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.TITULADO);

        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "administrador";
        crudTestConfiguration.entityClass = Usuario.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = usuario;
        crudTestConfiguration.primaryKeyName = "idIdentity";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = null;
        crudTestConfiguration.readNoContent = true;

        TestUtil.testCRUD(crudTestConfiguration);
    }

    @Test
    public void test_12_update_usuario() {
        Usuario usuario =new Usuario();
        GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.ADMINISTRADOR);
        usuario.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
        usuario.setLogin("administrador@fpempresa.net");
        usuario.setEmail("administrador@fpempresa.net");
        usuario.setNombre("Administrador");
        usuario.setApellidos(".");
        usuario.setIdIdentity(999999);
        usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);

        TestUtil.testUpdate("administrador", Usuario.class, true, 0, cookies, usuario, "idIdentity",null);
    }    
       
        
    
    @Test
    public void test_97_update_password_prohibido() {
        TestUtil.testUpdatePassword("administrador", false, HttpStatus.SC_FORBIDDEN, null, 30, "administrador");

    } 
    
    @Test
    public void test_98_update_password_contrasenya_erronea() {
        TestUtil.testUpdatePassword("administrador", false, HttpStatus.SC_BAD_REQUEST, cookies, 30, null);

    }     
    
    @Test
    public void test_99_update_password() {
        //TestUtil.testUpdatePassword("administrador", true, 0, cookies, 30, "administrador");
    }  
    
}
