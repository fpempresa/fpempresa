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
package es.logongas.fpempresa.dao.empresa;

import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.GenericDAO;

/**
 *
 * @author logongas
 */
public interface CandidatoDAO extends GenericDAO<Candidato,Integer>  {
    boolean isUsuarioCandidato(DataSession dataSession, Usuario usuario,Oferta oferta);
    Page<Candidato> getCandidatosOferta(DataSession dataSession, Oferta oferta,boolean ocultarRechazados,boolean certificados,int maxAnyoTitulo,PageRequest pageRequest);
    long getNumCandidatosOferta(DataSession dataSession, Oferta oferta);
}
