/**
 * FPempresa Copyright (C) 2014 Lorenzo González
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
package es.logongas.fpempresa.dao.estadisticas.impl;

import es.logongas.fpempresa.dao.estadisticas.EstadisticaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaEstadistica;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class EstadisticaDAOImplHibernate implements EstadisticaDAO {

    @Autowired
    SessionFactory sessionFactory;

    private List<FamiliaEstadistica> getListFamiliaEstadisticaDAO(List<Object[]> datos) {
        List<FamiliaEstadistica> familiaEstadisticas = new ArrayList<FamiliaEstadistica>();

        for (Object[] dato : datos) {
            FamiliaEstadistica familiaEstadistica = new FamiliaEstadistica((Integer) dato[0], (String) dato[1],((Number) dato[2]).intValue());

            familiaEstadisticas.add(familiaEstadistica);
        }

        return familiaEstadisticas;
    }

    @Override
    public List<FamiliaEstadistica> getOfertasGroupByFamilia() {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN ( Oferta as oferta  INNER  JOIN Empresa as empresa ON oferta.idEmpresa=empresa.idEmpresa ) ON familia.idFamilia=oferta.idFamilia\n"
                + "\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getOfertasGroupByFamilia(Empresa empresa) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN ( Oferta as oferta  INNER  JOIN Empresa as empresa ON oferta.idEmpresa=empresa.idEmpresa ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "   empresa.idEmpresa=?"
                + "\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, empresa.getIdEmpresa());

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getOfertasGroupByFamilia(Centro centro) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN ( Oferta as oferta  INNER  JOIN Empresa as empresa ON oferta.idEmpresa=empresa.idEmpresa ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "   empresa.idCentro=?"
                + "\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getCandidatosGroupByFamilia() {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN ( Oferta as oferta  INNER  JOIN Empresa as empresa ON oferta.idEmpresa=empresa.idEmpresa INNER  JOIN Candidato as candidato ON oferta.idOferta=candidato.idOferta ) ON familia.idFamilia=oferta.idFamilia\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getCandidatosGroupByFamilia(Empresa empresa) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN ( Oferta as oferta  INNER  JOIN Empresa as empresa ON oferta.idEmpresa=empresa.idEmpresa INNER  JOIN Candidato as candidato ON oferta.idOferta=candidato.idOferta ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "  empresa.idEmpresa=?\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, empresa.getIdEmpresa());

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getCandidatosGroupByFamilia(Centro centro) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN ( Oferta as oferta  INNER  JOIN Empresa as empresa ON oferta.idEmpresa=empresa.idEmpresa INNER  JOIN Candidato as candidato ON oferta.idOferta=candidato.idOferta ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "  empresa.idCentro=?\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getTituladosGroupByFamilia() {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN (Ciclo ciclo  JOIN FormacionAcademica as formacionAcademica  ON ciclo.idCiclo=formacionAcademica.idCiclo) ON familia.idFamilia=ciclo.idFamilia\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getTituladosGroupByFamilia(Centro centro) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   Familia as familia JOIN (Ciclo ciclo  JOIN FormacionAcademica as formacionAcademica  ON ciclo.idCiclo=formacionAcademica.idCiclo) ON familia.idFamilia=ciclo.idFamilia\n"
                + "WHERE\n"
                + "   formacionAcademica.idCentro=?\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());

        return getListFamiliaEstadisticaDAO(sqlQuery.list());
    }

}
