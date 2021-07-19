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
package es.logongas.fpempresa.dao.empresa.impl;

import es.logongas.fpempresa.dao.empresa.CandidatoDAO;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class CandidatoDAOImplHibernate extends GenericDAOImplHibernate<Candidato, Integer> implements CandidatoDAO {

    @Override
    public boolean isUsuarioCandidato(DataSession dataSession, Usuario usuario, Oferta oferta) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String hql = "SELECT candidato FROM Candidato candidato WHERE candidato.usuario.idIdentity=? AND candidato.oferta.idOferta=?";
        Query query = session.createQuery(hql);
        query.setInteger(0, usuario.getIdIdentity());
        query.setInteger(1, oferta.getIdOferta());

        List<Candidato> candidatos = (List<Candidato>) query.list();

        if (candidatos.size() == 0) {
            return false;
        } else if (candidatos.size() == 1) {
            return true;
        } else {
            throw new RuntimeException("PAra una oferta el usuario está mas de una vez:" + candidatos.size());
        }

    }

    @Override
    public long getNumCandidatosOferta(DataSession dataSession, Oferta oferta) {
        String hqlCount = " SELECT COUNT(candidato) FROM Candidato candidato WHERE candidato.oferta.idOferta=? AND candidato.borrado=0";

        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query queryCount = session.createQuery(hqlCount);
        queryCount.setParameter(0, oferta.getIdOferta());
        long numCandidatosOferta = (Long) queryCount.uniqueResult();

        return numCandidatosOferta;
    }

    @Override
    public Page<Candidato> getCandidatosOferta(DataSession dataSession, Oferta oferta, boolean ocultarRechazados, boolean certificados, int maxAnyoTitulo, PageRequest pageRequest) {
        String sqlPartFrom = " FROM Candidato candidato ";
        StringBuilder sqlPartWhere = new StringBuilder(" WHERE candidato.oferta.idOferta=? AND candidato.borrado=0 ");
        if (ocultarRechazados == true) {
            sqlPartWhere.append(" AND candidato.rechazado=? ");
        }
        sqlPartWhere.append(" AND NOT EXISTS ("
                + "SELECT "
                + "    formacionAcademica "
                + "FROM "
                + "    FormacionAcademica formacionAcademica "
                + "WHERE "
                + "    formacionAcademica.titulado.idTitulado=candidato.usuario.titulado.idTitulado AND "
                + "    (year(current_date())-year(formacionAcademica.fecha))>? "
                + ") ");
        if (certificados == true) {
            sqlPartWhere.append(" AND NOT EXISTS ("
                    + "SELECT "
                    + "    formacionAcademica "
                    + "FROM "
                    + "    FormacionAcademica formacionAcademica "
                    + "WHERE "
                    + "    formacionAcademica.titulado.idTitulado=candidato.usuario.titulado.idTitulado AND "
                    + "    certificadoTitulo=false "
                    + ") ");
        }

        String sqlPartOrderBy = " ORDER BY fecha ";
        String sqlPartSelectObject = " SELECT candidato ";
        String sqlPartSelectCount = " SELECT COUNT(candidato) ";

        String sqlData = sqlPartSelectObject + " " + sqlPartFrom + " " + sqlPartWhere + " " + sqlPartOrderBy;
        String sqlCount = sqlPartSelectCount + " " + sqlPartFrom + " " + sqlPartWhere;

        List<Object> parameters = new ArrayList<Object>();
        parameters.add(oferta.getIdOferta());
        if (ocultarRechazados == true) {
            parameters.add(false);
        }
        parameters.add(maxAnyoTitulo);

        Page page = getPaginatedQuery(dataSession, sqlData, sqlCount, pageRequest, parameters);

        return page;
    }

    @Override
    public void softDelete(DataSession dataSession, int idCandidato) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("UPDATE Candidato SET borrado=1 WHERE idCandidato=?");
        query.setInteger(0, idCandidato);

        int result=query.executeUpdate(); 
        
        if (result!=1) {
            throw new RuntimeException("Se debería haber actualizado una única fila pero se han actualizado:" + result + " idCandidato=" + idCandidato);
        }
    }
    
}
