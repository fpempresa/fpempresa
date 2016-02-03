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
import es.logongas.ix3.dao.DataSession;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class EstadisticaDAOImplHibernate implements EstadisticaDAO {

    @Override
    public List<FamiliaEstadistica> getOfertasGroupByFamilia(DataSession dataSession) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa ) ON familia.idFamilia=oferta.idFamilia\n"
                + "\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getOfertasGroupByFamilia(DataSession dataSession, Empresa empresa) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "   empresa.idEmpresa=?"
                + "\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, empresa.getIdEmpresa());

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getOfertasGroupByFamilia(DataSession dataSession, Centro centro) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "   empresa.idCentro=?"
                + "\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getCandidatosGroupByFamilia(DataSession dataSession) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa INNER  JOIN candidato ON oferta.idOferta=candidato.idOferta ) ON familia.idFamilia=oferta.idFamilia\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getCandidatosGroupByFamilia(DataSession dataSession, Empresa empresa) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa INNER  JOIN candidato ON oferta.idOferta=candidato.idOferta ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "  empresa.idEmpresa=?\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, empresa.getIdEmpresa());

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getCandidatosGroupByFamilia(DataSession dataSession, Centro centro) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa INNER  JOIN candidato ON oferta.idOferta=candidato.idOferta ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "  empresa.idCentro=?\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getTituladosGroupByFamilia(DataSession dataSession) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN (ciclo  JOIN formacionacademica  ON ciclo.idCiclo=formacionacademica.idCiclo) ON familia.idFamilia=ciclo.idFamilia\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getTituladosGroupByFamilia(DataSession dataSession, Centro centro) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN (ciclo  JOIN formacionacademica  ON ciclo.idCiclo=formacionacademica.idCiclo) ON familia.idFamilia=ciclo.idFamilia\n"
                + "WHERE\n"
                + "   formacionacademica.idCentro=?\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    private List<FamiliaEstadistica> getListFamiliaEstadistica(List<Object[]> datos) {
        List<FamiliaEstadistica> familiaEstadisticas = new ArrayList<FamiliaEstadistica>();

        for (Object[] dato : datos) {
            FamiliaEstadistica familiaEstadistica = new FamiliaEstadistica((Integer) dato[0], (String) dato[1],((Number) dato[2]).intValue());

            familiaEstadisticas.add(familiaEstadistica);
        }

        return familiaEstadisticas;
    }    
    
    
}
