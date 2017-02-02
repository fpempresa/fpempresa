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

//import es.logongas.fpempresa.modelo.centro.Centro;
//import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
//import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
//import es.logongas.fpempresa.modelo.empresa.Candidato;
//import es.logongas.fpempresa.modelo.empresa.Empresa;
//import es.logongas.fpempresa.modelo.empresa.Oferta;
//import es.logongas.fpempresa.modelo.titulado.ExperienciaLaboral;
//import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
//import es.logongas.fpempresa.modelo.titulado.Titulado;
//import es.logongas.fpempresa.modelo.titulado.TituloIdioma;
//import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
//import es.logongas.fpempresa.service.populate.PopulateService;
//import es.logongas.ix3.core.BusinessException;
//import es.logongas.ix3.dao.Filter;
//import es.logongas.ix3.service.CRUDService;
//import es.logongas.ix3.service.CRUDServiceFactory;
//import es.logongas.ix3.web.util.HttpResult;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author logongas
 */
@Controller
public class PopulateController {
    /**
     * private static final Log log =
     * LogFactory.getLog(PopulateController.class);
     *
     * @Autowired private PopulateService populateService;
     *
     * @Autowired private CRUDServiceFactory crudServiceFactory;
     *
     * @RequestMapping(value = {"/administrador/$populate"}, method =
     * RequestMethod.POST, produces = "application/json") public void
     * crearTodo(HttpServletRequest httpServletRequest,HttpServletResponse
     * httpServletResponse) {
     *
     * restMethod(httpServletRequest, httpServletResponse,"crearTodo",null, new
     * Command() {
     *
     * @Override public HttpResult run() throws Exception, BusinessException {
     *
     * Date dateStart = new Date();
     *
     *
     *
     * crearCentros(12); crearEmpresas(89); crearOfertas(438); crearUsuarios(23,
     * TipoUsuario.CENTRO); crearUsuarios(197, TipoUsuario.EMPRESA);
     * crearUsuarios(775, TipoUsuario.TITULADO); createCandidatos();
     *
     * Date dateEnd = new Date();
     *
     * Map<String, Object> result = new HashMap<String, Object>();
     * result.put("dateStart", dateStart); result.put("dateEnd", dateEnd);
     * result.put("duration", dateEnd.getTime() - dateStart.getTime()); return
     * new HttpResult(result); } }); }
     *
     * @RequestMapping(value = {"/administrador/$populate/Centro/{numCentros}"},
     * method = RequestMethod.POST, produces = "application/json") public void
     * crearCentrosAleatorios(HttpServletRequest
     * httpServletRequest,HttpServletResponse httpServletResponse,final
     * @PathVariable("numCentros") int numCentros) {
     *
     * restMethod(httpServletRequest,
     * httpServletResponse,"crearCentrosAleatorios",null, new Command() {
     *
     * @Override public HttpResult run() throws Exception, BusinessException {
     *
     * Date dateStart = new Date();
     *
     * crearCentros(numCentros);
     *
     * Date dateEnd = new Date();
     *
     * Map<String, Object> result = new HashMap<String, Object>();
     * result.put("dateStart", dateStart); result.put("dateEnd", dateEnd);
     * result.put("duration", dateEnd.getTime() - dateStart.getTime());
     * result.put("num", numCentros); return new HttpResult(result); } }); }
     *
     * @RequestMapping(value =
     * {"/administrador/$populate/Empresa/{numEmpresas}"}, method =
     * RequestMethod.POST, produces = "application/json") public void
     * crearEmpresasAleatorias(HttpServletRequest
     * httpServletRequest,HttpServletResponse httpServletResponse, final
     * @PathVariable("numEmpresas") int numEmpresas) {
     *
     * restMethod(httpServletRequest,
     * httpServletResponse,"crearEmpresasAleatorias",null, new Command() {
     *
     * @Override public HttpResult run() throws Exception, BusinessException {
     *
     * Date dateStart = new Date();
     *
     * crearEmpresas(numEmpresas);
     *
     * Date dateEnd = new Date();
     *
     * Map<String, Object> result = new HashMap<String, Object>();
     * result.put("dateStart", dateStart); result.put("dateEnd", dateEnd);
     * result.put("duration", dateEnd.getTime() - dateStart.getTime());
     * result.put("num", numEmpresas); return new HttpResult(result); } }); }
     *
     * @RequestMapping(value =
     * {"/administrador/$populate/Usuario/{numUsuarios}/{tipoUsuario}"}, method
     * = RequestMethod.POST, produces = "application/json") public void
     * crearUsuariosAleatorios(HttpServletRequest
     * httpServletRequest,HttpServletResponse httpServletResponse,final
     * @PathVariable("numUsuarios") int numUsuarios,final
     * @PathVariable("tipoUsuario") TipoUsuario tipoUsuario) {
     *
     * restMethod(httpServletRequest,
     * httpServletResponse,"crearUsuariosAleatorios",null, new Command() {
     *
     * @Override public HttpResult run() throws Exception, BusinessException {
     *
     * Date dateStart = new Date();
     *
     * crearUsuarios(numUsuarios, tipoUsuario);
     *
     * Date dateEnd = new Date();
     *
     * Map<String, Object> result = new HashMap<String, Object>();
     * result.put("dateStart", dateStart); result.put("dateEnd", dateEnd);
     * result.put("duration", dateEnd.getTime() - dateStart.getTime());
     * result.put("num", numUsuarios); return new HttpResult(result); } }); }
     *
     * @RequestMapping(value = {"/administrador/$populate/Oferta/{numOfertas}"},
     * method = RequestMethod.POST, produces = "application/json") public void
     * crearOfertasAleatorias(HttpServletRequest
     * httpServletRequest,HttpServletResponse httpServletResponse,final
     * @PathVariable("numOfertas") int numOfertas) {
     *
     * restMethod(httpServletRequest,
     * httpServletResponse,"crearOfertasAleatorias",null, new Command() {
     *
     * @Override public HttpResult run() throws Exception, BusinessException {
     *
     * Date dateStart = new Date();
     *
     * crearOfertas(numOfertas);
     *
     * Date dateEnd = new Date();
     *
     * Map<String, Object> result = new HashMap<String, Object>();
     * result.put("dateStart", dateStart); result.put("dateEnd", dateEnd);
     * result.put("duration", dateEnd.getTime() - dateStart.getTime());
     * result.put("num", numOfertas); return new HttpResult(result); } }); }
     *
     * @RequestMapping(value = {"/administrador/$populate/Candidato"}, method =
     * RequestMethod.POST, produces = "application/json") public void
     * crearCandidatos(HttpServletRequest httpServletRequest,HttpServletResponse
     * httpServletResponse) {
     *
     * restMethod(httpServletRequest,
     * httpServletResponse,"crearCandidatos",null, new Command() {
     *
     * @Override public HttpResult run() throws Exception, BusinessException {
     *
     * Date dateStart = new Date();
     *
     * createCandidatos();
     *
     * Date dateEnd = new Date();
     *
     * Map<String, Object> result = new HashMap<String, Object>();
     * result.put("dateStart", dateStart); result.put("dateEnd", dateEnd);
     * result.put("duration", dateEnd.getTime() - dateStart.getTime()); return
     * new HttpResult(result); } }); } *
     *
     * private void crearCentros(int numCentros) throws BusinessException {
     * CRUDService<Centro, Integer> centroService =
     * crudServiceFactory.getService(Centro.class); for (int i = 0; i <
     * numCentros; i++) { Centro centro =
     * populateService.createCentroAleatorio(); centroService.insert(centro); }
     * }
     *
     * private void crearEmpresas(int numEmpresas) throws BusinessException {
     * CRUDService<Empresa, Integer> empresaService =
     * crudServiceFactory.getService(Empresa.class); for (int i = 0; i <
     * numEmpresas; i++) { Empresa empresa =
     * populateService.createEmpresaAleatoria(); empresaService.insert(empresa);
     * } }
     *
     * private void crearOfertas(int numOfertas) throws BusinessException {
     * CRUDService<Oferta, Integer> ofertaService =
     * crudServiceFactory.getService(Oferta.class); for (int i = 0; i <
     * numOfertas; i++) { Oferta oferta =
     * populateService.createOfertaAleatoria(null);
     * ofertaService.insert(oferta); } }
     *
     * private void crearUsuarios(int numUsuarios, TipoUsuario tipoUsuario)
     * throws BusinessException { CRUDService<Usuario, Integer> usuarioService =
     * crudServiceFactory.getService(Usuario.class);
     * CRUDService<Titulado, Integer> tituladoService =
     * crudServiceFactory.getService(Titulado.class);
     * CRUDService<FormacionAcademica, Integer> formacionAcademicaService =
     * crudServiceFactory.getService(FormacionAcademica.class);
     * CRUDService<ExperienciaLaboral, Integer> experienciaLaboralService =
     * crudServiceFactory.getService(ExperienciaLaboral.class);
     * CRUDService<TituloIdioma, Integer> tituloIdiomaService =
     * crudServiceFactory.getService(TituloIdioma.class);
     *
     * for (int i = 0; i < numUsuarios; i++) { Usuario usuario =
     * populateService.createUsuarioAleatorio(tipoUsuario);
     *
     * if (usuario.getTipoUsuario() == TipoUsuario.TITULADO) {
     * tituladoService.insert(usuario.getTitulado()); for (FormacionAcademica
     * formacionAcademica : usuario.getTitulado().getFormacionesAcademicas()) {
     * formacionAcademicaService.insert(formacionAcademica); } for
     * (ExperienciaLaboral experienciaLaboral :
     * usuario.getTitulado().getExperienciasLaborales()) {
     * experienciaLaboralService.insert(experienciaLaboral); } for (TituloIdioma
     * tituloIdioma : usuario.getTitulado().getTitulosIdiomas()) {
     * tituloIdiomaService.insert(tituloIdioma); }
     *
     * }
     * usuarioService.insert(usuario);
     *
     * }
     * }
     *
     * private void createCandidatos() throws BusinessException {
     * CRUDService<Usuario, Integer> usuarioService =
     * crudServiceFactory.getService(Usuario.class); List<Filter> filters=new
     * ArrayList<Filter>(); filters.add(new Filter("tipoUsuario",
     * TipoUsuario.TITULADO)); List<Usuario>
     * usuarios=usuarioService.search(filters); for(Usuario usuario:usuarios) {
     * createCandidato(usuario); } }
     *
     * private void createCandidato(Usuario usuario) throws BusinessException {
     * CRUDService<Candidato, Integer> candidatoService =
     * crudServiceFactory.getService(Candidato.class); OfertaCRUDService
     * ofertaCRUDService = (OfertaCRUDService)
     * crudServiceFactory.getService(Oferta.class); if (usuario.getTipoUsuario()
     * == TipoUsuario.TITULADO) { List<Oferta> ofertas =
     * ofertaCRUDService.getOfertasUsuarioTitulado(usuario, null, null, null);
     * int numOfertas = (int) Math.ceil(ofertas.size() * 0.8); for (int j = 0; j
     * < numOfertas; j++) { Candidato candidato = candidatoService.create();
     * candidato.setOferta(ofertas.get(j)); candidato.setUsuario(usuario);
     * candidatoService.insert(candidato); } } }
     *
     *
     */
}
