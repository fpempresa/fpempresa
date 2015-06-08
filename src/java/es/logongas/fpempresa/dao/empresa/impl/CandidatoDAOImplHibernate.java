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
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import es.logongas.ix3.dao.impl.PageImpl;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class CandidatoDAOImplHibernate extends GenericDAOImplHibernate<Candidato, Integer> implements CandidatoDAO {

    @Override
    public boolean isUsuarioCandidato(Usuario usuario, Oferta oferta) {
        Session session = sessionFactory.getCurrentSession();

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
    public Page<Candidato> getCandidatosOferta(Oferta oferta, boolean ocultarRechazados, boolean certificados, int maxAnyoTitulo, PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();

        String sqlPartFrom = " FROM Candidato candidato ";
        StringBuilder sqlPartWhere = new StringBuilder(" WHERE candidato.oferta.idOferta=? ");
        if (ocultarRechazados == true) {
            sqlPartWhere.append(" AND candidato.rechazado=? ");
        }
        String sqlPartOrderBy = "";
        String sqlPartSelectObject = " SELECT candidato ";
        String sqlPartSelectCount = " SELECT COUNT(candidato) ";

        
        Query queryDatos = session.createQuery(sqlPartSelectObject + " " + sqlPartFrom + " " + sqlPartWhere + " " + sqlPartOrderBy);
        queryDatos.setInteger(0, oferta.getIdOferta());
        if (ocultarRechazados == true) {
            queryDatos.setBoolean(1, false);
        }
        queryDatos.setMaxResults(pageRequest.getPageSize());
        queryDatos.setFirstResult(pageRequest.getPageSize() * pageRequest.getPageNumber());

        List results = queryDatos.list();

        //Vamos ahora a calcular el total de páginas
        Query queryCount = session.createQuery(sqlPartSelectCount + " " + sqlPartFrom + " " + sqlPartWhere);
        queryCount.setInteger(0, oferta.getIdOferta());
        if (ocultarRechazados == true) {
            queryCount.setBoolean(1, false);
        }
        Long totalCount = (Long) queryCount.uniqueResult();

        int totalPages;
        if (totalCount == 0) {
            totalPages = 0;
        } else {
            totalPages = (int) (Math.ceil(((double) totalCount) / ((double) pageRequest.getPageSize())));
        }

        Page page = new PageImpl(results, pageRequest.getPageSize(), pageRequest.getPageNumber(), totalPages);

        return page;
    }

}
