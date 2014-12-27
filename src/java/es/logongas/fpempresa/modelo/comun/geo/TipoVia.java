/**
 *   FPempresa
 *   Copyright (C) 2014  Lorenzo González
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

package es.logongas.fpempresa.modelo.comun.geo;

/**
 * Tipos de via para una dirección
 * @author Lorenzo
 */
public enum TipoVia {
    ACEQUIA("acequia"),
    ACERA("acera"),
    ALAMEDA("alameda"),
    ALDEA("aldea"),
    AMPLIACION("ampliacion"),
    ANGOSTA("angosta"),
    APARTADODECORREOS("apartado de correos"),
    APARTAMENTOS("apartamentos"),
    ATAJO("atajo"),
    AVENIDA("avenida"),
    BAJADA("bajada"),
    BARRANCO("barranco"),
    BARRIADA("barriada"),
    BARRIO("barrio"),
    BLOQUES("bloques"),
    CALLE("calle"),
    CALLEJA("calleja"),
    CALLEJON("callejon"),
    CALLEJUELA("callejuela"),
    CALZADA("calzada"),
    CAMINO("camino"),
    CARRERA("carrera"),
    CARRETERA("carretera"),
    CASERIO("caserio"),
    CHALET("chalet"),
    COLONIA("colonia"),
    COOPERATIVA("cooperativa"),
    CORRAL("corral"),
    COSTANILLA("costanilla"),
    CUESTA("cuesta"),
    EDIFICIO("edificio"),
    ESCALA("escala"),
    ESCALERA("escalera"),
    ESCALINATA("escalinata"),
    ESTRADA("estrada"),
    GLORIETA("glorieta"),
    GRUPO("grupo"),
    LLANO("llano"),
    LUGAR("lugar"),
    MANZANA("manzana"),
    MERCADO("mercado"),
    MONTAñA("montaña"),
    MUNICIPIO("municipio"),
    OTROS("otros"),
    PARQUE("parque"),
    PARTICULAR("particular"),
    PARTIDA("partida"),
    PASADIZO("pasadizo"),
    PASAJE("pasaje"),
    PASEO("paseo"),
    PASEOALTO("paseo alto"),
    PASEOBAJO("paseo bajo"),
    PASILLO("pasillo"),
    PASO("paso"),
    PASSEIG("passeig"),
    PATIO("patio"),
    PLACETA("placeta"),
    PLAZA("plaza"),
    PLAZOLETA("plazoleta"),
    PLAZUELA("plazuela"),
    POBLADO("poblado"),
    POLIGONO("poligono"),
    PORTALES("portales"),
    PRIVADA("privada"),
    PROLONGACION("prolongacion"),
    RAMAL("ramal"),
    RAMBLA("rambla"),
    RAMPA("rampa"),
    RESIDENCIA("residencia"),
    RESIDENCIAL("residencial"),
    RIBERA("ribera"),
    RINCON("rincon"),
    RINCONADA("rinconada"),
    RONDA("ronda"),
    SECTOR("sector"),
    SENDA("senda"),
    SENDERO("sendero"),
    SUBIDA("subida"),
    TORRE("torre"),
    TORRENTE("torrente"),
    TRANSVERSAL("transversal"),
    TRASERA("trasera"),
    TRAVESIA("travesia"),
    URBANIZACION("urbanizacion"),
    VIA("via"),
    VILLAS("villas"),
    VIVIENDAS("viviendas");

    String text;

    private TipoVia(String text) {
        this.text = text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return text;
    }

}
