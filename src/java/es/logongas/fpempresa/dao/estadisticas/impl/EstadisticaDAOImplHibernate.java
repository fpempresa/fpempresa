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
    public List<FamiliaEstadistica> getOfertasGroupByFamilia(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "   empresa.idCentro=?"
                + (anyoInicio!=null?" AND YEAR(oferta.fecha)>=" + anyoInicio.intValue() + " ":"")
                + (anyoFin!=null?" AND YEAR(oferta.fecha)<=" + anyoFin.intValue() + " ":"")
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
    public List<FamiliaEstadistica> getCandidatosGroupByFamilia(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(*) as valor\n"
                + "FROM\n"
                + "   familia JOIN ( oferta  INNER  JOIN empresa ON oferta.idEmpresa=empresa.idEmpresa INNER  JOIN candidato ON oferta.idOferta=candidato.idOferta ) ON familia.idFamilia=oferta.idFamilia\n"
                + "WHERE\n"
                + "  empresa.idCentro=?\n"
                + (anyoInicio!=null?" AND YEAR(candidato.fecha)>=" + anyoInicio.intValue() + " ":"")
                + (anyoFin!=null?" AND YEAR(candidato.fecha)<=" + anyoFin.intValue() + " ":"")
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getTitulosFPGroupByFamilia(DataSession dataSession) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion,COUNT(formacionacademica.idTitulado) as valor\n"
                + "FROM\n"
                + "   familia JOIN (ciclo  JOIN formacionacademica  ON ciclo.idCiclo=formacionacademica.idCiclo) ON familia.idFamilia=ciclo.idFamilia\n"
                + "WHERE\n"
                + "   formacionacademica.tipoFormacionAcademica='CICLO_FORMATIVO' \n"
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);

        return getListFamiliaEstadistica(sqlQuery.list());
    }

    @Override
    public List<FamiliaEstadistica> getTitulosFPGroupByFamilia(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT \n"
                + "   familia.idFamilia,familia.descripcion, COUNT(formacionacademica.idTitulado) as valor\n"
                + "FROM\n"
                + "   familia JOIN (ciclo  JOIN formacionacademica  ON ciclo.idCiclo=formacionacademica.idCiclo) ON familia.idFamilia=ciclo.idFamilia\n"
                + "WHERE\n"
                + "   formacionacademica.tipoFormacionAcademica='CICLO_FORMATIVO' AND \n"
                + "   formacionacademica.idCentro=?\n"
                + (anyoInicio!=null?" AND YEAR(formacionacademica.fecha)>=" + anyoInicio.intValue() + " ":"")
                + (anyoFin!=null?" AND YEAR(formacionacademica.fecha)<=" + anyoFin.intValue() + " ":"")              
                + "GROUP BY\n"
                + "	familia.idFamilia,familia.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());
        return getListFamiliaEstadisticaConCiclos(dataSession, sqlQuery.list(), centro, anyoInicio, anyoFin);
    }

    private List<FamiliaEstadistica> getListFamiliaEstadistica(List<Object[]> datos) {
        List<FamiliaEstadistica> familiaEstadisticas = new ArrayList<FamiliaEstadistica>();

        for (Object[] dato : datos) {
            FamiliaEstadistica familiaEstadistica = new FamiliaEstadistica((Integer) dato[0], (String) dato[1], ((Number) dato[2]).intValue());

            familiaEstadisticas.add(familiaEstadistica);
        }

        return familiaEstadisticas;
    }

    private List<FamiliaEstadistica> getListFamiliaEstadisticaConCiclos(DataSession dataSession, List<Object[]> datos, Centro centro,Integer anyoInicio,Integer anyoFin) {
        List<FamiliaEstadistica> familiaEstadisticas = new ArrayList<FamiliaEstadistica>();
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "";

        for (Object[] dato : datos) {
            sql =     "SELECT \n" 
                    + "  count(formacionacademica.idTitulado) as 'Valor', ciclo.descripcion, ciclo.idCiclo \n" 
                    + "FROM \n" 
                    + "  (familia JOIN ciclo ON ciclo.idFamilia=familia.idFamilia) JOIN formacionacademica ON formacionacademica.idCiclo = ciclo.idCiclo  AND formacionacademica.idCentro = ? " 
                    + "WHERE " 
                    + "   formacionacademica.tipoFormacionAcademica='CICLO_FORMATIVO' AND \n"
                    + "  ciclo.idFamilia = ? "
                    + (anyoInicio!=null?" AND YEAR(formacionacademica.fecha)>=" + anyoInicio.intValue() + " ":"")
                    + (anyoFin!=null?" AND YEAR(formacionacademica.fecha)<=" + anyoFin.intValue() + " ":"")                     
                    + "GROUP BY " 
                    + "  ciclo.idCiclo";
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
