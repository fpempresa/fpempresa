/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.titulado.ExperienciaLaboral;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Idioma;
import es.logongas.fpempresa.modelo.titulado.NivelIdioma;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.modelo.titulado.TituloIdioma;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Genera nombre aleatorios a partir de una lista de nombres.
 *
 * @author logongas
 */
public class GeneradorDatosAleatorios {

    private GeneradorDatosAleatorios() {
    }

    static final public Usuario createUsuarioAleatorio(TipoUsuario tipoUsuario) {
        Random random = new Random(System.currentTimeMillis());
        String[] correos = {"gmail.com", "yahoo.com", "hotmail.com"};

        Usuario usuario = new Usuario();

        String ape1 = GeneradorDatosAleatorios.getApellidoPersona();
        String ape2 = GeneradorDatosAleatorios.getApellidoPersona();
        String nombre = GeneradorDatosAleatorios.getNombrePersona();
        String email = ape1.substring(0, 3) + ape2.substring(0, 3) + nombre.substring(0, 3) + random.nextInt(9999) + "@" + GeneradorDatosAleatorios.getAleatorio(correos);

        usuario.setApellidos(ape1 + " " + ape2);
        usuario.setNombre(nombre);
        usuario.setEmail(email.toLowerCase());
        usuario.setPassword("Qw34$rt5qqqQ");
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        usuario.setAceptarEnvioCorreos(true);

        return usuario;
    }

    static final public Centro createCentroAleatorio() {
        Centro centro = new Centro();
        centro.setEstadoCentro(EstadoCentro.PERTENECE_A_FPEMPRESA);
        centro.setNombre(GeneradorDatosAleatorios.getNombreCentroAleatorio());
        centro.setDireccion(createDireccionAleatoria());
        centro.setContacto(createContactoCentroAleatorio(centro));

        return centro;
    }

    static final public Empresa createEmpresaAleatoria(Centro centro) {
        String[] tiposEmpresa = {"Sociedad Anonima", "Sociedad Limitada", "Cooperativa", "Sociedad Laboral"};

        String nombreEmpresa = GeneradorDatosAleatorios.getNombreEmpresa() + " " + GeneradorDatosAleatorios.getApellidoPersona();
        Empresa empresa = new Empresa();

        empresa.setNombreComercial(nombreEmpresa);
        empresa.setRazonSocial(nombreEmpresa + " " + GeneradorDatosAleatorios.getAleatorio(tiposEmpresa));
        empresa.setDireccion(createDireccionAleatoria());
        if (centro != null) {
            empresa.getDireccion().setMunicipio(centro.getDireccion().getMunicipio());
        }
        empresa.setContacto(createContactoEmpresaAleatorio(empresa));
        empresa.setCentro(centro);

        empresa.setCif(GeneradorDatosAleatorios.getCif());

        return empresa;
    }

    static final public Titulado createTituladoAleatorio() {
        Titulado titulado = new Titulado();
        String[] permisosConducir = {"A", "A", "B", "B", "C1", "C", null, null, null, null, null, null, null, null, null, null, null, null, null, null};

        titulado.setDireccion(createDireccionAleatoria());
        titulado.setFechaNacimiento(GeneradorDatosAleatorios.getFecha(18, 35));
        titulado.setTipoDocumento(TipoDocumento.NIF_NIE);
        titulado.setNumeroDocumento(GeneradorDatosAleatorios.getNif());
        titulado.setPermisosConducir(GeneradorDatosAleatorios.getAleatorio(permisosConducir));
        titulado.setTelefono(GeneradorDatosAleatorios.getTelefono());
        titulado.setTelefonoAlternativo(GeneradorDatosAleatorios.getTelefono());
        titulado.setResumen(GeneradorDatosAleatorios.getResumenPersona());

        return titulado;
    }

    static final public Direccion createDireccionAleatoria() {
        Direccion direccion = new Direccion();
        direccion.setDatosDireccion(GeneradorDatosAleatorios.getDireccion());
        direccion.setMunicipio(getMunicipioAleatorio());

        return direccion;
    }

    static final public Contacto createContactoAleatorio() {
        Contacto contacto = new Contacto();
        contacto.setPersona(GeneradorDatosAleatorios.getNombrePersona() + " " + GeneradorDatosAleatorios.getApellidoPersona() + " " + GeneradorDatosAleatorios.getApellidoPersona());
        contacto.setTelefono(GeneradorDatosAleatorios.getTelefono());
        contacto.setFax(GeneradorDatosAleatorios.getTelefono());

        return contacto;
    }

    static final public TituloIdioma createTituloIdiomaAleatorio(Titulado titulado) {
        TituloIdioma tituloIdioma = new TituloIdioma();
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

    static final public FormacionAcademica createFormacionAcademicaAleatoria(Titulado titulado) {
        FormacionAcademica formacionAcademica = new FormacionAcademica();
        TipoFormacionAcademica[] tipoFormacionAcademica = {TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.CICLO_FORMATIVO, TipoFormacionAcademica.TITULO_UNIVERSITARIO};
        Boolean[] nuevoCentro = {false, false, false, false, false, false, true};

        formacionAcademica.setTitulado(titulado);
        formacionAcademica.setTipoFormacionAcademica((TipoFormacionAcademica) GeneradorDatosAleatorios.getAleatorio(tipoFormacionAcademica));
        formacionAcademica.setFecha(GeneradorDatosAleatorios.getFecha(0, 5));
        switch (formacionAcademica.getTipoFormacionAcademica()) {
            case CICLO_FORMATIVO:
                Ciclo ciclo = new Ciclo();
                ciclo.setIdCiclo(10);

                formacionAcademica.setCiclo(ciclo);

                Centro centro = new Centro();
                centro.setIdCentro(-1);
                formacionAcademica.setCentro(centro);
                formacionAcademica.setOtroCentro(GeneradorDatosAleatorios.getNombreCentroAleatorio());

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

    static final public ExperienciaLaboral createExperienciaLaboralAleatoria(Titulado titulado) {
        ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral();
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

    static final public Provincia getProvinciaAleatoria() {
        Random random = new Random();

        int i = random.nextInt(provincias.length);
        String nombre = provincias[i];

        Provincia provincia = new Provincia();
        provincia.setIdProvincia(i + 1);
        provincia.setDescripcion(nombre);
        return provincia;
    }

    static final public Municipio getMunicipioAleatorio() {
        Random random = new Random();

        int i = random.nextInt(municipios.length);
        String nombre = municipios[i];

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(i + 1);
        municipio.setDescripcion(nombre);
        municipio.setProvincia(getProvinciaAleatoria());
        return municipio;
    }

    static final public Municipio getMunicipioAleatorio(Provincia provincia) {
        Municipio municipio = getMunicipioAleatorio();
        municipio.setProvincia(provincia);
        return municipio;
    }

    static final public Contacto createContactoEmpresaAleatorio(Empresa empresa) {
        Contacto contacto = createContactoAleatorio();

        contacto.setUrl("http://www." + empresa.getNombreComercial().replaceAll("\\s+", "").toLowerCase() + ".com");
        contacto.setEmail("info@" + empresa.getNombreComercial().replaceAll("\\s+", "").toLowerCase() + ".com");

        return contacto;
    }

    static final public Contacto createContactoCentroAleatorio(Centro centro) {
        Contacto contacto = createContactoAleatorio();

        contacto.setUrl("http://www." + centro.getNombre().replaceAll("\\s+", "").toLowerCase() + ".com");
        contacto.setEmail("secretaria@" + centro.getNombre().replaceAll("\\s+", "").toLowerCase() + ".com");

        return contacto;
    }

    static final public String getNombreEmpresa() {
        return getAleatorio(empresas);
    }

    static final public String getNombrePersona() {
        return getAleatorio(nombres);
    }

    static final public String getApellidoPersona() {
        return getAleatorio(apellidos);
    }

    static final public String getDireccion() {
        return getAleatorio(direcciones);
    }

    static final public String getNombrePuestoTrabajo() {
        return getAleatorio(puestoTrabajo);
    }

    static final public String getNombreCentroAleatorio() {
        String[] tiposCentro = {"IES", "CIPFP"};
        String[] tiposPersona = {"Pintor", "", "", "Botanico", "Literato", "Arquitecto", "Poeta", "Escultor", "", "", "", "", "", "", ""};
        return getAleatorio(tiposCentro) + " " + getAleatorio(tiposPersona) + " " + getNombrePersona() + " " + getApellidoPersona();
    }

    static final public String getTelefono() {
        Random random = new Random();
        Integer[] prefix = {9, 6};

        return getAleatorio(prefix) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1);
    }

    static final public String getResumenPersona() {
        String[] pre = {"Especializado en", "Puedo", "Me gusta", "Mi pasión es", "Mi hobby es", "Disfruto al", "Siempre he querido", "He trabajo en"};

        return getAleatorio(pre) + " " + getAleatorio(verbo) + " " + getAleatorio(concepto) + " " + getAleatorio(complemento);
    }

    static final public String getCarreraUniversitaria() {
        return getAleatorio(carreras);
    }

    static final public String getCif() {
        Random random = new Random();
        String[] letrasIniciales = {"A", "B"};
        String cifSinLetra = getAleatorio(letrasIniciales) + "46" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1);

        return cifSinLetra + getLetraCif(cifSinLetra);
    }

    static final public String getNif() {
        Random random = new Random();
        String[] letrasIniciales = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String nifSinLetra = getAleatorio(letrasIniciales) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1);

        return nifSinLetra + getLetraNif(nifSinLetra);
    }

    static final public String getLetraNif(String nifSinLetra) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int valor;

        if (nifSinLetra.startsWith("X")) {
            //Es un NIE
            valor = Integer.parseInt(nifSinLetra.substring(1, nifSinLetra.length()));
        } else if (nifSinLetra.startsWith("Y")) {
            //Es un NIE
            valor = 10000000 + Integer.parseInt(nifSinLetra.substring(1, nifSinLetra.length()));
        } else if (nifSinLetra.startsWith("Z")) {
            //Es un NIE
            valor = 20000000 + Integer.parseInt(nifSinLetra.substring(1, nifSinLetra.length()));
        } else {
            //Es un NIF
            valor = Integer.parseInt(nifSinLetra.substring(0, nifSinLetra.length()));
        }

        return "" + letras.charAt(valor % 23);

    }

    static final public String getLetraCif(String cifSinLetra) {
        int a = 0;
        a = a + Integer.parseInt(cifSinLetra.substring(2, 3));
        a = a + Integer.parseInt(cifSinLetra.substring(4, 5));
        a = a + Integer.parseInt(cifSinLetra.substring(6, 7));

        int b = 0;
        for (int i = 0; i < 4; i++) {
            String parcialB = ((Integer.parseInt(cifSinLetra.substring((i * 2) + 1, (i * 2) + 2)) * 2) + "") + "0";
            b = b + Integer.parseInt(parcialB.substring(0, 1)) + Integer.parseInt(parcialB.substring(1, 2));
        }
        int c = a + b;
        String sc = ("0" + c);
        int e = Integer.parseInt(sc.substring(sc.length() - 1));
        int d = e;
        if (e != 0) {
            d = 10 - e;
        }

        String[] codigos = {"J", "A", "B", "C", "D", "E", "F", "G", "H", "I"};
        return codigos[d];
    }

    static final public Date getFecha(int minAnyos, int maxAnyos) {
        Random random = new Random();
        Calendar calendar = new GregorianCalendar();
        if (minAnyos == maxAnyos) {
            calendar.add(Calendar.YEAR, -minAnyos);
        } else {
            calendar.add(Calendar.YEAR, -(minAnyos + random.nextInt(maxAnyos - minAnyos)));
        }
        calendar.add(Calendar.DATE, -random.nextInt(360));

        return calendar.getTime();
    }

    static final public String getAleatorio(String[] datos) {
        Random random = new Random();

        return datos[random.nextInt(datos.length)];
    }

    static final public Object getAleatorio(Object[] datos) {
        Random random = new Random();

        return datos[random.nextInt(datos.length)];
    }

    static final public <T> T getAleatorio(List<T> datos) {
        Random random = new Random();

        return datos.get(random.nextInt(datos.size()));
    }

    private static final String[] municipios = {"Tobarra",
        "Valdeganga",
        "Vianos",
        "Villa de Ves",
        "Villalgordo del Júcar",
        "Villamalea",
        "Villapalacios",
        "Villarrobledo",
        "Villatoya",
        "Villavaliente",
        "Villaverde de Guadalimar",
        "Viveros",
        "Yeste",
        "Pozo Cañada",
        "Adsubia",
        "Agost",
        "Agres",
        "Aigües",
        "Albatera",
        "Alcalalí",
        "Alcocer de Planes",
        "Alcoleja",
        "Alcoy/Alcoi",
        "Alfafara",
        "Alfàs del Pi",
        "Algorfa",
        "Algueña",
        "Alicante/Alacant",
        "Almoradí",
        "Almudaina",
        "Alqueria",
        "Altea",
        "Aspe",
        "Balones",
        "Banyeres de Mariola",
        "Benasau",
        "Beneixama",
        "Benejúzar"

    };

    private static final String[] provincias = {"Araba/Álava",
        "Albacete",
        "Alicante/Alacant",
        "Almería",
        "Ávila",
        "Badajoz",
        "Balears, Illes",
        "Barcelona",
        "Burgos",
        "Cáceres",
        "Cádiz",
        "Castellón/Castelló",
        "Ciudad Real",
        "Córdoba",
        "Coruña, A",
        "Cuenca",
        "Girona",
        "Granada",
        "Guadalajara",
        "Gipuzkoa",
        "Huelva",
        "Huesca",
        "Jaén",
        "León",
        "Lleida",
        "Rioja, La",
        "Lugo",
        "Madrid",
        "Málaga",
        "Murcia",
        "Navarra",
        "Ourense",
        "Asturias",
        "Palencia",
        "Palmas, Las",
        "Pontevedra",
        "Salamanca",
        "Santa Cruz de Tenerife",
        "Cantabria",
        "Segovia",
        "Sevilla",
        "Soria",
        "Tarragona",
        "Teruel",
        "Toledo",
        "Valencia/Valéncia",
        "Valladolid",
        "Bizkaia",
        "Zamora",
        "Zaragoza",
        "Ceuta",
        "Melilla"};

    private static final String[] direcciones = {"c/San Vicente Ferrer, 15 bajo  ",
        "c/ San Pedro, 12",
        "c/ Luis Oliag, 69 1",
        "c/ Sanchis Sivera, 18 bajo",
        "Av.Naquera, 44-1",
        "Crta. CV416, s/n",
        "c/ Escultor Beltran 11",
        "c/ Altamira, 49",
        "c/ Blasco Ibañez, 192",
        "c/ Enginyer Balaguer 76-2-14",
        "Av. San Lorenzo, 39",
        "c/ Cervantes, 3     ",
        "Av. Gandia, 45",
        "Escultor Aixa, 12-2",
        "Urb. Los lagos 519 - Apt. Correos 69",
        "c/Dos de Mayo, 92 bajo        ",
        "c/ Jacinto Benavent, 11",
        "Av. Beato Ferreres 1-1-4         ",
        "Cami del Molí 9 bajo          ",
        "c/ Alqueria de Soria, 40-4",
        "c/ Benifairó, 1-1        ",
        "c/ Sueca 25          ",
        "c/ San Isidro Labrador, s/n",
        "c/ Senda del Secanet 44-8",
        "c/ Dr.Fleming, 30",
        "Av. Real de Madrid, 20       ",
        "Camino Moncada, 64-9 B      ",
        "c/ Bona Bista, 4     ",
        "c/ Manuel Lopez Varela, 32",
        "C/ Luis Vives, 18     ",
        "c/ La Sangre, s/n (Edif Mcpal)",
        "c/ Alberola 21",
        "Carrer del Mig 18-1",
        "c/ Pintor Tarrasó, 62-1     ",
        "c/Virgen de Campanar 15-10ª   ",
        "Ronda Joan Fuster, 6 bajo",
        "C/ Remedios Lizondo, 1",
        "c/ Benimamet, 20",
        "c/ Juan de Juanes 3 bajo",
        "c/ Reyes Católicos, 38",
        "carrer moreretes 1           ",
        "c/ San Antonio 4",
        "Pl.Españoleto 2",
        "C/ San Antoni, 4-b",
        "País Valencià, 17",
        "Jaime de Olit, 31          ",
        "C/ Mercado, 15 bajo             ",
        "PASSATGE REIX, 1-3-14",
        "C/ Felipe Valls, 175",
        "Avd. Alqueria de Roc, 12-24",
        "Avd. Censals 22",
        "Avd. Passeig del Oficis 28",
        "Avd. Ausias March, 40",
        "c/Padre Fullana, 8            ",
        "c/Xátiva, 24 bajo     ",
        "c/Constitución, 35 bajo       ",
        "c/ Salvador Botella, 33 -1-1",
        "Avd. La Bastida, 12",
        "C/ Leonardo Bonet, 8-1-3",
        "Avd. Mayor Santa Catalina, 11",
        "Avd. Lluis Suñer, 28 B",
        "Avd. Puchadeta del Sord, 16",
        "Avda. Corts Valencianes, 26",
        "P.Ind El Brosquil, c/ 11 Parc 12A",
        "c/ Jubilados, 19-2",
        "Virgen Dolores, 9-bajo            ",
        "Plaza Cabanilles, 4-2-6",
        "C/ Cirene, 12 arpart. B8",
        "Avd. Encarnacio 2",
        "Avd. Ausias March, 28",
        "Avd. Medico Marcial Bernat, 10",
        "Avd. San Sebastián, 102",
        "Avd. Botánico Cabanilles, 5",
        "c/ La Savina, 1-D",
        "c/ Senia de la Llobera, s/n Nave 1",
        "c/ Hernan Cortes  3     ",
        "P.Germanies, 93 Local B        ",
        "c/Llauri, 25 bajo             ",
        "c/ Dr Fleming, 57",
        "C/ Vergeret, 1-4-18",
        "Pais Valenciano, 36",
        "c/San Rafael 74               ",
        "C/ Ribera Baixa, 128",
        "Menendez Pelayo, 19",
        "C/ Pintor Sorolla, 5",
        "c/ Vte.Andrés Estellés, 31         ",
        "c/ 25 d`aBril, 11      ",
        "c/ San Cristobal, 10",
        "Plaça Major, 3",
        "Almeria, 8 pta 6",
        "c/Joaquin Sorolla 49 - 5",
        "c/ Maestro Amblar, 6",
        "c/ Padre Feijó, 4 Esc 1 pta 20",
        "c/ Marques de Montortal, 45b",
        "c/ Cumbres Mayores, 4-2         ",
        "carrtera LLiria 113-4 ",
        "c/ Maestro Rodrigo, 37 bajo",
        "C/ Picassent 7-10",
        "Av.Diputación, 1-bajo",
        "Plaça del Poble 1",
        "Calle Marines 2-12",
        "c/Colon, 6 bajo               ",
        "c/ Pintor Sorolla, 12 ",
        "c/ Torrent, 5-bajo",
        "c/ Benemerita Guardia Civil, 19        ",
        "c/ Mancomunidad Alto Turia, s/n",
        "Plaza de España, 1",
        "c/ San Vicente Mártir, 16 Entr 1 pta 6",
        "c/ Camino de Vera s/n            ",
        "c/ De la Ribera, 16",
        "c/ Jose Todolí Cucarella 52",
        "c/ Valencia, 2",
        "c/ Ermita, 36          ",
        "c/ San Antonio, 42",
        "c/ Antigua Via del Ferrocarril s/n",
        "c/ Vicent Tomas Marti, 14-9",
        "c/ Cervantes, 16-b",
        "Calle Acacias, 14    ",
        "Plaza La Cenia, 7 bajo        ",
        "c/ Padre Gil Sendra, 17 bajo",
        "c/ Virgen del Socorro, 10-8",
        "c/ Madre Vedruna, 15-B",
        "c/ Puente, 4",
        "C/ Mestre Serrano, 20",
        "c/ Elionor Ripoll, 13",
        "c/ Tauleta, 15",
        "c/ Pinedo al Mar 27 bajo    ",
        "C/ Bonavista 11",
        "Av. Joanot Martorel, 18",
        "Pza.Músico Espí 9-5ª          ",
        "c/Morella, 11 Bajo A          ",
        "c/ Pais Valencia, 14-2º-3ª",
        "Av.de Pera, 3",
        "c/ Fco. Valldecabres, 2-7",
        "c/ Actor Rambal, 29-1",
        "c/ Lepanto, 50",
        "c/ Carles Salvador 3",
        "Ronda Sindic A. Albuixech, 121",
        "c/ Maestro Palau, 98",
        "c/ Antonio Suarez, 22-14",
        "Carrera Malilla, 80 bajo",
        "c/Llano de Zaidia, 17 bajo    ",
        "c/Abadia 20           ",
        "c/ Magdalena, 87 bajo         ",
        "c/Caja de Ahorros, 7          ",
        "c/ San Miquel 15",
        "c/Almas, 19",
        "c/ Parroco Miguel Tarin 30",
        "c/Blasco Ibañez, 38",
        "c/ Pino 8          ",
        "c/Gandia, 26                  ",
        "c/Vicente Puchol, 16-1     ",
        "c/Blasco Ibáñez, 72 bajo      ",
        "Avda.Dr.Waskman, 58 bajo      ",
        "c/ D. Enrique Olmos Cercós, 2B-2",
        "c/Raquel Payá, 14             ",
        "c/ Pintor Sorolla 13",
        "c/ Jaume I, 36 bajo Izda",
        "c/Angel, 21                 ",
        "Avda.Pais Valencia, 15 bajo   ",
        "Av Museros, 25-46",
        "c/ Del Foc, 1",
        "c/ Turia 21",
        "c/Pinar, 5 bajo              ",
        "c/ Gran Via Ramon y Cajal 13 bajo",
        "c/ La Tauleta, 36",
        "c/ Libertad, 10",
        "c/Buen Suceso, 3-6ª           ",
        "c/ Arzobispo Olaechea, 39 B",
        "c/ Rio Genil, 4-11           ",
        "c/ Jaume I, 17                  ",
        "c/ Dr. Fleming, 7-1",
        "Plaza Antonio Machado 8",
        "Plaza Las Escuelas, 22-3",
        "Plaza San Cayetano, 10-12",
        "Avd. Cid, 3-3-9",
        "Plaza Favara, 4",
        "Plaza Luz Casanova, 11-bajo C",
        "Plaza Alcalde Miguel Domingo, 3",
        "Plaza Gandia, 21",
        "Plaza Mar, 17",
        "Pza Don Manuel Garcia Izquierdo, S/",
        "Avda Del Sur 22 - 3 - 5",
        "c/ José Iturbi, 27-B",
        "Plaza Coronel Lopez Aparicio 4"};

    private static final String[] empresas = {"kubs",
        "mirecker",
        "anahi",
        "appeph",
        "sanle",
        "intioga",
        "hebar",
        "hudon",
        "walsk",
        "medinp",
        "wiresta",
        "pfiensol",
        "bree",
        "banciter",
        "iton",
        "stan",
        "watics",
        "pohorp",
        "capuscona",
        "beling",
        "quervarix",
        "coman",
        "fraltalko",
        "brius",
        "yamatho",
        "valpopars",
        "catita",
        "branatin",
        "natic",
        "wespen",
        "eves",
        "abeng",
        "teles",
        "bankyo",
        "grum",
        "tsung",
        "amenom",
        "serne",
        "aluxon",
        "sheles",
        "angost",
        "yoki",
        "mizai",
        "happgno",
        "vivers",
        "daita",
        "danz",
        "authan",
        "scommurow",
        "tectohn",
        "teill",
        "frants",
        "costpatsu",
        "badide",
        "reldancon",
        "bhans",
        "urbaisl",
        "pacitsch",
        "carti",
        "symars",
        "shorts",
        "irie",
        "meran",
        "medic",
        "dare",
        "negank",
        "hinos",
        "cumb",
        "ahon",
        "nagan",
        "facom",
        "wace",
        "sindskair",
        "worizern",
        "sumin",
        "hancianey",
        "poling",
        "urbalern",
        "moubillo",
        "aurkersto",
        "hyds",
        "eures",
        "nidtral",
        "amber",
        "haria",
        "cumins",
        "ecommit",
        "alpos",
        "cond",
        "capir",
        "assai",
        "synt",
        "avin",
        "holay",
        "glar",
        "hewirles",
        "ters",
        "parminer",
        "nage",
        "kellima"};

    private static final String[] nombres = {"Antonio",
        "Jose",
        "Manuel",
        "Francisco",
        "Juan",
        "David",
        "Jose Antonio",
        "Jose Luis",
        "Javier",
        "Francisco Javier",
        "Jesus",
        "Daniel",
        "Carlos",
        "Miguel",
        "Alejandro",
        "Jose Manuel",
        "Rafael",
        "Pedro",
        "Angel",
        "Miguel Angel",
        "Jose Maria",
        "Fernando",
        "Pablo",
        "Luis",
        "Sergio",
        "Jorge",
        "Alberto",
        "Juan Carlos",
        "Juan Jose",
        "Alvaro",
        "Diego",
        "Adrian",
        "Juan Antonio",
        "Raul",
        "Enrique",
        "Ramon",
        "Vicente",
        "Ivan",
        "Ruben",
        "Oscar",
        "Andres",
        "Joaquin",
        "Juan Manuel",
        "Santiago",
        "Eduardo",
        "Victor",
        "Roberto",
        "Jaime",
        "Francisco Jose",
        "Mario",
        "Ignacio",
        "Alfonso",
        "Salvador",
        "Ricardo",
        "Marcos",
        "Jordi",
        "Emilio",
        "Julian",
        "Julio",
        "Guillermo",
        "Gabriel",
        "Tomas",
        "Agustin",
        "Jose Miguel",
        "Marc",
        "Gonzalo",
        "Felix",
        "Jose Ramon",
        "Mohamed",
        "Hugo",
        "Joan",
        "Ismael",
        "Nicolas",
        "Cristian",
        "Samuel",
        "Mariano",
        "Josep",
        "Domingo",
        "Juan Francisco",
        "Aitor",
        "Martin",
        "Alfredo",
        "Sebastian",
        "Jose Carlos",
        "Felipe",
        "Hector",
        "Cesar",
        "Jose Angel",
        "Jose Ignacio",
        "Victor Manuel",
        "Iker",
        "Gregorio",
        "Luis Miguel",
        "Alex",
        "Jose Francisco",
        "Juan Luis",
        "Rodrigo",
        "Albert",
        "Xavier",
        "Lorenzo",
        "Maria Carmen",
        "Maria",
        "Carmen",
        "Josefa",
        "Isabel",
        "Ana maria",
        "Maria Pilar",
        "Maria Dolores",
        "Maria Teresa",
        "Ana",
        "Laura",
        "Francisca",
        "Maria Angeles",
        "Cristina",
        "Antonia",
        "Marta",
        "Dolores",
        "Maria Isabel",
        "Maria Jose",
        "Lucia",
        "Maria Luisa",
        "Pilar",
        "Elena",
        "Concepcion",
        "Sara",
        "Paula",
        "Manuela",
        "Mercedes",
        "Rosa Maria",
        "Raquel",
        "Maria Jesus",
        "Juana",
        "Rosario",
        "Teresa",
        "Encarnacion",
        "Beatriz",
        "Nuria",
        "Silvia",
        "Julia",
        "Rosa",
        "Montserrat",
        "Patricia",
        "Irene",
        "Andrea",
        "Rocio",
        "Monica",
        "Alba",
        "Maria mar",
        "Angela",
        "Sonia",
        "Alicia",
        "Sandra",
        "Susana",
        "Margarita",
        "Marina",
        "Yolanda",
        "Maria Josefa",
        "Natalia",
        "Maria Rosario",
        "Inmaculada",
        "Eva",
        "Maria Mercedes",
        "Esther",
        "Ana isabel",
        "Angeles",
        "Noelia",
        "Claudia",
        "Veronica",
        "Amparo",
        "Maria rosa",
        "Carolina",
        "Maria Victoria",
        "Carla",
        "Eva maria",
        "Maria Concepcion",
        "Nerea",
        "Lorena",
        "Ana Belen",
        "Victoria",
        "Miriam",
        "Maria Elena",
        "Sofia",
        "Catalina",
        "Ines",
        "Maria Antonia",
        "Consuelo",
        "Emilia",
        "Maria Nieves",
        "Lidia",
        "Luisa",
        "Gloria",
        "Celia",
        "Olga",
        "Aurora",
        "Esperanza",
        "Josefina",
        "Maria soledad",
        "Milagros",
        "Maria cristina",
        "Daniela"};

    private static final String[] apellidos = {"Garcia",
        "Gonzalez",
        "Rodriguez",
        "Fernandez",
        "Lopez",
        "Martinez",
        "Sanchez",
        "Perez",
        "Gomez",
        "Martin",
        "Jimenez",
        "Ruiz",
        "Hernandez",
        "Diaz",
        "Moreno",
        "Alvarez",
        "Romero",
        "Alonso",
        "Gutierrez",
        "Navarro",
        "Torres",
        "Dominguez",
        "Vazquez",
        "Ramos",
        "Gil",
        "Ramirez",
        "Serrano",
        "Blanco",
        "Molina",
        "Suarez",
        "Morales",
        "Ortega",
        "Delgado",
        "Castro",
        "Ortiz",
        "Rubio",
        "Marin",
        "Sanz",
        "Iglesias",
        "Medina",
        "Garrido",
        "Cortes",
        "Santos",
        "Castillo",
        "Lozano",
        "Guerrero",
        "Cano",
        "Prieto",
        "Mendez",
        "Calvo",
        "Cruz",
        "Gallego",
        "Vidal",
        "Leon",
        "Marquez",
        "Herrera",
        "Flores",
        "Cabrera",
        "Campos",
        "Vega",
        "Fuentes",
        "Diez",
        "Carrasco",
        "Caballero",
        "Nieto",
        "Reyes",
        "Aguilar",
        "Pascual",
        "Herrero",
        "Santana",
        "Lorenzo",
        "Hidalgo",
        "Montero",
        "Gimenez",
        "Ferrer",
        "Duran",
        "Santiago",
        "Vicente",
        "Benitez",
        "Mora",
        "Arias",
        "Vargas",
        "Carmona",
        "Crespo",
        "Roman",
        "Pastor",
        "Soto",
        "Saez",
        "Velasco",
        "Moya",
        "Soler",
        "Esteban",
        "Parra",
        "Bravo",
        "Gallardo",
        "Rojas"};

    private static final String[] verbo = {"acelerar",
        "acoplar",
        "adoptar",
        "agregar",
        "alimentar",
        "articular",
        "analizar",
        "complementar",
        "cultivar",
        "conceptualizar",
        "compatibilizar",
        "contextualizar",
        "construir",
        "desarrollar",
        "desplegar",
        "diseñar",
        "encapsular",
        "engranar",
        "ensamblar",
        "escalar",
        "estimular",
        "evaluar",
        "evolucionar",
        "explotar",
        "extender",
        "facilitar",
        "favorecer",
        "generar",
        "gestionar",
        "habilitar",
        "incubar",
        "implementar",
        "impulsar",
        "incentivar",
        "integrar",
        "maximizar",
        "nivelar",
        "reconvertir",
        "re-inventar",
        "reformatear",
        "objetivizar",
        "optimizar",
        "orquestar",
        "propulsar",
        "racionalizar",
        "sinergizar",
        "sintetizar",
        "sistematizar",
        "transformar",
        "visualizar"};

    private static final String[] concepto = {"actuaciones",
        "ajustes",
        "aplicaciones",
        "asociaciones",
        "arquitecturas",
        "infraestructuras",
        "iniciativas",
        "canales",
        "comunidades",
        "conectividades",
        "contenidos",
        "convergencias",
        "dinámicas",
        "esquemas",
        "estructuras",
        "experiencias",
        "funcionalidades",
        "interfaces",
        "mecanismos",
        "mercados",
        "metodologías",
        "modelos",
        "nichos",
        "indicadores",
        "paradigmas",
        "plataformas",
        "políticas",
        "portales",
        "protocolos",
        "proyecciones",
        "proyectos",
        "redes",
        "relaciones",
        "servicios electrónicos",
        "sinergias",
        "sistemas",
        "soluciones",
        "tendencias",
        "tecnologías",
        "topologías",
        "transiciones",
        "transposiciones",
        "usuarios"};

    private static final String[] complemento = {"de activación",
        "afines",
        "de banda ancha",
        "business-to-business",
        "business-to-consumer",
        "conceptuales",
        "con capacidades dinámicas",
        "con capacidades Web",
        "de código abierto",
        "de colaboración",
        "de contorno",
        "de convergencia",
        "de distribución",
        "escalables",
        "eficientes",
        "globales",
        "granulares",
        "de iniciativa",
        "llave en mano",
        "a medida",
        "modulares",
        "con interactividad",
        "one-to-one",
        "perimetrales",
        "de personalización",
        "de tipo plug-and-play",
        "punto-com",
        "con proactividad",
        "de re-ingeniería",
        "de re-posicionamiento",
        "del sector privado",
        "de atención al cliente",
        "de tecnología punta",
        "en tiempo real",
        "de redes sociales",
        "sostenibles",
        "de la web 2.0",
        "transparentes",
        "de transición",
        "de última generación",
        "con centro en el usuario",
        "de valor añadido",
        "verticales",
        "de visión periférica",
        "virales",
        "virtuales"};

    private final static String[] carreras = {"Arqueología",
        "Artes Escénicas",
        "Bellas Artes",
        "Conservación - Restauración de Bienes Culturales",
        "Danza",
        "Diseño",
        "Estudios Literarios",
        "Filosofía",
        "Fotografía",
        "Geografía",
        "Astronomía y Astrofísica",
        "Biología",
        "Bioquímica",
        "Biotecnología",
        "Ciencia y Tecnología de los Alimentos",
        "Ciencias Ambientales",
        "Ciencias Biomédicas",
        "Ciencias del Mar",
        "Ciencias Experimentales",
        "Enología",
        "Ciencias Biomédicas",
        "Ciencias de la Actividad Física y del Deporte",
        "Enfermería",
        "Farmacia",
        "Fisioterapia",
        "Logopedia",
        "Medicina",
        "Nutrición Humana y Dietética",
        "Odontología",
        "Óptica y Optometría",
        "Administración y Dirección de Empresas",
        "Antropología",
        "Asistencia en Dirección",
        "Ciencias del Trabajo y Recursos Humanos",
        "Ciencias del Transporte y la Logística",
        "Ciencias Políticas y de la Administración Pública",
        "Comercio",
        "Comunicación Audiovisual",
        "Contabilidad y Finanzas",
        "Criminología",
        "Arquitectura",
        "Diseño y Desarrollo de videojuegos",
        "Ingeniería Aeroespacial",
        "Ingeniería Agroambiental",
        "Ingeniería Alimentaria",
        "Ingeniería Ambiental",
        "Ingeniería Biomédica",
        "Ingeniería de Caminos, Canales y Puertos",
        "Ingeniería de la Edificación",
        "Ingeniería de la Energía"};

    private static final String[] puestoTrabajo = {"Acomodador/a",
        "Acomodador/a-acomodador/a",
        "Actor/actriz",
        "Administracion-atencion al cliente",
        "Administracion-auxiliar administrativo/a",
        "Administracion-auxiliar contable",
        "Administracion-becaria asuntos sociales",
        "Administracion-botones",
        "Administracion-codificador de datos",
        "Administracion-direccion y gestion empresarial",
        "Administracion-oficial administrativo/a",
        "Administracion-ordenanza",
        "Administracion-otro personal de oficina",
        "Administracion-otros",
        "Administracion-profesional de contabilidad",
        "Administracion-recepcionista",
        "Administracion-secretariado",
        "Agente censal",
        "Agente de igualdad",
        "Agente desarrollo local",
        "Agente forestal",
        "Agente forestal-agente forestal",
        "Agente forestal-viverista",
        "Agente judicial",
        "Agricultor/a",
        "Alfarero/a",
        "Animador/a sociocultural",
        "Apicultor/a",
        "Archivero/a",
        "Archivero/a-archivero/a",
        "Archivero/a-auxiliar de archivo",
        "Archivero/a-ayudante de archivo",
        "Artesano/a",
        "Asesor",
        "Asesor de imagen",
        "Asesor en prl",
        "Asesor fiscal y tributario",
        "Asesor juridico",
        "Asesor/a-otros",
        "Auxiliar",
        "Auxiliar de control",
        "Auxiliar-ayuda a domicilio",
        "Auxiliar-jardin de infancia",
        "Auxiliar-servicios sociales",
        "Ayudante de marroquineria",
        "Ayudante de produccion",
        "Azafata",
        "Azafata-azafata de vuelo",
        "Biblioteca-auxiliar de biblioteca",
        "Biblioteca-ayudante de biblioteca",
        "Bibliotecario/a",
        "Bibliotecario/a-auxiliar",
        "Bibliotecario/a-diplomado/a biblioteconomia",
        "Calderero-aislador",
        "Camara",
        "Capataz forestal",
        "Carpintero/a",
        "Carpintero/a de aluminio",
        "Carpintero/a de armar en constr.",
        "Carpintero/a de decorados",
        "Carpintero/a de pvc",
        "Carpintero/a ebanista artesano",
        "Carpintero/a metalico",
        "Carpintero/a-otros",
        "Cerrajero/a",
        "Chapista",
        "Chapista de aluminio",
        "Chapista industrial",
        "Chapista-otros",
        "Colocador/a",
        "Colocador/a de moqueta",
        "Colocador/a pavimentos ligeros",
        "Colocador/a prefabric. hormigon",
        "Colocador/a prefabricados (lig.)",
        "Colocador/a tuberia hormigon",
        "Colocador/a-otros",
        "Comercio",
        "Comercio-agente comercial",
        "Comercio-agente de seguros",
        "Comercio-agente inmobiliario",
        "Comercio-asesor/a financiero/a",
        "Comercio-ayudante de panaderia",
        "Comercio-ayudante de pasteleria",
        "Comercio-cajero/a",
        "Comercio-carnicero/a",
        "Comercio-charcutero/a",
        "Comercio-control de calidad",
        "Comercio-dependiente/a",
        "Comercio-encargado/a",
        "Comercio-expendedor/a",
        "Comercio-frutero/a",
        "Comercio-jefe/a de almacen",
        "Comercio-manipulador/a de alimentos",
        "Comercio-mozo/a de almacen",
        "Comercio-otros",
        "Comercio-pescadero/a",
        "Comercio-repartidor/a a domicilio",
        "Comercio-reponedor/a",
        "Comercio-secretario comercial",
        "Comercio-supervisor/a",
        "Comercio-teleoperador/a",
        "Comercio-vendedor/a",
        "Comercio-vendedor/a a domicilio",
        "Comercio-vendedor/a por telefono",
        "Comercio-verdulero/a",
        "Comercio-viajante",
        "Conductor/a operario/a",
        "Conductor/a operario/a-adoquin-pavimentador",
        "Conductor/a operario/a-bulldozer",
        "Conductor/a operario/a-camion volquete",
        "Conductor/a operario/a-carretilla elevadora",
        "Conductor/a operario/a-dumper",
        "Conductor/a operario/a-excavadora",
        "Conductor/a operario/a-extend.asfaltica",
        "Conductor/a operario/a-grua en camion",
        "Conductor/a operario/a-grua fija",
        "Conductor/a operario/a-grua movil",
        "Conductor/a operario/a-grua puente",
        "Conductor/a operario/a-grúa telescópica",
        "Conductor/a operario/a-grua torre",
        "Conductor/a operario/a-hormigonera movil",
        "Conductor/a operario/a-manitu",
        "Conductor/a operario/a-maq. obras publicas",
        "Conductor/a operario/a-maq.incadora pilotes",
        "Conductor/a operario/a-maquin. de vias",
        "Conductor/a operario/a-maquin.compactacion",
        "Conductor/a operario/a-maquin.dragados",
        "Conductor/a operario/a-maquin.explanacion",
        "Conductor/a operario/a-maquinaria limpieza",
        "Conductor/a operario/a-maquinas mov.t.",
        "Conductor/a operario/a-martillo neumatico",
        "Conductor/a operario/a-motoniveladora",
        "Conductor/a operario/a-otros",
        "Conductor/a operario/a-pala cargadora",
        "Conductor/a operario/a-retroexcavadora",
        "Construccion",
        "Construccion-albañil",
        "Construccion-albañil-remates",
        "Construccion-albañil-replanteo",
        "Construccion-albañil-revestidor",
        "Construccion-alicatador/a",
        "Construccion-aparejador/a",
        "Construccion-aprendiz de construccion",
        "Construccion-apuntalador/a de edificios",
        "Construccion-artillero/a de la construccion",
        "Construccion-auxiliar administ. obra",
        "Construccion-auxiliar tecnico de obra",
        "Construccion-ayudante de obra",
        "Construccion-azulejero/a artesanal",
        "Construccion-barrenista",
        "Construccion-cantero/a artesan marmol o piedr",
        "Construccion-cantero/a de construccion",
        "Construccion-capataz de obra edificacion",
        "Construccion-cristalero/a de edificios",
        "Construccion-demoledor de edificios",
        "Construccion-encargado/a de obra",
        "Construccion-encargado/a de obra edif",
        "Construccion-encargado/a de obras publicas",
        "Construccion-escayolista",
        "Construccion-oficial de primera",
        "Construccion-oficial de segunda",
        "Construccion-oficial de tercera",
        "Construccion-otros",
        "Construccion-peon de caminos",
        "Construccion-peon de la construccion",
        "Construccion-pizarrista",
        "Consultor/a-medio ambiente",
        "Coordinador/a de proyectos de desarrollo",
        "Coordinador/a de formación",
        "Crupier",
        "Decorador/a",
        "Decorador/a interiores",
        "Decorador/a-adornista",
        "Decorador/a-escaparatista",
        "Decorador/a-otros",
        "Documentalista",
        "Ebanista",
        "Educador/a social",
        "Electricista",
        "Electricista-aprendiz electricista",
        "Electricista-ayudante de electricista",
        "Electricista-electricista",
        "Enmoquetador/a",
        "Entrevistador/a-encuestador/a",
        "Esteticista",
        "Fontanero/a",
        "Fotografo/a",
        "Grabador/a de datos",
        "Hosteleria",
        "Hosteleria-aprendiz de cocina",
        "Hosteleria-aprendiz de hostelería",
        "Hosteleria-asistente/a de grupos turisticos",
        "Hosteleria-ayudante/a de cocina",
        "Hosteleria-barman",
        "Hosteleria-camarero/a",
        "Hosteleria-camarero/a de planta",
        "Hosteleria-encargado/a",
        "Hosteleria-jefe/a de cocina",
        "Hosteleria-office",
        "Hosteleria-otros",
        "Hosteleria-pinche",
        "Ilustrador/a",
        "Industria",
        "Industria-artesanos/as de la madera",
        "Industria-artesanos/as de textiles",
        "Industria-artesanos/as de textiles y cueros",
        "Industria-control sistemas calidad",
        "Industria-costureros/as",
        "Industria-embaleje",
        "Industria-encargado/a",
        "Industria-fresador tornero",
        "Industria-operario/a industrial",
        "Industria-tintoreria",
        "Industria-tratamiento de la leche",
        "Industria-tratamiento de la leche y prod. lacteos",
        "Industria-zapateria",
        "Informatica",
        "Informatica-analisis de sistemas",
        "Informatica-bases de datos",
        "Informatica-cibernetica",
        "Informatica-diseñador grafico multimedia",
        "Informatica-hardware",
        "Informatica-internet",
        "Informatica-jefe departamento tecnico",
        "Informatica-lenguajes de programacion",
        "Informática-modelador 3d",
        "Informatica-programador/a web",
        "Informatica-redes",
        "Informatica-seguridad informatica",
        "Informatica-sistemas operativos",
        "Informatica-software",
        "Informatica-teleinformatica",
        "Instalador/a",
        "Instalador/a aire acond y ventil",
        "Instalador/a calefaccion",
        "Instalador/a de antenas",
        "Instalador/a electric.edificios",
        "Instalador/a electricista",
        "Instalador/a electricista indust",
        "Instalador/a impermeabilizacion",
        "Instalador/a mat.aislant.insonor",
        "Instalador/a material aislante",
        "Instalador/a tuberias de gas",
        "Instalador/a tuberias industrial",
        "Instalador/a tubos en zanjas",
        "Instalador/a-otros",
        "Interprete",
        "Intérprete lenguaje de signos",
        "Investigador/a social",
        "Jardinero/a",
        "Joyero/a",
        "Limpiador/a",
        "Limpiador/a acabados edificios",
        "Limpiador/a de alcantarillas",
        "Limpiador/a de fachadas",
        "Limpiador/a de portales y esc.",
        "Limpiador/a de ventanas",
        "Limpiador/a de ventanas gran alt",
        "Limpiador/a fachadas agua",
        "Limpiador/a fachadas con arena",
        "Limpiador/a-auxiliar de limpieza",
        "Limpiador/a-barrendero/a",
        "Limpiador/a-mantenedor/a piscinas",
        "Limpiador/a-otros",
        "Locutor/a de radio",
        "Logistica",
        "Logistica-aparcacoches",
        "Logistica-chofer",
        "Logistica-clasificador/a",
        "Logistica-conductor/a",
        "Logistica-conductor/a de ambulancias",
        "Logistica-conductor/a de autobuses",
        "Logistica-conductor/a de camion cisterna",
        "Logistica-conductor/a de camiones",
        "Logistica-conductor/a de gruas",
        "Logistica-conductor/a de gruas y maq. pesada",
        "Logistica-conductor/a de tractocamion",
        "Logistica-conductor/a maquinaria",
        "Logistica-mensajero/a",
        "Logistica-otros trabajos de logistica",
        "Logistica-peon carga y descarga",
        "Logistica-repartidor/a",
        "Marketing",
        "Masajista",
        "Mecanico/a",
        "Mediador/a social",
        "Medio ambiente-auxiliar forestal",
        "Militar",
        "Militar-militar",
        "Modelo",
        "Monitor/a",
        "Monitor/a-aerobic",
        "Monitor/a-baile",
        "Monitor/a-bus escolar",
        "Monitor/a-comedor",
        "Monitor/a-coordinacion de actividades juveniles",
        "Monitor/a-deportivo/a",
        "Monitor/a-infantil",
        "Monitor/a-mantenimiento",
        "Monitor/a-tecnico de juventud",
        "Montador/a",
        "Montador/a cercados y vallas met",
        "Montador/a de aislamientos",
        "Montador/a de andamios",
        "Montador/a de carpinteria gral.",
        "Montador/a de grua",
        "Montador/a de puertas automatic.",
        "Montador/a de puertas blindadas",
        "Montador/a de toldos",
        "Montador/a en piedra escult/monu",
        "Montador/a placas energia solar",
        "Montador/a-ajust.maq.construcc",
        "Montador/a-otros",
        "Musico",
        "Orientador/a laboral",
        "Otros servicios",
        "Panadero/a",
        "Pastelero/a",
        "Patronaje-profesor/a de patronaje industrial",
        "Patronista",
        "Patronista-diseñador/a",
        "Peluquero/a",
        "Peluquero/a-aprendiz peluquero/a",
        "Peluquero/a-ayudante",
        "Peluquero/a-canino",
        "Peluquero/a-peluquero/a",
        "Peon",
        "Peon agricola",
        "Peon forestal",
        "Peon ganadero",
        "Peon-otros",
        "Periodismo",
        "Periodismo-corrector/a de textos",
        "Periodismo-redactor/a",
        "Personal de mantenimiento",
        "Pintor/a",
        "Pocero",
        "Produccion de espectaculos y audiovisuales",
        "Profesor/a",
        "Profesor/a- de cocina",
        "Profesor/a-actividades artistico-manuales",
        "Profesor/a-alemán",
        "Profesor/a-clases particulares",
        "Profesor/a-danza",
        "Profesor/a-diplomado/a magisterio",
        "Profesor/a-educacion fisica",
        "Profesor/a-educador/a adultos",
        "Profesor/a-educador/a especial",
        "Profesor/a-educador/a infantil",
        "Profesor/a-enseñanza secundaria",
        "Profesor/a-enseñanza superior",
        "Profesor/a-español para extrangeros",
        "Profesor/a-formador/a informatica",
        "Profesor/a-frances",
        "Profesor/a-inglés",
        "Profesor/a-monitor de futbol",
        "Profesor/a-monitor de natacion",
        "Profesor/a-monitor/a de tiempo libre",
        "Profesor/a-musica",
        "Profesor/a-tai chi",
        "Profesor/a-universidad",
        "Profesor/a-yoga",
        "Programador/a",
        "Relaciones publicas",
        "Restaurador/a",
        "Sanidad",
        "Sanidad-ats",
        "Sanidad-auxiliar de clinica",
        "Sanidad-auxiliar de enfermeria",
        "Sanidad-auxiliar de farmacia",
        "Sanidad-auxiliar de geriatria",
        "Sanidad-ayudante de estomatologia",
        "Sanidad-bioquimica",
        "Sanidad-celador/a",
        "Sanidad-cuidado de ancianos/as",
        "Sanidad-dentista",
        "Sanidad-diplomado/a fisioterapia",
        "Sanidad-enfermero/a",
        "Sanidad-farmaceutico/a",
        "Sanidad-higienista dental",
        "Sanidad-laboratorios",
        "Sanidad-masajista",
        "Sanidad-medico",
        "Sanidad-odontologo/a",
        "Sanidad-opticos",
        "Sanidad-otros",
        "Sanidad-protesico dental",
        "Sanidad-veterinarios/as",
        "Seguridad",
        "Seguridad-conserje",
        "Seguridad-controlador/a de aparcamiento",
        "Seguridad-detective",
        "Seguridad-guarda",
        "Seguridad-guarda de obra",
        "Seguridad-guardaespaldas",
        "Seguridad-guardia jurado",
        "Seguridad-militar",
        "Seguridad-otros",
        "Seguridad-policia municipal",
        "Seguridad-policia nacional",
        "Seguridad-portero/a",
        "Seguridad-portero/a de finca",
        "Seguridad-vigilante",
        "Servicio domestico-aux. serv. personal",
        "Servicios domesticos",
        "Servicios domesticos-ama de llaves",
        "Servicios domesticos-canguro",
        "Servicios domesticos-empleado/a de hogar",
        "Servicios domesticos-empleado/a de hogar interna",
        "Servicios domesticos-lavanderos/as",
        "Servicios domesticos-lavanderos/as, planchadores/as",
        "Servicios domesticos-limpieza",
        "Servicios domesticos-limpieza de interior de edificios",
        "Servicios domesticos-otros",
        "Socorrista",
        "Solador/a",
        "Soldador/a",
        "Taller",
        "Taller-chapista",
        "Taller-ensamblador/a",
        "Taller-fresador/a",
        "Taller-jefe/a de mantenimiento",
        "Taller-jefe/a de taller",
        "Taller-mecanica de automoviles",
        "Taller-mecanica de maquinaria",
        "Taller-montador/a",
        "Taller-mozo/a",
        "Taller-otros",
        "Taller-tecnico/a de electronica",
        "Taller-tecnico/a de telecomunicaciones",
        "Taller-trabajador/a de artes graficas",
        "Taquillero/a",
        "Tecnico de cultura",
        "Tecnico de imagen",
        "Tecnico de semovientes",
        "Tecnico de sonido",
        "Tecnico en comercio exterior",
        "Tecnico en electronica",
        "Tecnico en estadistica",
        "Tecnico en medio ambiente",
        "Tecnico en proteccion civil",
        "Tecnico en recursos humanos",
        "Tecnico en salud ambiental",
        "Técnico en tratamiento de aguas",
        "Tecnico/a en publicidad",
        "Tecnico/a en relac. publicas",
        "Tecnico/a prevencion riesgos laborales",
        "Terapeuta ocupacional",
        "Trabajador/a de la ceramica",
        "Trabajador/a industria textil",
        "Trabajador/a industria textil-costurero/a",
        "Trabajador/a social",
        "Traductor/a",
        "Tramoyista",
        "Tramoyista-tramoyista",
        "Turismo",
        "Turismo- turismo rural",
        "Turismo-agente de viajes",
        "Turismo-agente de viajes",
        "Turismo-diplomado/a",
        "Turismo-gerente empresas turismo",
        "Turismo-guia",
        "Turismo-informador/a turistico/a"};

}
