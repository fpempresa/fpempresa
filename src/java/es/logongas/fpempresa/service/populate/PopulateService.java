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
package es.logongas.fpempresa.service.populate;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.fpempresa.modelo.comun.geo.Municipio;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.ExperienciaLaboral;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Idioma;
import es.logongas.fpempresa.modelo.titulado.NivelIdioma;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.modelo.titulado.TituloIdioma;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class PopulateService implements Service {

    @Autowired
    protected DAOFactory daoFactory;

    @Autowired
    private CRUDServiceFactory crudServiceFactory;

    public Centro createCentroAleatorio() throws BusinessException {
        CRUDService<Centro, Integer> centroService = crudServiceFactory.getService(Centro.class);

        Centro centro = centroService.create();

        
        centro.setEstadoCentro(EstadoCentro.PERTENECE_A_FPEMPRESA);
        centro.setNombre(GeneradorDatosAleatorios.getNombreCentroAleatorio());
        centro.setDireccion(createDireccionAleatoria());
        centro.setContacto(createContactoCentroAleatorio(centro));
        
        return centro;
    }

    public Empresa createEmpresaAleatoria() throws BusinessException {
        String[] tiposEmpresa = {"Sociedad Anonima", "Sociedad Limitada", "Cooperativa", "Sociedad Laboral"};
        Centro[] centros = {getCentroAleatorio(), null};

        CRUDService<Empresa, Integer> empresaService = crudServiceFactory.getService(Empresa.class);

        String nombreEmpresa = GeneradorDatosAleatorios.getNombreEmpresa() + " " + GeneradorDatosAleatorios.getApellidoPersona();
        Empresa empresa = empresaService.create();

        empresa.setNombreComercial(nombreEmpresa);
        empresa.setRazonSocial(nombreEmpresa + " " + GeneradorDatosAleatorios.getAleatorio(tiposEmpresa));
        empresa.setDireccion(createDireccionAleatoria());
        empresa.setContacto(createContactoEmpresaAleatorio(empresa));
        empresa.setCentro((Centro) GeneradorDatosAleatorios.getAleatorio(centros));

        if (empresa.getCentro() != null) {
            empresa.getDireccion().setMunicipio(getMunicipioAleatorio(empresa.getCentro().getDireccion().getMunicipio().getProvincia()));
        }

        empresa.setCif(GeneradorDatosAleatorios.getCif());

        return empresa;
    }

    public Oferta createOfertaAleatoria(Empresa empresa) throws BusinessException {
        CRUDService<Oferta, Integer> ofertaService = crudServiceFactory.getService(Oferta.class);
        Oferta oferta = ofertaService.create();

        if (empresa == null) {
            empresa = getEmpresaAleatoria();
        }

        Boolean[] cerradas = {false, false, false, true};

        String puesto = GeneradorDatosAleatorios.getNombrePuestoTrabajo();

        oferta.setEmpresa(empresa);
        oferta.setCerrada((Boolean) GeneradorDatosAleatorios.getAleatorio(cerradas));
        oferta.setFamilia(getFamiliaAleatoria());
        Set<Ciclo> ciclos = new HashSet<Ciclo>();
        ciclos.add(getCicloAleatorio(oferta.getFamilia()));
        oferta.setCiclos(ciclos);

        Contacto contacto = new Contacto();
        if (empresa.getCentro() != null) {
            contacto.setTextoLibre("Enviar un correo a 'rrrhh@" + empresa.getNombreComercial().replaceAll("\\s+", "").toLowerCase() + ".com' con el asunto 'oferta trabajo de " + puesto + "'.\nIncluir curriculum en formato PDF");
        }
        oferta.setContacto(contacto);
        oferta.setDescripcion("Oferta laboral para trabajar como '" + puesto + "' a jornada completa. \nNo es necesaria experiencia.");
        oferta.setMunicipio(getMunicipioAleatorio(empresa.getDireccion().getMunicipio().getProvincia()));
        oferta.setPuesto(puesto);

        return oferta;
    }

    public Usuario createUsuarioAleatorio(TipoUsuario tipoUsuario) throws BusinessException {
        Random random = new Random(System.currentTimeMillis());
        CRUDService<Usuario, Integer> usuarioService = crudServiceFactory.getService(Usuario.class);
        String[] correos = {"gmail.com", "yahoo.com", "hotmail.com"};

        Usuario usuario = usuarioService.create();

        String ape1 = GeneradorDatosAleatorios.getApellidoPersona();
        String ape2 = GeneradorDatosAleatorios.getApellidoPersona();
        String nombre = GeneradorDatosAleatorios.getNombrePersona();
        String email = ape1.substring(0, 3) + ape2.substring(0, 3) + nombre.substring(0, 3) + random.nextInt(9999) + "@" + GeneradorDatosAleatorios.getAleatorio(correos);

        usuario.setApellidos(ape1 + " " + ape2);
        usuario.setNombre(nombre);
        usuario.setEmail(email.toLowerCase());
        usuario.setPassword("a");
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        switch (usuario.getTipoUsuario()) {
            case EMPRESA:
                usuario.setEmpresa(getEmpresaSinCentroAleatoria());
                break;
            case CENTRO:
                usuario.setCentro(getCentroAleatorio());
                break;
            case TITULADO:
                usuario.setTitulado(createTituladoAleatorio());
                break;
        }

        return usuario;
    }

    private Titulado createTituladoAleatorio() throws BusinessException {
        CRUDService<Titulado, Integer> tituladoService = crudServiceFactory.getService(Titulado.class);
        Titulado titulado = tituladoService.create();
        String[] permisosConducir = {"A", "A", "B", "B", "C1", "C", null, null, null, null, null, null, null, null, null, null, null, null, null, null};

        titulado.setDireccion(createDireccionAleatoria());
        titulado.setFechaNacimiento(GeneradorDatosAleatorios.getFecha(18, 35));
        titulado.setTipoDocumento(TipoDocumento.NIF_NIE);
        titulado.setNumeroDocumento(GeneradorDatosAleatorios.getNif());
        titulado.setPermisosConducir(GeneradorDatosAleatorios.getAleatorio(permisosConducir));
        titulado.setTelefono(GeneradorDatosAleatorios.getTelefono());
        titulado.setTelefonoAlternativo(GeneradorDatosAleatorios.getTelefono());
        titulado.setResumen(GeneradorDatosAleatorios.getResumenPersona());
        titulado.setFormacionesAcademicas(createFormacionesAcademicasAleatorias(titulado));
        titulado.setExperienciasLaborales(createExperienciasLaboralesAleatorias(titulado));
        titulado.setTitulosIdiomas(createTitulosIdiomasAleatorios(titulado));

        return titulado;
    }

    private Contacto createContactoAleatorio() throws BusinessException {
        Contacto contacto = new Contacto();
        contacto.setPersona(GeneradorDatosAleatorios.getNombrePersona() + " " + GeneradorDatosAleatorios.getApellidoPersona() + " " + GeneradorDatosAleatorios.getApellidoPersona());
        contacto.setTelefono(GeneradorDatosAleatorios.getTelefono());
        contacto.setFax(GeneradorDatosAleatorios.getTelefono());

        return contacto;
    }
    
    private Contacto createContactoEmpresaAleatorio(Empresa empresa) throws BusinessException {
        Contacto contacto = createContactoAleatorio();

        contacto.setUrl("http://www." + empresa.getNombreComercial().replaceAll("\\s+", "").toLowerCase()+".com");
        contacto.setEmail("info@"+ empresa.getNombreComercial().replaceAll("\\s+", "").toLowerCase()+".com");
        
        return contacto;
    }    

    private Contacto createContactoCentroAleatorio(Centro centro) throws BusinessException {
        Contacto contacto = createContactoAleatorio();

        contacto.setUrl("http://www." + centro.getNombre().replaceAll("\\s+", "").toLowerCase()+".com");
        contacto.setEmail("secretaria@"+ centro.getNombre().replaceAll("\\s+", "").toLowerCase()+".com");
        
        return contacto;
    }    
    
    private Set<FormacionAcademica> createFormacionesAcademicasAleatorias(Titulado titulado) throws BusinessException {
        Set<FormacionAcademica> formacionesAcademicas = new HashSet<FormacionAcademica>();

        Integer[] numPosiblesTitulos = {1, 1, 1, 2, 2, 3, 3};
        int numTitulos = (Integer) GeneradorDatosAleatorios.getAleatorio(numPosiblesTitulos);
        for (int i = 0; i < numTitulos; i++) {
            formacionesAcademicas.add(createFormacionAcademicaAleatoria(titulado));
        }

        return formacionesAcademicas;
    }

    private FormacionAcademica createFormacionAcademicaAleatoria(Titulado titulado) throws BusinessException {
        CRUDService<FormacionAcademica, Integer> formacionAcademicaService = crudServiceFactory.getService(FormacionAcademica.class);
        FormacionAcademica formacionAcademica = formacionAcademicaService.create();
        TipoFormacionAcademica[] tipoFormacionAcademica = {TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.TITULO_UNIVERSITARIO};
        Boolean[] nuevoCentro = {false, false, false, false, false, false, true};

        formacionAcademica.setTitulado(titulado);
        formacionAcademica.setTipoFormacionAcademica((TipoFormacionAcademica) GeneradorDatosAleatorios.getAleatorio(tipoFormacionAcademica));
        formacionAcademica.setFecha(GeneradorDatosAleatorios.getFecha(0, 5));
        switch (formacionAcademica.getTipoFormacionAcademica()) {
            case CICLO_FORMATIVO:

                formacionAcademica.setCiclo(getCicloAleatorio());

                if ((boolean) GeneradorDatosAleatorios.getAleatorio(nuevoCentro)) {
                    CRUDService<Centro, Integer> centroService = crudServiceFactory.getService(Centro.class);
                    formacionAcademica.setCentro(centroService.read(-1));
                    formacionAcademica.setOtroCentro(GeneradorDatosAleatorios.getNombreCentroAleatorio());
                } else {
                    Centro centro = getCentroAleatorio(titulado.getDireccion().getMunicipio().getProvincia());
                    if (centro == null) {
                        centro = getCentroAleatorio();
                    }
                    formacionAcademica.setCentro(centro);
                    Boolean[] certificadoTitulo = {true, true, false};
                    formacionAcademica.setCertificadoTitulo((Boolean) GeneradorDatosAleatorios.getAleatorio(certificadoTitulo));
                }

                break;
            case TITULO_UNIVERSITARIO:
                formacionAcademica.setOtroCentro("Universidad de " + getProvinciaAleatoria().getDescripcion());
                formacionAcademica.setOtroTitulo(GeneradorDatosAleatorios.getCarreraUniversitaria());
                break;
            default:
                throw new RuntimeException("Tipo de formacion academicas no soportado:" + formacionAcademica.getTipoFormacionAcademica());
        }

        return formacionAcademica;
    }

    private Set<ExperienciaLaboral> createExperienciasLaboralesAleatorias(Titulado titulado) throws BusinessException {
        Set<ExperienciaLaboral> experienciasLaborales = new HashSet<ExperienciaLaboral>();

        Integer[] numPosiblesExperienciasLaborales = {0, 0, 0, 0, 1, 1, 1, 2, 2};
        int numExperienciasLaborales = (Integer) GeneradorDatosAleatorios.getAleatorio(numPosiblesExperienciasLaborales);
        for (int i = 0; i < numExperienciasLaborales; i++) {
            experienciasLaborales.add(createExperienciaLaboralAleatoria(titulado));
        }

        return experienciasLaborales;
    }

    private ExperienciaLaboral createExperienciaLaboralAleatoria(Titulado titulado) throws BusinessException {
        CRUDService<ExperienciaLaboral, Integer> experienciaLaboralService = crudServiceFactory.getService(ExperienciaLaboral.class);
        ExperienciaLaboral experienciaLaboral = experienciaLaboralService.create();
        Random random = new Random();

        Calendar calendarFin = new GregorianCalendar();
        calendarFin.setTime(GeneradorDatosAleatorios.getFecha(0, 5));
        calendarFin.set(Calendar.MILLISECOND, 0);
        calendarFin.set(Calendar.SECOND, 0);
        calendarFin.set(Calendar.MINUTE, 0);
        calendarFin.set(Calendar.HOUR_OF_DAY, 0);
        Date fechaFin = calendarFin.getTime();

        Calendar calendarInicio = new GregorianCalendar();
        calendarInicio.setTime(fechaFin);
        calendarInicio.add(Calendar.DATE, -(random.nextInt(90) + 7));
        calendarInicio.set(Calendar.MILLISECOND, 0);
        calendarInicio.set(Calendar.SECOND, 0);
        calendarInicio.set(Calendar.MINUTE, 0);
        calendarInicio.set(Calendar.HOUR_OF_DAY, 0);
        Date fechaInicio = calendarInicio.getTime();

        experienciaLaboral.setTitulado(titulado);
        experienciaLaboral.setFechaInicio(fechaInicio);
        experienciaLaboral.setFechaFin(fechaFin);
        experienciaLaboral.setNombreEmpresa(GeneradorDatosAleatorios.getNombreEmpresa());
        experienciaLaboral.setPuestoTrabajo(GeneradorDatosAleatorios.getNombrePuestoTrabajo());
        experienciaLaboral.setDescripcion("Realizar trabajos relacionados con el puesto de trabajo de " + experienciaLaboral.getPuestoTrabajo());

        return experienciaLaboral;
    }

    private Set<TituloIdioma> createTitulosIdiomasAleatorios(Titulado titulado) throws BusinessException {
        Set<TituloIdioma> titulosIdiomas = new HashSet<TituloIdioma>();

        Integer[] numPosiblesIdiomas = {0, 0, 0, 1, 1, 2};
        int numIdiomas = (Integer) GeneradorDatosAleatorios.getAleatorio(numPosiblesIdiomas);
        for (int i = 0; i < numIdiomas; i++) {
            titulosIdiomas.add(createTituloIdiomaAleatorio(titulado));
        }

        return titulosIdiomas;
    }

    private TituloIdioma createTituloIdiomaAleatorio(Titulado titulado) throws BusinessException {
        CRUDService<TituloIdioma, Integer> tituloIdiomaService = crudServiceFactory.getService(TituloIdioma.class);
        TituloIdioma tituloIdioma = tituloIdiomaService.create();
        String[] otroIdioma = {"Chino", "Ruso", "Armenio", "Italiano", "Árabe", "Griego", "Japonés"};

        tituloIdioma.setTitulado(titulado);
        tituloIdioma.setFecha(GeneradorDatosAleatorios.getFecha(0, 5));
        tituloIdioma.setIdioma((Idioma) GeneradorDatosAleatorios.getAleatorio(Idioma.values()));
        if (tituloIdioma.getIdioma() == Idioma.OTRO) {
            tituloIdioma.setOtroIdioma(GeneradorDatosAleatorios.getAleatorio(otroIdioma));
        }
        tituloIdioma.setNivelIdioma((NivelIdioma) GeneradorDatosAleatorios.getAleatorio(NivelIdioma.values()));

        return tituloIdioma;
    }

    private Direccion createDireccionAleatoria() throws BusinessException {
        Direccion direccion = new Direccion();
        direccion.setDatosDireccion(GeneradorDatosAleatorios.getDireccion());
        direccion.setMunicipio(getMunicipioAleatorio());

        return direccion;
    }

    private Familia getFamiliaAleatoria() throws BusinessException {
        CRUDService<Familia, Integer> familiaService = crudServiceFactory.getService(Familia.class);

        Familia familia = GeneradorDatosAleatorios.getAleatorio(familiaService.search(null));

        return familia;
    }

    private Ciclo getCicloAleatorio() throws BusinessException {
        CRUDService<Ciclo, Integer> cicloService = crudServiceFactory.getService(Ciclo.class);

        Ciclo ciclo = GeneradorDatosAleatorios.getAleatorio(cicloService.search(null));

        return ciclo;
    }
    
    private Ciclo getCicloAleatorio(Familia familia) throws BusinessException {
        CRUDService<Ciclo, Integer> cicloService = crudServiceFactory.getService(Ciclo.class);
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("familia.idFamilia", familia.getIdFamilia()));
        
        Ciclo ciclo = GeneradorDatosAleatorios.getAleatorio(cicloService.search(filters));

        return ciclo;
    }
        

    private Provincia getProvinciaAleatoria() throws BusinessException {
        CRUDService<Provincia, Integer> provinciaService = crudServiceFactory.getService(Provincia.class);

        Provincia provincia = GeneradorDatosAleatorios.getAleatorio(provinciaService.search(null));

        return provincia;
    }

    private Empresa getEmpresaAleatoria() throws BusinessException {
        CRUDService<Empresa, Integer> empresaService = crudServiceFactory.getService(Empresa.class);

        Empresa empresa = GeneradorDatosAleatorios.getAleatorio(empresaService.search(null));

        return empresa;
    }

    private Municipio getMunicipioAleatorio() throws BusinessException {
        CRUDService<Municipio, Integer> municipioService = crudServiceFactory.getService(Municipio.class);

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("provincia.idProvincia", getProvinciaAleatoria().getIdProvincia()));

        Municipio municipio = GeneradorDatosAleatorios.getAleatorio(municipioService.search(filters));

        return municipio;
    }

    private Municipio getMunicipioAleatorio(Provincia provincia) throws BusinessException {
        CRUDService<Municipio, Integer> municipioService = crudServiceFactory.getService(Municipio.class);

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("provincia.idProvincia", provincia.getIdProvincia()));

        Municipio municipio = GeneradorDatosAleatorios.getAleatorio(municipioService.search(filters));

        return municipio;
    }

    private Centro getCentroAleatorio() throws BusinessException {
        CRUDService<Centro, Integer> centroService = crudServiceFactory.getService(Centro.class);

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("idCentro", 0, FilterOperator.gt));
        Centro centro = GeneradorDatosAleatorios.getAleatorio(centroService.search(filters));

        return centro;
    }

    private Centro getCentroAleatorio(Provincia provincia) throws BusinessException {
        CRUDService<Centro, Integer> centroService = crudServiceFactory.getService(Centro.class);

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("idCentro", 0, FilterOperator.gt));
        filters.add(new Filter("direccion.municipio.provincia.idProvincia", provincia.getIdProvincia()));
        List<Centro> centros = centroService.search(filters);

        Centro centro;
        if (centros.size() == 0) {
            centro = null;
        } else {
            centro = GeneradorDatosAleatorios.getAleatorio(centros);
        }

        return centro;
    }

    private Empresa getEmpresaSinCentroAleatoria() throws BusinessException {
        CRUDService<Empresa, Integer> empresaService = crudServiceFactory.getService(Empresa.class);
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(new Filter("centro", true, FilterOperator.isnull));
        Empresa empresa = GeneradorDatosAleatorios.getAleatorio(empresaService.search(filters));

        return empresa;
    }

}
