/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.api;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import org.junit.Test;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.populate.GeneradorDatosAleatorios;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
/**
 *
 * @author logongas
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Empresario {
    static Map<String, String> cookiesAdministrador;
    static Map<String, String> cookiesEmpresario;
    static Map<String, String> cookiesTitulado;
    static Usuario usuarioEmpresario;
    static Empresa empresa;
    static Oferta oferta;
    static Usuario usuarioTitulado;
    static Candidato candidato;
    static FormacionAcademica formacionAcademica;

    @BeforeClass
    public static void setUpClass() {       
        cookiesAdministrador=TestUtil.login("administrador@fpempresa.net", "administrador");

    }
    @AfterClass
    public static void tearDownClass() {
        //cookies=TestUtil.login(usuario.getLogin(), usuario.getPassword());        
        //TestUtil.testDelete("titulado",Usuario.class, false, HttpStatus.SC_FORBIDDEN, null, usuario.getIdIdentity());
        //TestUtil.testDelete("titulado",Usuario.class, true, 0, cookies, usuario.getIdIdentity());
    }    

    
    @Test
    public void test_01_insertar_empresario() {
        usuarioEmpresario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.EMPRESA);
        int idIdentityProfesor=TestUtil.testInsert("site", Usuario.class, true, 0, null, usuarioEmpresario, "idIdentity", null);
        usuarioEmpresario.setIdIdentity(idIdentityProfesor); 
    }  
       
    @Test
    public void test_02_login_empresario() {
        cookiesEmpresario= TestUtil.login(usuarioEmpresario.getEmail(),usuarioEmpresario.getPassword());
    }
    
    @Test
    public void test_03_insertar_empresa() {
        empresa=GeneradorDatosAleatorios.createEmpresaAleatoria(null);
        int idEmpresa=TestUtil.testInsert("empresa", Empresa.class, true, 0, cookiesEmpresario, empresa, "idEmpresa", null);
        empresa.setIdEmpresa(idEmpresa);
        usuarioEmpresario.setEmpresa(empresa);
    }

    @Test
    public void test_04_estadisticas_empresa() {
        given().
                cookies(cookiesEmpresario).
                when().
                get("/api/empresa/Estadisticas/empresa/{idEmpresa}",empresa.getIdEmpresa()).
                then().
                statusCode(HttpStatus.SC_OK).
                body("ofertasPorFamilia", not(nullValue())).
                body("candidatosPorFamilia", not(nullValue())).
                body("numeroCandidatos", not(nullValue())).
                body("numeroOfertas", not(nullValue()));
    }
    
    @Test
    public void test_05_update_empresa() {
        TestUtil.testUpdate("empresa", Empresa.class, true, 0, cookiesEmpresario, empresa, "idEmpresa",null);
    }
    
    @Test
    public void test_06_crud_empresario() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.EMPRESA);
        usuario.setEmpresa(empresa);
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("empresa.idEmpresa", empresa.getIdEmpresa());        
        params.put("tipoUsuario",TipoUsuario.EMPRESA.name() );        
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "empresa";
        crudTestConfiguration.entityClass = Usuario.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookiesEmpresario;
        crudTestConfiguration.entity = usuario;
        crudTestConfiguration.primaryKeyName = "idIdentity";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    }    
    
    @Test
    public void test_07_crud_oferta() {
        oferta=TestUtil.createOfertaAplicacionesWeb(empresa);
        
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("empresa.idEmpresa", empresa.getIdEmpresa());
        
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "empresa";
        crudTestConfiguration.entityClass = Oferta.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookiesEmpresario;
        crudTestConfiguration.entity = oferta;
        crudTestConfiguration.primaryKeyName = "idOferta";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    }    
    
    @Test
    public void test_08_insertar_oferta() {
        oferta=TestUtil.createOfertaAplicacionesWeb(empresa);
        int idOferta=TestUtil.testInsert("empresa", Oferta.class, true, 0, cookiesEmpresario, oferta, "idOferta", null);
        oferta.setIdOferta(idOferta);
    } 
    
    @Test
    public void test_09_insertar_titulado_con_formacion_academica() {
        usuarioTitulado = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.TITULADO);
        int idIdentity = TestUtil.testInsert("site",Usuario.class, true, 0, null, usuarioTitulado, "idIdentity",null);
        usuarioTitulado.setIdIdentity(idIdentity);
        
        cookiesTitulado=TestUtil.login(usuarioTitulado.getLogin(), usuarioTitulado.getPassword());
        
        Titulado titulado=GeneradorDatosAleatorios.createTituladoAleatorio();
        int idTitulado=TestUtil.testInsert("titulado",Titulado.class, true, 0, cookiesTitulado, titulado, "idTitulado",null);
        titulado.setIdTitulado(idTitulado);
        usuarioTitulado.setTitulado(titulado);        
        
        
        formacionAcademica=new FormacionAcademica();
        formacionAcademica.setTitulado(titulado);
        formacionAcademica.setTipoFormacionAcademica(TipoFormacionAcademica.CICLO_FORMATIVO);
        formacionAcademica.setFecha(GeneradorDatosAleatorios.getFecha(0, 5));
        Centro centro=new Centro();
        centro.setIdCentro(-1);
        formacionAcademica.setCentro(centro);
        formacionAcademica.setOtroCentro(GeneradorDatosAleatorios.getNombreCentroAleatorio());
        formacionAcademica.setCiclo(oferta.getCiclos().iterator().next());
        int idFormacionAcademica=TestUtil.testInsert("titulado", FormacionAcademica.class, true, 0, cookiesTitulado, formacionAcademica, "idFormacionAcademica", null);
        formacionAcademica.setIdFormacionAcademica(idFormacionAcademica);

    }
    

    @Test
    public void test_10_insertar_candidato() {
        candidato=new Candidato();
        candidato.setOferta(oferta);
        candidato.setUsuario(usuarioTitulado);
        int idCandidato=TestUtil.testInsert("titulado", Candidato.class, true, 0, cookiesTitulado, candidato, "idCandidato", null);
        candidato.setIdCandidato(idCandidato);
    }
    
    @Test
    public void test_11_numero_candidatos() {

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$namedsearch", "getNumCandidatosOferta");        
        params.put("oferta", oferta.getIdOferta());        
        
        given().log().ifValidationFails().
        queryParams(params).
        cookies(cookiesEmpresario).
        when().log().ifValidationFails().get("/api/empresa/Candidato").then().parser("text/plain", Parser.TEXT).body(containsString("1"));

    }    
    
    @Test
    public void test_12_buscar_candidatos() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$namedsearch", "getCandidatosOferta");        
        params.put("oferta", oferta.getIdOferta());        
        params.put("certificados", "false");        
        params.put("maxAnyoTitulo", 200);        
        params.put("ocultarRechazados", "false");        

        TestUtil.testPaginatedSearch("empresa", Candidato.class, true, 0, cookiesEmpresario, params).then().statusCode(HttpStatus.SC_OK).body("content",hasSize(equalTo(1)));
    
    }     
    
    @Test
    public void test_13_obtener_candidato() {
        TestUtil.testRead("empresa", Candidato.class, true, 0, cookiesEmpresario, candidato.getIdCandidato(),null).body("idCandidato",equalTo(candidato.getIdCandidato()));
    }     
    
    @Test
    public void test_14_obtener_foto() {       
        given().log().ifValidationFails().
        cookies(cookiesEmpresario).
        when().log().ifValidationFails().get("/api/empresa/Candidato/{idCandidato}/foto",candidato.getIdCandidato()).then().statusCode(HttpStatus.SC_OK);
    }     
    
    @Test
    public void test_15_borrar_candidato() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("oferta.idOferta", oferta.getIdOferta());        
        params.put("usuario.idIdentity", usuarioTitulado.getIdIdentity());        

        TestUtil.testSearch("titulado", Candidato.class, true, 0, cookiesTitulado, params).then().body("",hasSize(equalTo(1))).body("[0].idCandidato",equalTo(candidato.getIdCandidato()));
        
        TestUtil.testDelete("titulado",Candidato.class, true, 0, cookiesTitulado, candidato.getIdCandidato()); 
    }    
    
    @Test
    public void test_16_buscar_candidatos() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$namedsearch", "getCandidatosOferta");        
        params.put("oferta", oferta.getIdOferta());        
        params.put("certificados", "false");        
        params.put("maxAnyoTitulo", 200);        
        params.put("ocultarRechazados", "false");        

        TestUtil.testPaginatedSearch("empresa", Candidato.class, true, 0, cookiesEmpresario, params).then().statusCode(HttpStatus.SC_OK).body("content",hasSize(equalTo(0)));
    
    }   
        
    


    
    @Test
    public void test_17_update_usuario() {
        TestUtil.testUpdate("empresa", Usuario.class, true, 0, cookiesEmpresario, usuarioEmpresario, "idIdentity",null);
    }    
    
    @Test
    public void test_18_update_password_contrasenya_erronea() {
        TestUtil.testUpdatePassword("empresa", false, HttpStatus.SC_BAD_REQUEST, cookiesEmpresario, usuarioEmpresario.getIdIdentity(), null);

    }     
    
    @Test
    public void test_19_update_password() {
        TestUtil.testUpdatePassword("empresa", true, 0, cookiesEmpresario, usuarioEmpresario.getIdIdentity(), usuarioEmpresario.getPassword());
        cookiesEmpresario= TestUtil.login(usuarioEmpresario.getEmail(),usuarioEmpresario.getPassword());        
    }
    
    @Test
    public void test_20_estadisticas_centro_prohibidas_sin_login() {
        given().
                when().get("/api/centro/Estadisticas/empresa/{idEmpresa}",empresa.getIdEmpresa()).
                then().statusCode(HttpStatus.SC_FORBIDDEN);
    }
}

   