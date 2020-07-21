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
package es.logongas.fpempresa.util.validators;

import java.util.regex.Pattern;

/**
 *
 * @author logongas
 *
 * https://www.agenciatributaria.es/AEAT.internet/Inicio/La_Agencia_Tributaria/Campanas/_Campanas_/Fiscalidad_de_no_residentes/_Identificacion_/Preguntas_frecuentes_sobre_obtencion_de_NIF_de_no_Residentes/_Que_tipos_de_NIF_de_personas_fisicas_utiliza_la_normativa_tributaria_espanola_.shtml#:~:text=A%20las%20personas%20f%C3%ADsicas%20espa%C3%B1olas,14%20a%C3%B1os)%20cuando%20lo%20soliciten.
 *
 */
public class CIFNIFValidator {

    String nif;

    private final char[] arrLettersDcNif = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    private final char[] arrLettersDcCif = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    private static final Pattern PATTERN_CIF = Pattern.compile("[ABCDEFGHJUV][0-9]{8}");
    private static final Pattern PATTERN_CIF_OTRO = Pattern.compile("[ABCDEFGPQSNWR][0-9]{7}[ABCDEFGHIJ]");
    private static final Pattern PATTERN_NIF = Pattern.compile("[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]");
    private static final Pattern PATTERN_NIE = Pattern.compile("[XYZ][0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]");
    private static final Pattern PATTERN_NIF_OTRO = Pattern.compile("[KLM][0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]"); //Españoles no resientes sin DNI (NIF L),Españoles residentes menores 14 años sin DNI,Extranjeros sin NIE

    public CIFNIFValidator(String nif) {
        this.nif = nif;
    }

    public boolean isValid() {
        return isValid(this.nif);
    }

    private boolean isValid(String nif) {

        if (nif == null) {
            return false;
        }
        if (nif.length() != 9) {
            return false;
        }

        //Una 1º letra de CIF y el resto números
        if (PATTERN_CIF.matcher(nif).matches()) {
            int calculoDC = 0;
            for (int i = 1; i < 8; i++) {
                if ((i == 2) || (i == 4) || (i == 6)) {
                    calculoDC += nif.charAt(i) - '0';
                } else {
                    int j = (nif.charAt(i) - '0') * 2;
                    if (j > 9) {
                        j -= 9;
                    }
                    calculoDC += j;
                }
            }
            calculoDC = 10 - calculoDC % 10;
            if (calculoDC == 10) {
                calculoDC = 0;
            }

            
            if (calculoDC == nif.charAt(8) - '0') {
                return true;
            }

            //Falla el DC
            return false;
        }

        //CIF con 1º Letra, numeros y al final otra letra
        if (PATTERN_CIF_OTRO.matcher(nif).matches()) {
            int calculoDC = 0;
            for (int i = 1; i < 8; i++) {
                if ((i == 2) || (i == 4) || (i == 6)) {
                    calculoDC += nif.charAt(i) - '0';
                } else {
                    int j = (nif.charAt(i) - '0') * 2;
                    if (j > 9) {
                        j -= 9;
                    }
                    calculoDC += j;
                }
            }
            calculoDC = 10 - calculoDC % 10;
            if (arrLettersDcCif[(calculoDC - 1)] == nif.charAt(8)) {
                //CIF de organización o extranjero
                return true;
            }

            return false;
        }

        //NIF con Numeros y al final Letra de DC NIF
        if (PATTERN_NIF.matcher(nif).matches()) {
            String sNumero = nif.substring(0, 8);
            int numero = Integer.parseInt(sNumero);
            int calculoDC = numero % 23;
            if (calculoDC + 1 > 23) {
                return false;
            }
            if (nif.charAt(8) == arrLettersDcNif[calculoDC]) {
                if (nif.equalsIgnoreCase("00000001R") || nif.equalsIgnoreCase("00000000T") || nif.equalsIgnoreCase("99999999R")) {
                    //La EAET permite estos NIFs
                    return false;
                }
                return true;
            }

            return false;
        }

        //NIE con 1º Letra de NIE (X,Y,Z), despues  numeros y al final Letra de DC NIF
        if (PATTERN_NIE.matcher(nif).matches()) {
            String sNumero = nif.substring(1, 8);
            int numero = Integer.parseInt(sNumero);
            if (nif.charAt(0) == 'Y') {
                numero += 10000000;
            } else if (nif.charAt(0) == 'Z') {
                numero += 20000000;
            }
            int calculoDC = numero % 23;
            calculoDC += 1;
            if (calculoDC > 23) {
                return false;
            }
            if (nif.charAt(8) == arrLettersDcNif[(calculoDC - 1)]) {
                if (nif.equals("X0000000T")) {
                    //Este nif nunca existe
                    return false;
                }

                return true;
            }

            return false;
        }

        //NIF ESPECIAL 1º Letra (K,L,M), despues  numeros y al final Letra de DC NIF
        if (PATTERN_NIF_OTRO.matcher(nif).matches()) {
            String sNumero = nif.substring(1, 3);
            int numero = Integer.parseInt(sNumero);
            if ((numero < 1) || (numero > 56)) {
                return false;
            }

            sNumero = nif.substring(1, 8);
            numero = Integer.parseInt(sNumero);
            int calculoDC = numero % 23;
            calculoDC += 1;
            if (calculoDC > 23) {
                return false;
            }
            if (nif.charAt(8) == arrLettersDcNif[calculoDC - 1]) {
                return true;
            }

            return false;
        }

        return false;
    }

}
