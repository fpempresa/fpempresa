/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.presentacion.api;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.ExperienciaLaboral;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.modelo.titulado.TituloIdioma;
import es.logongas.fpempresa.modelo.titulado.configuracion.Configuracion;
import es.logongas.fpempresa.modelo.titulado.configuracion.NotificacionOferta;
import es.logongas.fpempresa.service.populate.GeneradorDatosAleatorios;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author logongas
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TituladoTest {
    static Map<String, String> cookies;
    static Usuario usuario;
    static Titulado titulado;

    @BeforeClass
    public static void setUpClass() {
        usuario = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.TITULADO);
        String currentPassword = usuario.getPassword();
        int idIdentity = TestUtil.testInsert("site",Usuario.class, true, 0, null, usuario, "idIdentity",null);
        usuario.setIdIdentity(idIdentity);
        
        cookies=TestUtil.login(usuario.getLogin(), currentPassword);

    }
    @AfterClass
    public static void tearDownClass() {
        cookies=TestUtil.login(usuario.getLogin(), usuario.getPassword());        
        TestUtil.testDelete("titulado",Usuario.class, false, HttpStatus.SC_FORBIDDEN, null, usuario.getIdIdentity());
        TestUtil.testDelete("titulado",Usuario.class, true, 0, cookies, usuario.getIdIdentity());
    }    


    @Test
    public void test_01_insertat_titulado() {
        titulado=GeneradorDatosAleatorios.createTituladoAleatorio();
        int idTitulado=TestUtil.testInsert("titulado",Titulado.class, true, 0, cookies, titulado, "idTitulado",null);
        titulado.setIdTitulado(idTitulado);
        usuario.setTitulado(titulado);
    }
    
    @Test
    public void test_02_crud_titulo_idioma() {
        TituloIdioma tituloIdioma=GeneradorDatosAleatorios.createTituloIdiomaAleatorio(titulado);
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("titulado.idTitulado", titulado.getIdTitulado());
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "titulado";
        crudTestConfiguration.entityClass = TituloIdioma.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = tituloIdioma;
        crudTestConfiguration.primaryKeyName = "idTituloIdioma";
        crudTestConfiguration.paginated = false;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);        

    } 
    @Test
    public void test_03_crud_formacion_academica() {
        FormacionAcademica formacionAcademica=GeneradorDatosAleatorios.createFormacionAcademicaAleatoria(titulado);
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("titulado.idTitulado", titulado.getIdTitulado());
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "titulado";
        crudTestConfiguration.entityClass = FormacionAcademica.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = formacionAcademica;
        crudTestConfiguration.primaryKeyName = "idFormacionAcademica";
        crudTestConfiguration.paginated = false;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    } 
    @Test
    public void test_04_crud_experiencia_laboral() {
        ExperienciaLaboral experienciaLaboral=GeneradorDatosAleatorios.createExperienciaLaboralAleatoria(titulado);
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("titulado.idTitulado", titulado.getIdTitulado());
        
        CRUDTestConfiguration crudTestConfiguration = new CRUDTestConfiguration();
        crudTestConfiguration.appPath = "titulado";
        crudTestConfiguration.entityClass = ExperienciaLaboral.class;
        crudTestConfiguration.success = true;
        crudTestConfiguration.httpStatus = 0;
        crudTestConfiguration.cookies = cookies;
        crudTestConfiguration.entity = experienciaLaboral;
        crudTestConfiguration.primaryKeyName = "idExperienciaLaboral";
        crudTestConfiguration.paginated = false;
        crudTestConfiguration.params = params;
        crudTestConfiguration.readNoContent = false;

        TestUtil.testCRUD(crudTestConfiguration);
    }
    
    
    @Test
    public void test_05_update_usuario() {
        TestUtil.testUpdate("titulado", Usuario.class, true, 0, cookies, usuario, "idIdentity",null);
    } 
    
    
    @Test
    public void test_06_update_configuracion() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$expand", "configuracion.notificacionOferta.provincias");      
        Configuracion configuracion=new Configuracion();
        NotificacionOferta notificacionOferta=new NotificacionOferta();
        notificacionOferta.setNotificarPorEmail(true);
        Set<Provincia> provincias =new HashSet<Provincia>();
        Provincia provincia=GeneradorDatosAleatorios.getProvinciaAleatoria();
        provincias.add(provincia);
        notificacionOferta.setProvincias(provincias);
        configuracion.setNotificacionOferta(notificacionOferta);
        titulado.setConfiguracion(configuracion);
        
        TestUtil.testUpdate("titulado", Titulado.class, true, 0, cookies, titulado, "idTitulado",params);
        
        TestUtil.testRead("titulado", Titulado.class, true, 0, cookies, titulado.getIdTitulado(),params).
                body("configuracion.notificacionOferta.provincias",hasSize(equalTo(1))).              
                body("configuracion.notificacionOferta.provincias[0].idProvincia",equalTo(provincia.getIdProvincia())).
                body("configuracion.notificacionOferta.notificarPorEmail",equalTo(true));
        
        
    } 
    
    @Test
    public void test_07_buscar_ofertas_pero_no_hay() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("$namedsearch", "getOfertasUsuarioTitulado");         
        params.put("usuario", usuario.getIdIdentity());         
        params.put("$expand", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos");         
        
        TestUtil.testSearch("titulado", Oferta.class, true, 0, cookies, params).then().body("",hasSize(equalTo(0)));
    }
    
    @Test
    public void test_08_nuevo_candidato_oferta_centro() {
        int idCentro;
        int idIdentityProfesor;
        int idEmpresa;
        int idOferta;
        int idFormacionAcademica;
        int idCandidato;
        
        Map<String,String> cookiesAdministrador= TestUtil.login("administrador@fpempresa.net", "administrador");
        
        Centro centro = GeneradorDatosAleatorios.createCentroAleatorio();
        idCentro=TestUtil.testInsert("administrador", Centro.class, true, 0, cookiesAdministrador, centro, "idCentro", null);
        centro.setIdCentro(idCentro);
        
        Usuario profesor = GeneradorDatosAleatorios.createUsuarioAleatorio(TipoUsuario.CENTRO);
        profesor.setCentro(centro);
        idIdentityProfesor=TestUtil.testInsert("administrador", Usuario.class, true, 0, cookiesAdministrador, profesor, "idIdentity", null);
        profesor.setIdIdentity(idIdentityProfesor);
        
        Map<String,String> cookiesProfesor= TestUtil.login(profesor.getEmail(),profesor.getPassword());
        
        
        Empresa empresaCentro=GeneradorDatosAleatorios.createEmpresaAleatoria(centro);
        idEmpresa=TestUtil.testInsert("centro", Empresa.class, true, 0, cookiesProfesor, empresaCentro, "idEmpresa", null);
        empresaCentro.setIdEmpresa(idEmpresa);
        
        Oferta oferta=TestUtil.createOfertaAplicacionesWeb(empresaCentro);
        idOferta=TestUtil.testInsert("centro", Oferta.class, true, 0, cookiesProfesor, oferta, "idOferta", null);
        oferta.setIdOferta(idOferta);
        
        FormacionAcademica formacionAcademica=new FormacionAcademica();
        formacionAcademica.setTitulado(titulado);
        formacionAcademica.setTipoFormacionAcademica(TipoFormacionAcademica.CICLO_FORMATIVO);
        formacionAcademica.setFecha(GeneradorDatosAleatorios.getFecha(0, 5));
        formacionAcademica.setCentro(centro);
        formacionAcademica.setCiclo(oferta.getCiclos().iterator().next());
        
        idFormacionAcademica=TestUtil.testInsert("titulado", FormacionAcademica.class, true, 0, cookies, formacionAcademica, "idFormacionAcademica", null);
        
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("usuario", usuario.getIdIdentity());         
        params.put("$expand", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos");         
        
        params.put("$namedsearch", "getOfertasUsuarioTitulado"); 
        TestUtil.testSearch("titulado", Oferta.class, true, 0, cookies, params).then().body("",hasItem(hasEntry("idOferta", idOferta)));
        params.put("$namedsearch", "getOfertasInscritoUsuarioTitulado");                
        TestUtil.testSearch("titulado", Oferta.class, true, 0, cookies, params).then().body("",hasSize(equalTo(0)));
        
        Candidato candidato=new Candidato();
        candidato.setOferta(oferta);
        candidato.setUsuario(usuario);
        idCandidato=TestUtil.testInsert("titulado", Candidato.class, true, 0, cookies, candidato, "idCandidato", null);
        candidato.setIdCandidato(idCandidato);
        
        params.put("$namedsearch", "getOfertasUsuarioTitulado");              
        TestUtil.testSearch("titulado", Oferta.class, true, 0, cookies, params).then().body("",not(hasItem(hasEntry("idOferta", idOferta))));       
        params.put("$namedsearch", "getOfertasInscritoUsuarioTitulado");                
        TestUtil.testSearch("titulado", Oferta.class, true, 0, cookies, params).then().body("",hasSize(equalTo(1))).body("[0].idOferta",equalTo(idOferta));

        Map<String,Object> paramsSearchCandidato=new HashMap<String,Object>();
        paramsSearchCandidato.put("oferta.idOferta", oferta.getIdOferta());        
        paramsSearchCandidato.put("usuario.idIdentity", usuario.getIdIdentity());        

        TestUtil.testSearch("titulado", Candidato.class, true, 0, cookies, paramsSearchCandidato).then().body("",hasSize(equalTo(1))).body("[0].idCandidato",equalTo(candidato.getIdCandidato()));
        TestUtil.testDelete("titulado",Candidato.class, true, 0, cookies, idCandidato);  
        
        params.put("$namedsearch", "getOfertasUsuarioTitulado"); 
        TestUtil.testSearch("titulado", Oferta.class, true, 0, cookies, params).then().body("",hasItem(hasEntry("idOferta", idOferta)));
        params.put("$namedsearch", "getOfertasInscritoUsuarioTitulado");                
        TestUtil.testSearch("titulado", Oferta.class, true, 0, cookies, params).then().body("",hasSize(equalTo(0)));
        
        TestUtil.testDelete("titulado",FormacionAcademica.class, true, 0, cookies, idFormacionAcademica);
        TestUtil.testDelete("centro",Oferta.class, true, 0, cookiesProfesor, idOferta);
        TestUtil.testDelete("centro",Empresa.class, true, 0, cookiesProfesor, idEmpresa);
        TestUtil.testDelete("administrador",Usuario.class, true, 0, cookiesAdministrador, idIdentityProfesor);
    }    

    
    @Test
    public void test_97_update_password_prohibido() {
        TestUtil.testUpdatePassword("titulado", false, HttpStatus.SC_FORBIDDEN, null, usuario.getIdIdentity(), usuario.getPassword());

    } 
    
    @Test
    public void test_98_update_password_contrasenya_erronea() {
        TestUtil.testUpdatePassword("titulado", false, HttpStatus.SC_BAD_REQUEST, cookies, usuario.getIdIdentity(), null);

    }     
    
    @Test
    public void test_99_update_password() {
        TestUtil.testUpdatePassword("titulado", true, 0, cookies, usuario.getIdIdentity(), usuario.getPassword());
    }  
}
