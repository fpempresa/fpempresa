/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.titulado;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.fpempresa.modelo.comun.geo.Municipio;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
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
import es.logongas.ix3.dao.DataSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rhuffus
 */
public class ImportarTituladosJsonDeserializer extends JsonDeserializer<List<Usuario>> {

    private DAOFactory daoFactory;
    private final Map<Integer, Municipio> listadoMunicipios = new HashMap();
    private final Map<Integer, Centro> listadoCentros = new HashMap();
    private final Map<Integer, Ciclo> listadoCiclos = new HashMap();
    private final DataSession dataSession;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public ImportarTituladosJsonDeserializer(DataSession dataSession, DAOFactory daoFactory) {
        this.dataSession = dataSession;
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Usuario> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        List<Usuario> listadoUsuarios = new ArrayList();

        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode listadoUsuariosJsonNode = objectCodec.readTree(jsonParser);

        if (!listadoUsuariosJsonNode.isArray()) {
            throw new IOException("El documento no es un listado de usuarios");
        }

        Iterator<JsonNode> iteradorUsuariosJsonNode = listadoUsuariosJsonNode.elements();
        while (iteradorUsuariosJsonNode.hasNext()) {

            JsonNode usuarioJsonNode = iteradorUsuariosJsonNode.next();

            // Obtener datos del Usuario
            final String email = !usuarioJsonNode.get("email").isNull() ? usuarioJsonNode.get("email").asText() : null;
            final String nombre = !usuarioJsonNode.get("nombre").isNull() ? usuarioJsonNode.get("nombre").asText() : null;
            final String apellidos = !usuarioJsonNode.get("apellidos").isNull() ? usuarioJsonNode.get("apellidos").asText() : null;
            final String password = !usuarioJsonNode.get("password").isNull() ? usuarioJsonNode.get("password").asText() : null;

            // Creamos el objeto Usuario
            final Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setPassword(password);
            usuario.setTipoUsuario(TipoUsuario.TITULADO);
            usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
            usuario.setValidadoEmail(true);
            usuario.setFecha(new Date());

            // Obtener datos del Titulado
            JsonNode tituladoJsonNode = usuarioJsonNode.get("titulado");
            if (tituladoJsonNode.isNull()) {
                throw new JsonMappingException(jsonParser, "No se ha encontrado el objeto titulado dentro de uno de los usuarios del listado");
            }

            final String fechaNacimiento = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("fechaNacimiento").asText() : null;
            final String telefono = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("telefono").asText() : null;
            final String telefonoAlternativo = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("telefonoAlternativo").asText() : null;
            final String tipoDocumento = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("tipoDocumento").asText() : null;
            final String numeroDocumento = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("numeroDocumento").asText() : null;
            final String resumen = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("resumen").asText() : null;
            final String otrasCompetencias = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("otrasCompetencias").asText() : null;
            final String permisosConducir = !tituladoJsonNode.get("fechaNacimiento").isNull() ? tituladoJsonNode.get("permisosConducir").asText() : null;

            // Creamos el objeto Titulado
            final Titulado titulado = new Titulado();
            titulado.setFechaNacimiento(this.convertirFecha(fechaNacimiento));
            titulado.setTelefono(telefono);
            titulado.setTelefonoAlternativo(telefonoAlternativo);
            titulado.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
            titulado.setNumeroDocumento(numeroDocumento);
            titulado.setTitulosIdiomas(new HashSet());
            titulado.setExperienciasLaborales(new HashSet());
            titulado.setFormacionesAcademicas(new HashSet());
            titulado.setResumen(resumen);
            titulado.setOtrasCompetencias(otrasCompetencias);
            titulado.setPermisosConducir(permisosConducir);

            // Asignamos el Titulado al Usuario
            usuario.setTitulado(titulado);

            JsonNode direccionTituladoJsonNode = tituladoJsonNode.get("direccion");
            if (!direccionTituladoJsonNode.isNull()) {

                // Obtener la dirección del Titulado
                final String datosDireccion = !direccionTituladoJsonNode.get("datosDireccion").isNull() ? direccionTituladoJsonNode.get("datosDireccion").asText() : null;
                final int idMunicipio = !direccionTituladoJsonNode.get("idMunicipio").isNull() ? direccionTituladoJsonNode.get("idMunicipio").asInt() : null;

                // Creamos el objeto Direccion del Titulado
                final Direccion direccion = new Direccion();
                direccion.setDatosDireccion(datosDireccion);
                direccion.setMunicipio(this.buscarMunicipioPorId(idMunicipio));

                // Asignamos la Direccion al Titulado
                titulado.setDireccion(direccion);
            }

            // Obtener los TituloIdioma's del Titulado
            JsonNode titulosIdiomasJsonNode = tituladoJsonNode.get("titulosIdiomas");
            if (!titulosIdiomasJsonNode.isArray()) {
                throw new IOException("Uno de los nodos titulosIdiomas no es un listado");
            }

            Iterator<JsonNode> iteradorTitulosIdiomasJsonNode = titulosIdiomasJsonNode.elements();
            while (iteradorTitulosIdiomasJsonNode.hasNext()) {

                JsonNode tituloIdiomaJsonNode = iteradorTitulosIdiomasJsonNode.next();

                // Leemos un TituloIdioma del listado
                final String nivelIdioma = !tituloIdiomaJsonNode.get("nivelIdioma").isNull() ? tituloIdiomaJsonNode.get("nivelIdioma").asText() : null;
                final String idioma = !tituloIdiomaJsonNode.get("idioma").isNull() ? tituloIdiomaJsonNode.get("idioma").asText() : null;
                final String otroIdioma = !tituloIdiomaJsonNode.get("otroIdioma").isNull() ? tituloIdiomaJsonNode.get("otroIdioma").asText() : null;
                final String fecha = !tituloIdiomaJsonNode.get("fecha").isNull() ? tituloIdiomaJsonNode.get("fecha").asText() : null;

                // Creamos el objeto TituloIdioma
                final TituloIdioma tituloIdioma = new TituloIdioma();
                tituloIdioma.setNivelIdioma(NivelIdioma.valueOf(nivelIdioma));
                tituloIdioma.setIdioma(Idioma.valueOf(idioma));
                tituloIdioma.setOtroIdioma(tituloIdioma.getIdioma() == Idioma.OTRO ? otroIdioma : null);
                tituloIdioma.setFecha(this.convertirFecha(fecha));
                tituloIdioma.setTitulado(titulado);

                // Asignamos el TituloIdioma al listado TituloIdioma's del Titulado
                titulado.getTitulosIdiomas().add(tituloIdioma);

            }

            // Obtener las ExperienciaLaboral'es del Titulado
            JsonNode experienciasLaboralesJsonNode = tituladoJsonNode.get("experienciasLaborales");
            if (!experienciasLaboralesJsonNode.isArray()) {
                throw new IOException("Uno de los nodos experienciasLaborales no es un listado");
            }

            Iterator<JsonNode> iteradorExperineciasLaboralesJsonNode = experienciasLaboralesJsonNode.elements();
            while (iteradorExperineciasLaboralesJsonNode.hasNext()) {

                JsonNode experienciaLaboralJsonNode = iteradorExperineciasLaboralesJsonNode.next();

                // Leemos una ExperienciaLaboral del listado
                final String nombreEmpresa = !experienciaLaboralJsonNode.get("nombreEmpresa").isNull() ? experienciaLaboralJsonNode.get("nombreEmpresa").asText() : null;
                final String fechaInicio = !experienciaLaboralJsonNode.get("fechaInicio").isNull() ? experienciaLaboralJsonNode.get("fechaInicio").asText() : null;
                final String fechaFin = !experienciaLaboralJsonNode.get("fechaFin").isNull() ? experienciaLaboralJsonNode.get("fechaFin").asText() : null;
                final String puestoTrabajo = !experienciaLaboralJsonNode.get("puestoTrabajo").isNull() ? experienciaLaboralJsonNode.get("puestoTrabajo").asText() : null;
                final String descripcion = !experienciaLaboralJsonNode.get("descripcion").isNull() ? experienciaLaboralJsonNode.get("descripcion").asText() : null;

                // Creamos el objeto ExperienciaLaboral
                final ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral();
                experienciaLaboral.setNombreEmpresa(nombreEmpresa);
                experienciaLaboral.setFechaInicio(this.convertirFecha(fechaInicio));
                experienciaLaboral.setFechaFin(this.convertirFecha(fechaFin));
                experienciaLaboral.setPuestoTrabajo(puestoTrabajo);
                experienciaLaboral.setDescripcion(descripcion);
                experienciaLaboral.setTitulado(titulado);

                // Asignamos la ExperienciaLaboral al listado de ExperienciaLaboral'es del Titulado
                titulado.getExperienciasLaborales().add(experienciaLaboral);

            }

            // Obtener las FormacionAcademaica's del Titulado
            JsonNode formacionesAcademicasJsonNode = tituladoJsonNode.get("formacionesAcademicas");
            if (!formacionesAcademicasJsonNode.isArray()) {
                throw new IOException("Uno de los nodos formacionesAcademicas no es un listado");
            }

            Iterator<JsonNode> iteradorFormacionesAcademicasJsonNode = formacionesAcademicasJsonNode.elements();
            while (iteradorFormacionesAcademicasJsonNode.hasNext()) {

                JsonNode formacionAcademicaJsonNode = iteradorFormacionesAcademicasJsonNode.next();
                // Leemos una FormacionAcademaica del listado
                final String tipoFormacionAcademica = !formacionAcademicaJsonNode.get("tipoFormacionAcademica").isNull() ? formacionAcademicaJsonNode.get("tipoFormacionAcademica").asText() : null;
                final int idCentro = !formacionAcademicaJsonNode.get("idCentro").isNull() ? formacionAcademicaJsonNode.get("idCentro").asInt() : null;
                final int idCiclo = !formacionAcademicaJsonNode.get("idCiclo").isNull() ? formacionAcademicaJsonNode.get("idCiclo").asInt() : null;
                final String otroCentro = !formacionAcademicaJsonNode.get("otroCentro").isNull() ? formacionAcademicaJsonNode.get("otroCentro").asText() : null;
                final String otroTitulo = !formacionAcademicaJsonNode.get("otroTitulo").isNull() ? formacionAcademicaJsonNode.get("otroTitulo").asText() : null;
                final String fecha = !formacionAcademicaJsonNode.get("fecha").isNull() ? formacionAcademicaJsonNode.get("fecha").asText() : null;

                // Creamos el objeto FormacionAcademaica
                final FormacionAcademica formacionAcademica = new FormacionAcademica();
                formacionAcademica.setTipoFormacionAcademica(TipoFormacionAcademica.valueOf(tipoFormacionAcademica));
                formacionAcademica.setCentro(this.buscarCentroPorId(idCentro));
                formacionAcademica.setCiclo(this.buscarCicloPorId(idCiclo));
                formacionAcademica.setOtroCentro(otroCentro);
                formacionAcademica.setOtroTitulo(otroTitulo);
                formacionAcademica.setFecha(this.convertirFecha(fecha));
                formacionAcademica.setTitulado(titulado);

                // Asignamos la FormacionAcademaica al listado de FormacionAcademaica's del Titulado
                titulado.getFormacionesAcademicas().add(formacionAcademica);

            }

            listadoUsuarios.add(usuario);

        }

        return listadoUsuarios;
    }

    private Municipio buscarMunicipioPorId(int municipioId) throws IOException {
        Municipio municipio;
        // Primero buscamos en el listado de Municipios por si ya lo tenemos
        municipio = this.listadoMunicipios.get(municipioId);
        if (municipio != null) {
            return municipio;
        } else {
            try {
                // Si no se encuentra en el listado lo obtenemos de la base de datos
                municipio = this.daoFactory.getDAO(Municipio.class).read(dataSession, municipioId);
            } catch (BusinessException ex) {
                throw new IOException("Error al buscar el Municipio en la base de datos");
            }
            if (municipio != null) {
                this.listadoMunicipios.put(municipioId, municipio);
                return municipio;
            } else {
                throw new IOException("Uno de los municipios especifiacados no existe");
            }
        }
    }

    private Centro buscarCentroPorId(int centroId) throws IOException {
        Centro centro;
        // Primero buscamos en el listado de centros por si ya lo tenemos
        centro = this.listadoCentros.get(centroId);
        if (centro != null) {
            return centro;
        } else {
            try {
                // Si no se encuentra en el listado lo obtenemos de la base de datos
                centro = this.daoFactory.getDAO(Centro.class).read(dataSession, centroId);
            } catch (BusinessException ex) {
                throw new IOException("Error al buscar el Centro en la base de datos", ex);
            }
            if (centro != null) {
                this.listadoCentros.put(centroId, centro);
                return centro;
            } else {
                throw new IOException("Uno de los centros especifiacados no existe");
            }
        }
    }

    private Ciclo buscarCicloPorId(int cicloId) throws IOException {
        Ciclo ciclo;
        // Primero buscamos en el listado de ciclos por si ya lo tenemos
        ciclo = this.listadoCiclos.get(cicloId);
        if (ciclo != null) {
            return ciclo;
        } else {
            try {
                // Si no se encuentra en el listado lo obtenemos de la base de datos
                ciclo = this.daoFactory.getDAO(Ciclo.class).read(dataSession, cicloId);
            } catch (BusinessException ex) {
                throw new IOException("Error al buscar el Ciclo en la base de datos", ex);
            }
            if (ciclo != null) {
                this.listadoCiclos.put(cicloId, ciclo);
                return ciclo;
            } else {
                throw new IOException("Uno de los ciclos especifiacados no existe");
            }
        }
    }

    private Date convertirFecha(String fecha) throws IOException {
        try {
            return this.simpleDateFormat.parse(fecha);
        } catch (ParseException ex) {
            throw new IOException("La fecha no se pudo convertir", ex);
        }
    }

}
