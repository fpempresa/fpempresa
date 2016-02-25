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
public class ProfesorTest {
    static Map<String, String> cookiesAdministrador;
    static Map<String, String> cookiesProfesor;
    static Map<String, String> cookiesTitulado;
    static Usuario usuarioProfesor;
    static Centro centro;
    static Empresa empresaCentro;
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
    public void test_01_insertar_centro() {
        centro = GeneradorDatosAleatorios.createCentroAleatorio();
        int idCentro=TestUtil.testInsert("administrador", Centro.class, true, 0, cookiesAdministrador, centro, "idCentro", null);
        centro.setIdCentro(idCentro);
    }
    
    @Test
    public void test_02_insertar_profesor() {
        usuarioProfesor = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.CENTRO);
        usuarioProfesor.setCentro(centro);
        int idIdentityProfesor=TestUtil.testInsert("administrador", Usuario.class, true, 0, cookiesAdministrador, usuarioProfesor, "idIdentity", null);
        usuarioProfesor.setIdIdentity(idIdentityProfesor); 
    }   

    @Test
    public void test_03_login_profesor() {
        cookiesProfesor= TestUtil.login(usuarioProfesor.getEmail(),usuarioProfesor.getPassword());
    }     
    
    
    
    @Test
    public void test_04_insertar_empresa_centro() {
        empresaCentro=GeneradorDatosAleatorios.createEmpresaAleatoria(centro);
        int idEmpresa=TestUtil.testInsert("centro", Empresa.class, true, 0, cookiesProfesor, empresaCentro, "idEmpresa", null);
        empresaCentro.setIdEmpresa(idEmpresa);
    }    
    
    @Test
    public void test_05_crud_oferta() {
        oferta=TestUtil.createOfertaAplicacionesWeb(empresaCentro);
        
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("empresa.centro.idCentro", centro.getIdCentro());
        
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "centro";
        crudTestConfiguration.entityClass = Oferta.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookiesProfesor;
        crudTestConfiguration.entity = oferta;
        crudTestConfiguration.primaryKeyName = "idOferta";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    }    
    
    @Test
    public void test_06_insertar_oferta() {
        oferta=TestUtil.createOfertaAplicacionesWeb(empresaCentro);
        int idOferta=TestUtil.testInsert("centro", Oferta.class, true, 0, cookiesProfesor, oferta, "idOferta", null);
        oferta.setIdOferta(idOferta);
    } 
    
    @Test
    public void test_07_insertar_titulado_con_formacion_academica() {
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
        formacionAcademica.setCentro(centro);
        formacionAcademica.setCiclo(oferta.getCiclos().iterator().next());
        int idFormacionAcademica=TestUtil.testInsert("titulado", FormacionAcademica.class, true, 0, cookiesTitulado, formacionAcademica, "idFormacionAcademica", null);
        formacionAcademica.setIdFormacionAcademica(idFormacionAcademica);

    }
    

    @Test
    public void test_08_insertar_candidato() {
        candidato=new Candidato();
        candidato.setOferta(oferta);
        candidato.setUsuario(usuarioTitulado);
        int idCandidato=TestUtil.testInsert("titulado", Candidato.class, true, 0, cookiesTitulado, candidato, "idCandidato", null);
        candidato.setIdCandidato(idCandidato);
    }
    
    @Test
    public void test_09_numero_candidatos() {

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$namedsearch", "getNumCandidatosOferta");        
        params.put("oferta", oferta.getIdOferta());        
        
        given().log().ifValidationFails().
        queryParams(params).
        cookies(cookiesProfesor).
        when().log().ifValidationFails().get("/api/centro/Candidato").then().parser("text/plain", Parser.TEXT).body(containsString("1"));

    }    
    
    @Test
    public void test_10_buscar_candidatos() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$namedsearch", "getCandidatosOferta");        
        params.put("oferta", oferta.getIdOferta());        
        params.put("certificados", "false");        
        params.put("maxAnyoTitulo", 200);        
        params.put("ocultarRechazados", "false");        

        TestUtil.testPaginatedSearch("centro", Candidato.class, true, 0, cookiesProfesor, params).then().statusCode(HttpStatus.SC_OK).body("content",hasSize(equalTo(1)));
    
    }     
    
    @Test
    public void test_11_obtener_candidato() {
        TestUtil.testRead("centro", Candidato.class, true, 0, cookiesProfesor, candidato.getIdCandidato(),null).body("idCandidato",equalTo(candidato.getIdCandidato()));
    }     
    
    @Test
    public void test_12_obtener_foto() {       
        given().log().ifValidationFails().
        cookies(cookiesProfesor).
        when().log().ifValidationFails().get("/api/centro/Candidato/{idCandidato}/foto",candidato.getIdCandidato()).then().statusCode(HttpStatus.SC_OK);
    }     
    
    @Test
    public void test_13_borrar_candidato() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("oferta.idOferta", oferta.getIdOferta());        
        params.put("usuario.idIdentity", usuarioTitulado.getIdIdentity());        

        TestUtil.testSearch("titulado", Candidato.class, true, 0, cookiesTitulado, params).then().body("",hasSize(equalTo(1))).body("[0].idCandidato",equalTo(candidato.getIdCandidato()));
        
        TestUtil.testDelete("titulado",Candidato.class, true, 0, cookiesTitulado, candidato.getIdCandidato()); 
    }    
    
    @Test
    public void test_14_buscar_candidatos() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$namedsearch", "getCandidatosOferta");        
        params.put("oferta", oferta.getIdOferta());        
        params.put("certificados", "false");        
        params.put("maxAnyoTitulo", 200);        
        params.put("ocultarRechazados", "false");        

        TestUtil.testPaginatedSearch("centro", Candidato.class, true, 0, cookiesProfesor, params).then().statusCode(HttpStatus.SC_OK).body("content",hasSize(equalTo(0)));
    
    } 
    
    @Test
    public void test_15_crud_empresa() {
        Empresa empresa = GeneradorDatosAleatorios.createEmpresaAleatoria(centro);

        Map<String,Object> params=new HashMap<String,Object>();
        params.put("centro.idCentro", centro.getIdCentro());        
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "centro";
        crudTestConfiguration.entityClass = Empresa.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookiesProfesor;
        crudTestConfiguration.entity = empresa;
        crudTestConfiguration.primaryKeyName = "idEmpresa";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    }
    
    @Test
    public void test_16_update_usuario() {
        TestUtil.testUpdate("centro", Usuario.class, true, 0, cookiesProfesor, usuarioProfesor, "idIdentity",null);
    }    
    
    @Test
    public void test_17_update_password_contrasenya_erronea() {
        TestUtil.testUpdatePassword("centro", false, HttpStatus.SC_BAD_REQUEST, cookiesProfesor, usuarioProfesor.getIdIdentity(), null);

    }     
    
    @Test
    public void test_18_update_password() {
        TestUtil.testUpdatePassword("centro", true, 0, cookiesProfesor, usuarioProfesor.getIdIdentity(), usuarioProfesor.getPassword());
        cookiesProfesor= TestUtil.login(usuarioProfesor.getEmail(),usuarioProfesor.getPassword());        
    }
    
    @Test
    public void test_19_estadisticas_centro_prohibidas_sin_login() {
        given().
                when().get("/api/centro/Estadisticas/centro/{idCentro}",centro.getIdCentro()).
                then().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void test_20_estadisticas_centro() {
        given().
                cookies(cookiesProfesor).
                when().
                get("/api/centro/Estadisticas/centro/{idCentro}",centro.getIdCentro()).
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
    public void test_21_buscar_titulados() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("tipoUsuario",TipoUsuario.TITULADO.name());         
        params.put("titulado.formacionesAcademicas.centro.idCentro", centro.getIdCentro());         
 
        TestUtil.testPaginatedSearch("centro", Usuario.class, true, 0, cookiesProfesor, params).then().body("content",hasSize(equalTo(1))).body("content[0].idIdentity",equalTo(usuarioTitulado.getIdIdentity()));
    }
    
    @Test
    public void test_22_leer_titulados() {      
        TestUtil.testRead("centro", Usuario.class, true, 0, cookiesProfesor,usuarioTitulado.getIdIdentity() ,null).body("idIdentity",equalTo(usuarioTitulado.getIdIdentity()));
    }
    
    @Test
    public void test_23_update_centro() {
        TestUtil.testUpdate("centro", Centro.class, true, 0, cookiesProfesor, centro, "idCentro",null);
    }
    
    @Test
    public void test_24_crud_profesores() {
        Usuario usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.CENTRO);
        usuario.setCentro(centro);
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("centro.idCentro", centro.getIdCentro());        
        params.put("tipoUsuario",TipoUsuario.CENTRO.name() );        
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "centro";
        crudTestConfiguration.entityClass = Usuario.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookiesProfesor;
        crudTestConfiguration.entity = usuario;
        crudTestConfiguration.primaryKeyName = "idIdentity";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    }
    
    @Test
    public void test_25_crud_certificado_titulo() {
        GregorianCalendar gregorianCalendar=new GregorianCalendar();
        gregorianCalendar.setTime(formacionAcademica.getFecha());
        
        CertificadoTitulo certificadoTitulo = new CertificadoTitulo();
        certificadoTitulo.setAnyo(gregorianCalendar.get(GregorianCalendar.YEAR));
        certificadoTitulo.setCentro(formacionAcademica.getCentro());
        certificadoTitulo.setCiclo(formacionAcademica.getCiclo());
        certificadoTitulo.setNifnie(usuarioTitulado.getTitulado().getNumeroDocumento()+","+GeneradorDatosAleatorios.getNif()+"\n"+GeneradorDatosAleatorios.getNif());
              
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("centro.idCentro", centro.getIdCentro());                
        
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "centro";
        crudTestConfiguration.entityClass = CertificadoTitulo.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookiesProfesor;
        crudTestConfiguration.entity = certificadoTitulo;
        crudTestConfiguration.primaryKeyName = "idCertificadoTitulo";
        crudTestConfiguration.paginated = true;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    } 
    
    @Test
    public void test_26_comprobar_certificado_titulo_en_formacion() {
        TestUtil.testRead("titulado",FormacionAcademica.class,true,0,cookiesTitulado,formacionAcademica.getIdFormacionAcademica(),null).body("certificadoTitulo", equalTo(false));
        
        GregorianCalendar gregorianCalendar=new GregorianCalendar();
        gregorianCalendar.setTime(formacionAcademica.getFecha());
        
        CertificadoTitulo certificadoTitulo = new CertificadoTitulo();
        certificadoTitulo.setAnyo(gregorianCalendar.get(GregorianCalendar.YEAR));
        certificadoTitulo.setCentro(formacionAcademica.getCentro());
        certificadoTitulo.setCiclo(formacionAcademica.getCiclo());
        certificadoTitulo.setNifnie(usuarioTitulado.getTitulado().getNumeroDocumento()+","+GeneradorDatosAleatorios.getNif()+"\n"+GeneradorDatosAleatorios.getNif());
        
        System.out.println(certificadoTitulo.getNifnie());
        
        int idCentificadoTitulo=TestUtil.testInsert("centro",CertificadoTitulo.class,true,0,cookiesProfesor,certificadoTitulo,"idCertificadoTitulo",null);
        
        TestUtil.testRead("titulado",FormacionAcademica.class,true,0,cookiesTitulado,formacionAcademica.getIdFormacionAcademica(),null).body("certificadoTitulo", equalTo(true));
        
        TestUtil.testDelete("centro",CertificadoTitulo.class,true,0,cookiesProfesor,idCentificadoTitulo);
        
        TestUtil.testRead("titulado",FormacionAcademica.class,true,0,cookiesTitulado,formacionAcademica.getIdFormacionAcademica(),null).body("certificadoTitulo", equalTo(false));

    }
    
    @Test
    public void test_27_cambiar_centro() {
        Centro nuevoCentro = GeneradorDatosAleatorios.createCentroAleatorio();
        int idNuevoCentro=TestUtil.testInsert("administrador", Centro.class, true, 0, cookiesAdministrador, nuevoCentro, "idCentro", null);
        centro.setIdCentro(idNuevoCentro);
        

        given().
                log().ifValidationFails().cookies(cookiesProfesor).
        when().
                patch("/api/centro/Usuario/{idIdentity}/centro/{idNuevoCentro}",usuarioProfesor.getIdIdentity(),nuevoCentro.getIdCentro()).
        then().
                log().ifValidationFails().statusCode(HttpStatus.SC_OK);
        
        
        given().
                log().ifValidationFails().contentType("application/json").accept("application/json").queryParam("login", usuarioProfesor.getLogin()).queryParam("password", usuarioProfesor.getPassword()).
        when().
                post("/api/session").
        then().
                log().ifValidationFails().statusCode(HttpStatus.SC_BAD_REQUEST);

        
        usuarioProfesor.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        TestUtil.testUpdate("administrador", Usuario.class, true, 0, cookiesAdministrador, usuarioProfesor, "idIdentity",null);
        
        
        given().
                log().ifValidationFails().contentType("application/json").accept("application/json").queryParam("login", usuarioProfesor.getLogin()).queryParam("password", usuarioProfesor.getPassword()).
        when().
                post("/api/session").
        then().
                log().ifValidationFails().statusCode(HttpStatus.SC_OK);        
        
    }  
    
    
}
