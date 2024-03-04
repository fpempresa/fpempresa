/*
 * FPempresa Copyright (C) 2023 Lorenzo González
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
package es.logongas.fpempresa.security.ace;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoFormacionAcademica;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess.InsertArguments;
import es.logongas.ix3.security.model.ACE;
import es.logongas.ix3.security.model.ACE.ConditionalScriptEvaluator;
import es.logongas.ix3.security.model.Identity;
import java.util.List;
import java.util.Set;

/**
 * No se puede ser candidato de una oferta que no es de mi centro o que no tengo el ciclo que piden
 * aceType='Allow'
 * idPermission=22 (PreExecuteBusinessProcess)
 * idIdentity=32 (GTitulados)
 * secureResourceRegExp='.*BusinessProcess.Candidato.insert';
 * @author logongas
 */
public class ConditionalScriptEvaluatorTituladoAllowCandidatoInsert implements ConditionalScriptEvaluator {

    @Override
    public boolean evaluate(ACE ace, Identity identity, String secureResourceName, Object arguments, List<String> mg) {

        Candidato candidato = ((InsertArguments<Candidato>) arguments).entity;
        Set<Ciclo> ciclosOferta = candidato.getOferta().getCiclos();
        Usuario usuario = candidato.getUsuario();
        Set<FormacionAcademica> formacionesAcademicas = ((Usuario) identity).getTitulado().getFormacionesAcademicas();
        Centro centro = candidato.getOferta().getEmpresa().getCentro();

        //El candidato deber el mismo que yo mismo. No puedo insertar como candidato a otro
        if (usuario.getIdIdentity() != identity.getIdIdentity()) {
            return false;
        }

        //Tengo que tener alguno de los ciclos de la oferta y si es una oferta de centro en el centro de la oferta
        for (Ciclo ciclo : ciclosOferta) {
            if (isRealizadoCiclo(formacionesAcademicas, ciclo.getIdCiclo(), centro) == true) {
                return true;
            }

        }

        return false;
    }

    
    /**
     * Comprueba si en la formación academica 
     * @param formacionesAcademicas
     * @param idCiclo
     * @param centro
     * @return 
     */
    private boolean isRealizadoCiclo(Set<FormacionAcademica> formacionesAcademicas, int idCiclo, Centro centro) {
        for (FormacionAcademica formacionAcademica : formacionesAcademicas) {

            if (formacionAcademica.getTipoFormacionAcademica() == TipoFormacionAcademica.CICLO_FORMATIVO) {
                if (formacionAcademica.getCiclo().getIdCiclo() == idCiclo) {

                    if (centro != null) {
                        //Comprobar tambien que ha hecho el ciclo en el centro
                        if (formacionAcademica.getCentro() != null) {
                            if (formacionAcademica.getCentro().getIdCentro() == centro.getIdCentro()) {
                                return true;
                            } else {
                                //No hacer nada
                                //El ciclo no lo ha hecho en este centro
                            }
                        } else {
                            //No hacer nada
                            //No hay centro en el ciclo , así que seguro que no lo ha hecho aqui
                        }
                    } else {
                        //Si no hay centro, seguro que lo ha realizado
                        return true;
                    }

                }
            }
        }
        return false;
    }
}
