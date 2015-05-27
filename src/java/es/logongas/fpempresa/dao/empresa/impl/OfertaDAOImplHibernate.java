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

import es.logongas.fpempresa.dao.empresa.OfertaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class OfertaDAOImplHibernate extends GenericDAOImplHibernate<Oferta, Integer> implements OfertaDAO {

    @Override
    public List<Oferta> getOfertasUsuarioTitulado(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT oferta.*\n"
                + "FROM\n"
                + "   Oferta oferta   \n"
                + "		INNER JOIN OfertaCiclo ofertaCiclo   ON oferta.idOferta=ofertaCiclo.idOferta \n"
                + "		INNER JOIN Municipio municipioOferta ON oferta.idMunicipio=municipioOferta.idMunicipio\n"
                + "		INNER JOIN Empresa empresa           ON oferta.idEmpresa=empresa.idEmpresa,\n"
                + "   Usuario usuario \n"
                + "		INNER JOIN FormacionAcademica formacionAcademica ON usuario.idTitulado=formacionAcademica.idTitulado \n"
                + "		INNER JOIN Titulado titulado                     ON usuario.idTitulado=titulado.idTitulado  \n"
                + "		INNER JOIN Municipio municipioTitulado           ON titulado.idMunicipio=municipioTitulado.idMunicipio\n"
                + "WHERE\n"
                + "   oferta.cerrada <> 1 AND\n"
                + "   usuario.idIdentity=? AND\n"
                + "   empresa.idCentro IS NULL AND\n"
                + "   ofertaCiclo.idCiclo=formacionAcademica.idCiclo AND\n"
                + "   municipioOferta.idProvincia=municipioTitulado.idProvincia AND\n"
                + "   not exists (SELECT * FROM Candidato candidato WHERE candidato.idIdentity=usuario.idIdentity AND candidato.idOferta=oferta.idOferta)\n"
                + "   \n"
                + "UNION DISTINCT \n"
                + "\n"
                + "SELECT oferta.*\n"
                + "FROM\n"
                + "   Oferta oferta   \n"
                + "		INNER JOIN OfertaCiclo ofertaCiclo  ON oferta.idOferta=ofertaCiclo.idOferta \n"
                + "		INNER JOIN Empresa empresa          ON oferta.idEmpresa=empresa.idEmpresa,\n"
                + "   Usuario usuario \n"
                + "		INNER JOIN FormacionAcademica formacionAcademica ON usuario.idTitulado=formacionAcademica.idTitulado   \n"
                + "WHERE\n"
                + "   oferta.cerrada <> 1 AND\n"
                + "   usuario.idIdentity=? AND\n"
                + "   empresa.idCentro = formacionAcademica.idCentro AND\n"
                + "   ofertaCiclo.idCiclo=formacionAcademica.idCiclo AND\n"
                + "   not exists (SELECT * FROM Candidato candidato WHERE candidato.idIdentity=usuario.idIdentity AND candidato.idOferta=oferta.idOferta)";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.addEntity(Oferta.class);
        sqlQuery.setInteger(0, usuario.getIdIdentity());
        sqlQuery.setInteger(1, usuario.getIdIdentity());

        return (List<Oferta>) sqlQuery.list();
    }

    @Override
    public List<Oferta> getOfertasInscritoUsuarioTitulado(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT candidato.oferta FROM Candidato candidato WHERE candidato.usuario.idIdentity=?";

        Query query = session.createQuery(hql);
        query.setInteger(0, usuario.getIdIdentity());

        return (List<Oferta>) query.list();
    }

    @Override
    public List<Oferta> getOfertasEmpresasCentro(Centro centro) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT oferta FROM Oferta AS oferta WHERE oferta.empresa.centro.idCentro=?";

        Query query = session.createQuery(hql);
        query.setInteger(0, centro.getIdCentro());

        return (List<Oferta>) query.list();
    }

    @Override
    public List<Oferta> getOfertasEmpresa(Empresa empresa) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT oferta FROM Oferta AS oferta WHERE oferta.empresa.idEmpresa=?";

        Query query = session.createQuery(hql);
        query.setInteger(0, empresa.getIdEmpresa());

        return (List<Oferta>) query.list();
    }

}
