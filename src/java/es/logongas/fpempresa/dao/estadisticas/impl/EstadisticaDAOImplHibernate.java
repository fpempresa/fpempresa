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
import es.logongas.fpempresa.modelo.comun.geo.ComunidadAutonoma;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.CicloEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.DataValue;
import es.logongas.fpempresa.modelo.estadisticas.Estadistica;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.GroupByEstadistica;
import es.logongas.ix3.dao.DataSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public Estadistica getEstadisticaOfertas(DataSession dataSession,GroupByEstadistica groupByEstadistica, Date filterDesde,Date filterHasta,ComunidadAutonoma filterComunidadAutonoma,Provincia filterProvincia,Familia filterFamilia,Ciclo filterCiclo) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String title;
        String xLabel;
        String yLabel="Nº de Ofertas";    
        List<DataValue> dataValues;
        
        if ((filterProvincia!=null) && (filterComunidadAutonoma==null)) {
            throw new RuntimeException("No es posible que esté la provincia sin que esté la comunidad autónoma");
        }
        if ((filterCiclo!=null) && (filterFamilia==null)) {
            throw new RuntimeException("No es posible que esté el ciclo sin que esté la familia");
        }  
        
        if ((filterProvincia!=null) && (filterComunidadAutonoma!=null)) {
            if (filterProvincia.getComunidadAutonoma().getIdComunidadAutonoma()!=filterComunidadAutonoma.getIdComunidadAutonoma()) {
                throw new RuntimeException("La comunidad autonoma de la provincia no coincide con la comunidad autonoma");
            }
        } 
        
        if ((filterCiclo!=null) && (filterFamilia!=null)) {
            if (filterCiclo.getFamilia().getIdFamilia()!=filterFamilia.getIdFamilia()) {
                throw new RuntimeException("La familia del ciclo no coincide con la familia");
            }
        } 
        
        boolean addFromCiclos=false;
        if (filterCiclo!=null) {
            addFromCiclos=true;
        }
        
        String selectColumns;
        String groupBy;  
        
        
        switch (groupByEstadistica) {
            case Ninguna:
                selectColumns="COUNT(*) AS num";
                groupBy=""; 
                xLabel="";
                title="Todas las ofertas";
                break;
            case Ubicacion:
                if ((filterComunidadAutonoma==null) && (filterProvincia==null)) {
                    selectColumns="COUNT(*) AS num,ComunidadAutonoma.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Comunidad autónoma";
                    title="Ofertas por comunidad";                    
                } else if ((filterComunidadAutonoma==null) && (filterProvincia!=null)) {
                    throw new RuntimeException("Error de lógica no puede ser que esté la provincia y no la comunidad autónoma:"+filterComunidadAutonoma+","+filterProvincia);
                } else if ((filterComunidadAutonoma!=null) && (filterProvincia==null)) {
                    selectColumns="COUNT(*) AS num,provincia.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Provincia";
                    title="Ofertas por provincia";                     
                } else if ((filterComunidadAutonoma!=null) && (filterProvincia!=null)) {
                    selectColumns="COUNT(*) AS num";
                    groupBy="";
                    xLabel="";
                    title="Todas las ofertas";                      
                } else {
                    throw new RuntimeException("Error de lógica:"+filterComunidadAutonoma+","+filterProvincia);
                }
                
                break;
            case CatalogoAcademico:
                if ((filterFamilia==null) && (filterCiclo==null)) {
                    selectColumns="COUNT(*) AS num,familia.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Familia profesinal";
                    title="Ofertas por familia profesional";                      
                } else if ((filterFamilia==null) && (filterCiclo!=null)) {
                    throw new RuntimeException("Error de lógica no puede ser que esté el ciclo y no la familia:"+filterFamilia+","+filterCiclo);
                } else if ((filterFamilia!=null) && (filterCiclo==null)) {
                    selectColumns="COUNT(*) AS num,ciclo.descripcion as g1";
                    groupBy="GROUP BY g1";                    
                    addFromCiclos=true;
                    xLabel="Ciclo formativo";
                    title="Ofertas por ciclo formativo";                      
                } else if ((filterFamilia!=null) && (filterCiclo!=null)) {
                    selectColumns="COUNT(*) AS num";
                    groupBy="";
                    xLabel="";
                    title="Todas las ofertas";                      
                } else {
                    throw new RuntimeException("Error de lógica:"+filterFamilia+","+filterCiclo);
                }                
                
                break;
            default:
                throw new RuntimeException("groupByEstadistica desconocido:"+groupByEstadistica);
        }
        
        
        StringBuilder sbWhere=new StringBuilder();
        if (filterDesde!=null) {
            sbWhere.append(" AND DATE(oferta.fecha)>=DATE(?)");
        }
        if (filterHasta!=null) {
            sbWhere.append(" AND DATE(oferta.fecha)<=DATE(?)");
        }        
        if (filterComunidadAutonoma!=null) {
            sbWhere.append(" AND ComunidadAutonoma.idComunidadAutonoma=?");
        }  
        if (filterProvincia!=null) {
            sbWhere.append(" AND provincia.idProvincia=?");
        }         
        if (filterFamilia!=null) {
            sbWhere.append(" AND familia.idFamilia=?");
        } 
        if (filterCiclo!=null) {
            sbWhere.append(" AND ciclo.idCiclo=?");
        } 

        String sql = 
                "SELECT \n" +
                selectColumns + "\n"+
                "FROM \n" +
                "	oferta INNER JOIN \n" +
                "	familia ON oferta.idFamilia=familia.idFamilia INNER JOIN\n" +
                (addFromCiclos==true?"	ofertaciclo ON oferta.idOferta=ofertaciclo.idOferta INNER JOIN\n":"") +
                (addFromCiclos==true?"	ciclo ON ofertaciclo.idCiclo=ciclo.idCiclo INNER JOIN \n":"") +
                "	municipio ON oferta.idMunicipio=municipio.idMunicipio INNER JOIN\n" +
                "	provincia ON provincia.idProvincia=municipio.idProvincia INNER JOIN\n" +
                "	ComunidadAutonoma ON ComunidadAutonoma.idComunidadAutonoma=provincia.idComunidadAutonoma\n" +
                "	\n" +
                "WHERE \n" +
                "       1=1 " + sbWhere + "\n" +
                "       " + groupBy + "\n" +
                "ORDER BY \n" +
                "	num DESC";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        int iParam=0;
        
        if (filterDesde!=null) {
            sqlQuery.setDate(iParam++,filterDesde );
        }
        if (filterHasta!=null) {
            sqlQuery.setDate(iParam++,filterHasta );
        }       
        if (filterComunidadAutonoma!=null) {
            sqlQuery.setInteger(iParam++, filterComunidadAutonoma.getIdComunidadAutonoma());
        }  
        if (filterProvincia!=null) {
            sqlQuery.setInteger(iParam++, filterProvincia.getIdProvincia());
        }         
        if (filterFamilia!=null) {
            sqlQuery.setInteger(iParam++, filterFamilia.getIdFamilia());
        } 
        if (filterCiclo!=null) {
            sqlQuery.setInteger(iParam++, filterCiclo.getIdCiclo());
        } 

        dataValues=getListDataValuesFromDatos(sqlQuery.list());
        
        Estadistica estadistica=new Estadistica(title, xLabel, yLabel, dataValues);
        
        return estadistica;
    }

    @Override
    public Estadistica getEstadisticaCandidatos(DataSession dataSession,GroupByEstadistica groupByEstadistica, Date filterDesde,Date filterHasta,ComunidadAutonoma filterComunidadAutonoma,Provincia filterProvincia,Familia filterFamilia,Ciclo filterCiclo) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String title;
        String xLabel;
        String yLabel="Nº de candidatos";    
        List<DataValue> dataValues;
    
    
        if ((filterProvincia!=null) && (filterComunidadAutonoma==null)) {
            throw new RuntimeException("No es posible que esté la provincia sin que esté la comunidad autónoma");
        }
        if ((filterCiclo!=null) && (filterFamilia==null)) {
            throw new RuntimeException("No es posible que esté el ciclo sin que esté la familia");
        }  
        
        if ((filterProvincia!=null) && (filterComunidadAutonoma!=null)) {
            if (filterProvincia.getComunidadAutonoma().getIdComunidadAutonoma()!=filterComunidadAutonoma.getIdComunidadAutonoma()) {
                throw new RuntimeException("La comunidad autonoma de la provincia no coincide con la comunidad autonoma");
            }
        } 
        
        if ((filterCiclo!=null) && (filterFamilia!=null)) {
            if (filterCiclo.getFamilia().getIdFamilia()!=filterFamilia.getIdFamilia()) {
                throw new RuntimeException("La familia del ciclo no coincide con la familia");
            }
        } 
        
        boolean addFromCiclos=false;
        if (filterCiclo!=null) {
            addFromCiclos=true;
        }
        
        String selectColumns;
        String groupBy;  
        
        
        switch (groupByEstadistica) {
            case Ninguna:
                selectColumns="COUNT(*) AS num";
                groupBy="";
                xLabel="";
                title="Todos los candidatos";
                break;                 
            case Ubicacion:
                
                if ((filterComunidadAutonoma==null) && (filterProvincia==null)) {
                    selectColumns="COUNT(*) AS num,ComunidadAutonoma.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Comunidad autónoma";
                    title="Candidatos por comunidad";                      
                } else if ((filterComunidadAutonoma==null) && (filterProvincia!=null)) {
                    throw new RuntimeException("Error de lógica no puede ser que esté la provincia y no la comunidad autónoma:"+filterComunidadAutonoma+","+filterProvincia);
                } else if ((filterComunidadAutonoma!=null) && (filterProvincia==null)) {
                    selectColumns="COUNT(*) AS num,provincia.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Provincia";
                    title="Candidatos por provincia";                     
                } else if ((filterComunidadAutonoma!=null) && (filterProvincia!=null)) {
                    selectColumns="COUNT(*) AS num";
                    groupBy="";
                    xLabel="";
                    title="Todos los candidatos";                     
                } else {
                    throw new RuntimeException("Error de lógica:"+filterComunidadAutonoma+","+filterProvincia);
                }
                
                break;
            case CatalogoAcademico:
                
                if ((filterFamilia==null) && (filterCiclo==null)) {
                    selectColumns="COUNT(*) AS num,familia.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Familia profesional";
                    title="Candidatos por familia profesional";                     
                } else if ((filterFamilia==null) && (filterCiclo!=null)) {
                    throw new RuntimeException("Error de lógica no puede ser que esté el ciclo y no la familia:"+filterFamilia+","+filterCiclo);
                } else if ((filterFamilia!=null) && (filterCiclo==null)) {
                    selectColumns="COUNT(*) AS num,ciclo.descripcion as g1";
                    groupBy="GROUP BY g1";
                    addFromCiclos=true;
                    xLabel="Ciclo formativo";
                    title="Candidatos por ciclo formativo";                     
                } else if ((filterFamilia!=null) && (filterCiclo!=null)) {
                    selectColumns="COUNT(*) AS num";
                    groupBy="";
                    xLabel="";
                    title="Todos los candidatos";                     
                } else {
                    throw new RuntimeException("Error de lógica:"+filterFamilia+","+filterCiclo);
                }                
                
                break;
            default:
                throw new RuntimeException("groupByEstadistica desconocido:"+groupByEstadistica);
        }
        
        
        StringBuilder sbWhere=new StringBuilder();
        if (filterDesde!=null) {
            sbWhere.append(" AND DATE(candidato.fecha)>=DATE(?)");
        }
        if (filterHasta!=null) {
            sbWhere.append(" AND DATE(candidato.fecha)<=DATE(?)");
        }        
        if (filterComunidadAutonoma!=null) {
            sbWhere.append(" AND ComunidadAutonoma.idComunidadAutonoma=?");
        }  
        if (filterProvincia!=null) {
            sbWhere.append(" AND provincia.idProvincia=?");
        }         
        if (filterFamilia!=null) {
            sbWhere.append(" AND familia.idFamilia=?");
        } 
        if (filterCiclo!=null) {
            sbWhere.append(" AND ciclo.idCiclo=?");
        } 

        String sql = 
                "SELECT \n" +
                selectColumns + "\n"+
                "FROM \n" +
                "	candidato INNER JOIN \n" +
                "	usuario ON usuario.idIdentity=candidato.idCandidato INNER JOIN \n" +
                "	titulado ON titulado.idTitulado=usuario.idTitulado INNER JOIN    \n" +             
                "	oferta ON oferta.idOferta=candidato.idOferta INNER JOIN\n" +
                "	familia ON oferta.idFamilia=familia.idFamilia INNER JOIN\n" +
                (addFromCiclos==true?"	ofertaciclo ON oferta.idOferta=ofertaciclo.idOferta INNER JOIN\n":"") +
                (addFromCiclos==true?"	ciclo ON ofertaciclo.idCiclo=ciclo.idCiclo INNER JOIN \n":"") +
                "	municipio ON municipio.idMunicipio=titulado.idMunicipio INNER JOIN\n" +
                "	provincia ON provincia.idProvincia=municipio.idProvincia INNER JOIN\n" +
                "	ComunidadAutonoma ON ComunidadAutonoma.idComunidadAutonoma=provincia.idComunidadAutonoma\n" +
                "	\n" +
                "WHERE \n" +
                "       1=1 " + sbWhere + "\n" +
                "       " + groupBy + "\n" +
                "ORDER BY \n" +
                "	num DESC";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        int iParam=0;
        
        if (filterDesde!=null) {
            sqlQuery.setDate(iParam++,filterDesde );
        }
        if (filterHasta!=null) {
            sqlQuery.setDate(iParam++,filterHasta );
        }       
        if (filterComunidadAutonoma!=null) {
            sqlQuery.setInteger(iParam++, filterComunidadAutonoma.getIdComunidadAutonoma());
        }  
        if (filterProvincia!=null) {
            sqlQuery.setInteger(iParam++, filterProvincia.getIdProvincia());
        }         
        if (filterFamilia!=null) {
            sqlQuery.setInteger(iParam++, filterFamilia.getIdFamilia());
        } 
        if (filterCiclo!=null) {
            sqlQuery.setInteger(iParam++, filterCiclo.getIdCiclo());
        } 

        dataValues=getListDataValuesFromDatos(sqlQuery.list());
        
        Estadistica estadistica=new Estadistica(title, xLabel, yLabel, dataValues);
        
        return estadistica;

    }    
    

    @Override
    public Estadistica getEstadisticaEmpresas(DataSession dataSession,GroupByEstadistica groupByEstadistica, Date filterDesde,Date filterHasta,ComunidadAutonoma filterComunidadAutonoma,Provincia filterProvincia,Familia filterFamilia,Ciclo filterCiclo) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String title;
        String xLabel;
        String yLabel="Nº de empresas";    
        List<DataValue> dataValues;
        
        if ((filterProvincia!=null) && (filterComunidadAutonoma==null)) {
            throw new RuntimeException("No es posible que esté la provincia sin que esté la comunidad autónoma");
        }
        if ((filterCiclo!=null) && (filterFamilia==null)) {
            throw new RuntimeException("No es posible que esté el ciclo sin que esté la familia");
        }  
        
        if ((filterProvincia!=null) && (filterComunidadAutonoma!=null)) {
            if (filterProvincia.getComunidadAutonoma().getIdComunidadAutonoma()!=filterComunidadAutonoma.getIdComunidadAutonoma()) {
                throw new RuntimeException("La comunidad autonoma de la provincia no coincide con la comunidad autonoma");
            }
        } 
        
        if ((filterCiclo!=null) && (filterFamilia!=null)) {
            if (filterCiclo.getFamilia().getIdFamilia()!=filterFamilia.getIdFamilia()) {
                throw new RuntimeException("La familia del ciclo no coincide con la familia");
            }
        } 
        
        boolean addFromFamilia=false;
        if (filterFamilia!=null) {
            addFromFamilia=true;
        }
        boolean addFromCiclos=false;
        if (filterCiclo!=null) {
            addFromCiclos=true;
        }
        
        String selectColumns;
        String groupBy;  
        
        
        switch (groupByEstadistica) {
            case Ninguna:
                selectColumns="COUNT(*) AS num";
                groupBy="";
                xLabel="";
                title="Todas las empresas";             
                break;                
            case Ubicacion:
                if ((filterComunidadAutonoma==null) && (filterProvincia==null)) {
                    selectColumns="COUNT(*) AS num,ComunidadAutonoma.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Comunidad autónoma";
                    title="Empresas por comunidad";                     
                } else if ((filterComunidadAutonoma==null) && (filterProvincia!=null)) {
                    throw new RuntimeException("Error de lógica no puede ser que esté la provincia y no la comunidad autónoma:"+filterComunidadAutonoma+","+filterProvincia);
                } else if ((filterComunidadAutonoma!=null) && (filterProvincia==null)) {
                    selectColumns="COUNT(*) AS num,provincia.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Provincia";
                    title="Empresas por provincia";                     
                } else if ((filterComunidadAutonoma!=null) && (filterProvincia!=null)) {
                    selectColumns="COUNT(*) AS num";
                    groupBy="";
                    xLabel="";
                    title="Todas las empresas";                     
                } else {
                    throw new RuntimeException("Error de lógica:"+filterComunidadAutonoma+","+filterProvincia);
                }
                
                break;
            case CatalogoAcademico:               
                if ((filterFamilia==null) && (filterCiclo==null)) {
                    selectColumns="COUNT(*) AS num,familia.descripcion as g1";
                    groupBy="GROUP BY g1";
                    xLabel="Familia profesional";
                    title="Empresas por familia profesional"; 
                    addFromFamilia=true;
                } else if ((filterFamilia==null) && (filterCiclo!=null)) {
                    throw new RuntimeException("Error de lógica no puede ser que esté el ciclo y no la familia:"+filterFamilia+","+filterCiclo);
                } else if ((filterFamilia!=null) && (filterCiclo==null)) {
                    selectColumns="COUNT(*) AS num,ciclo.descripcion as g1";
                    groupBy="GROUP BY g1";
                    addFromCiclos=true;
                    xLabel="Ciclo formativo";
                    title="Empresas por ciclo formativo"; 
                    addFromCiclos=true;                    
                } else if ((filterFamilia!=null) && (filterCiclo!=null)) {
                    selectColumns="COUNT(*) AS num";
                    groupBy="";
                    xLabel="";
                    title="Todas las empresas";                     
                } else {
                    throw new RuntimeException("Error de lógica:"+filterFamilia+","+filterCiclo);
                }                
                
                break;
            default:
                throw new RuntimeException("groupByEstadistica desconocido:"+groupByEstadistica);                                
        }
        
        
        StringBuilder sbWhere=new StringBuilder();
        if (filterDesde!=null) {
            sbWhere.append(" AND DATE(empresa.fecha)>=DATE(?)");
        }
        if (filterHasta!=null) {
            sbWhere.append(" AND DATE(empresa.fecha)<=DATE(?)");
        }        
        if (filterComunidadAutonoma!=null) {
            sbWhere.append(" AND ComunidadAutonoma.idComunidadAutonoma=?");
        }  
        if (filterProvincia!=null) {
            sbWhere.append(" AND provincia.idProvincia=?");
        }          
        if (filterFamilia!=null) {
            sbWhere.append(" AND familia.idFamilia=?");
        } 
        if (filterCiclo!=null) {
            sbWhere.append(" AND ciclo.idCiclo=?");
        } 
        
        String sql = 
                "SELECT \n" +
                selectColumns + "\n"+
                "FROM \n" +
                "	empresa INNER JOIN \n" +
                (((addFromFamilia==true) || (addFromCiclos==true))?"	oferta ON oferta.idEmpresa=empresa.idEmpresa INNER JOIN\n":"") +
                (((addFromFamilia==true) || (addFromCiclos==true))?"	familia ON familia.idFamilia=oferta.idFamilia INNER JOIN \n":"") + 
                (addFromCiclos==true?"	ofertaciclo ON oferta.idOferta=ofertaciclo.idOferta INNER JOIN\n":"") +
                (addFromCiclos==true?"	ciclo ON ofertaciclo.idCiclo=ciclo.idCiclo INNER JOIN \n":"") +                
                "	municipio ON empresa.idMunicipio=municipio.idMunicipio INNER JOIN\n" +
                "	provincia ON provincia.idProvincia=municipio.idProvincia INNER JOIN\n" +
                "	ComunidadAutonoma ON ComunidadAutonoma.idComunidadAutonoma=provincia.idComunidadAutonoma\n" +
                "	\n" +
                "WHERE \n" +
                "       1=1 " + sbWhere + "\n" +
                "       " + groupBy + "\n" +
                "ORDER BY \n" +
                "	num DESC";
        
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        int iParam=0;
        
        if (filterDesde!=null) {
            sqlQuery.setDate(iParam++,filterDesde );
        }
        if (filterHasta!=null) {
            sqlQuery.setDate(iParam++,filterHasta );
        }       
        if (filterComunidadAutonoma!=null) {
            sqlQuery.setInteger(iParam++, filterComunidadAutonoma.getIdComunidadAutonoma());
        }  
        if (filterProvincia!=null) {
            sqlQuery.setInteger(iParam++, filterProvincia.getIdProvincia());
        }         
        if (filterFamilia!=null) {
            sqlQuery.setInteger(iParam++, filterFamilia.getIdFamilia());
        } 
        if (filterCiclo!=null) {
            sqlQuery.setInteger(iParam++, filterCiclo.getIdCiclo());
        }
        
        dataValues=getListDataValuesFromDatos(sqlQuery.list());
        
        Estadistica estadistica=new Estadistica(title, xLabel, yLabel, dataValues);
        
        return estadistica;

    }    
    
    
    private List<DataValue> getListDataValuesFromDatos(List<Object[]> datos) {
        List<DataValue> dataValues = new ArrayList<>();

        for (Object rawDato : datos) {
            String data;
            int value;
            if (rawDato instanceof BigInteger) {
                data="Todo";
                value=((BigInteger)rawDato).intValue();
            } else {
                Object[] dato=(Object[])rawDato;
                value=((Number) dato[0]).intValue(); 

                switch (dato.length) {
                    case 0:
                        data="Tod";
                        break;
                    case 2:
                        data=(String) dato[1];
                        break;
                    case 3:
                        data=(String) dato[1]+"-"+(String) dato[2];
                        break;
                    default:
                        throw new RuntimeException("El Nº de columna no es ǘalido:"+dato.length);
                }
            }
            
            DataValue dataValue = new DataValue(data, value);
            dataValues.add(dataValue);
        }
        return dataValues; 
    }
        
    
}
