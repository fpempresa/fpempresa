/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.populate;

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

    static final public String getTelefono() {
        Random random = new Random();
        Integer[] prefix={9,6};

        return getAleatorio(prefix) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1);
    }

    static final public String getCif() {
        Random random = new Random();
        String[] letrasIniciales = {"C", "D", "F", "G", "J", "N", "P", "Q", "R", "S", "V", "W"};
        String cifSinLetra = getAleatorio(letrasIniciales) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1);

        return cifSinLetra + getLetraCif(cifSinLetra);
    }
    static final public String getNif() {
        Random random = new Random();
        String[] letrasIniciales = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "X"};
        String nifSinLetra = getAleatorio(letrasIniciales) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1) + "" + (random.nextInt(8) + 1);

        return nifSinLetra + getLetraNif(nifSinLetra);
    }    

    private static String getLetraNif(String nifSinLetra) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int valor;

        if (nifSinLetra.startsWith("X")) {
            //Es un NIE
            valor = Integer.parseInt(nifSinLetra.substring(1, nifSinLetra.length() - 1));
        } else if (nifSinLetra.startsWith("Y")) {
            //Es un NIE
            valor = 10000000 + Integer.parseInt(nifSinLetra.substring(1, nifSinLetra.length() - 1));
        } else if (nifSinLetra.startsWith("Z")) {
            //Es un NIE
            valor = 20000000 + Integer.parseInt(nifSinLetra.substring(1, nifSinLetra.length() - 1));
        } else {
            //Es un NIF
            valor = Integer.parseInt(nifSinLetra.substring(0, nifSinLetra.length() - 1));
        }

        return ""+letras.charAt(valor % 23);
        

    }

    private static String getLetraCif(String cifSinLetra) {
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
            calendar.add(Calendar.YEAR, -(minAnyos+random.nextInt(maxAnyos-minAnyos)));
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
        "Muñoz",
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
        "Nuñez",
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
        "Peña",
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
        "Ibañez",
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

}
