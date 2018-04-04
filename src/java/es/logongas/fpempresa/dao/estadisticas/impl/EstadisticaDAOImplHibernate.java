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
import es.logongas.fpempresa.modelo.estadisticas.CicloEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaEstadistica;
import es.logongas.ix3.dao.DataSession;
import java.math.BigInteger;
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
                + "   familia.idFamilia,familia.descripcion, COUNT(DISTINCT formacionacademica.idTitulado) as valor\n"
                + "FROM\n"
                + "   familia JOIN (ciclo  JOIN formacionacademica  ON ciclo.idCiclo=formacionacademica.idCiclo) ON familia.idFamilia=ciclo.idFamilia\n"
                + "WHERE\n"
                + "   formacionacademica.idCentro=?\n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());
        return getListFamiliaEstadisticaConCiclos(dataSession, sqlQuery.list(), centro);
    }

    private List<FamiliaEstadistica> getListFamiliaEstadistica(List<Object[]> datos) {
        List<FamiliaEstadistica> familiaEstadisticas = new ArrayList<FamiliaEstadistica>();

        for (Object[] dato : datos) {
            FamiliaEstadistica familiaEstadistica = new FamiliaEstadistica((Integer) dato[0], (String) dato[1], ((Number) dato[2]).intValue());

            familiaEstadisticas.add(familiaEstadistica);
        }

        return familiaEstadisticas;
    }

    private List<FamiliaEstadistica> getListFamiliaEstadisticaConCiclos(DataSession dataSession, List<Object[]> datos, Centro centro) {
        List<FamiliaEstadistica> familiaEstadisticas = new ArrayList<FamiliaEstadistica>();
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "";

        for (Object[] dato : datos) {
            sql = "SELECT count(formacionacademica.idTitulado) as 'Valor', ciclo.descripcion, ciclo.idCiclo FROM (familia JOIN ciclo ON ciclo.idFamilia=familia.idFamilia) JOIN formacionacademica ON formacionacademica.idCiclo = ciclo.idCiclo  AND formacionacademica.idCentro = ? WHERE ciclo.idFamilia = ? GROUP BY ciclo.idCiclo";
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.setInteger(0, (Integer) centro.getIdCentro());
            sqlQuery.setInteger(1, (Integer) dato[0]);
            List<CicloEstadistica> listCiclos = getListCicloEstadistica(sqlQuery.list());
            FamiliaEstadistica familiaEstadistica = new FamiliaEstadistica((Integer) dato[0], (String) dato[1], ((Number) dato[2]).intValue(), listCiclos);

            familiaEstadisticas.add(familiaEstadistica);
        }

        return familiaEstadisticas;
    }

    private List<CicloEstadistica> getListCicloEstadistica(List<Object[]> datos) {
        List<CicloEstadistica> cicloEstadisticas = new ArrayList<CicloEstadistica>();

        for (Object[] dato : datos) {
            CicloEstadistica cicloEstadistica = new CicloEstadistica(((Number) dato[2]).intValue(), (String) dato[1], ((Number) dato[0]).intValue());
            cicloEstadisticas.add(cicloEstadistica);
        }
        return cicloEstadisticas;
    }

    @Override
    public Integer getSumCentros(DataSession dataSession) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT COUNT(*) FROM centro WHERE IdCentro>0";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        BigInteger sumCentros=(BigInteger)sqlQuery.list().get(0);
        return sumCentros.intValue();
}

    @Override
    public Integer getSumEmpresas(DataSession dataSession) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT COUNT(*) FROM empresa";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        BigInteger sumEmpresas=(BigInteger)sqlQuery.list().get(0);
        return sumEmpresas.intValue();
    }

}
