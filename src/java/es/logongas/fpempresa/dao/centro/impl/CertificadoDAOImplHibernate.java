/*
 * FPempresa Copyright (C) 2021 Lorenzo González
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
package es.logongas.fpempresa.dao.centro.impl;

import es.logongas.fpempresa.dao.centro.CertificadoDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoAnyo;
import es.logongas.fpempresa.modelo.centro.CertificadoCiclo;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

/**
 *
 * @author logongas
 */
public class CertificadoDAOImplHibernate implements CertificadoDAO {
    
    @Override
    public List<CertificadoAnyo> getCertificadosAnyoCentro(DataSession dataSession, Centro centro) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT DISTINCT\n"
                + "  YEAR(FormAcad.fecha) AS anyo,\n"
                + "  (SELECT COUNT(*) FROM FormacionAcademica FA  WHERE FA.borrado=0 AND YEAR(FA.Fecha)=YEAR(FormAcad.fecha) AND FA.idCentro=FormAcad.idCentro AND FA.tipoFormacionAcademica='CICLO_FORMATIVO') AS numTitulosTotales,\n"
                + "  (SELECT COUNT(*) FROM FormacionAcademica FA  WHERE FA.borrado=0 AND YEAR(FA.Fecha)=YEAR(FormAcad.fecha) AND FA.idCentro=FormAcad.idCentro AND FA.tipoFormacionAcademica='CICLO_FORMATIVO' AND FA.CertificadoTitulo=1) AS numTitulosCertificados\n"
                + "FROM\n"
                + "	FormacionAcademica FormAcad  \n"
                + "WHERE\n"
                + "	FormAcad.idCentro=? AND\n"
                + "	FormAcad.borrado=0 AND \n"
                + "	tipoFormacionAcademica='CICLO_FORMATIVO' \n"
                + "ORDER BY Anyo DESC";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        sqlQuery.setInteger(0, centro.getIdCentro());

        List<CertificadoAnyo> certificadosAnyos=getDataFromSQLQuery(sqlQuery,CertificadoAnyo.class);

        return certificadosAnyos;

    }

    @Override
    public List<CertificadoCiclo> getCertificadosCicloCentro(DataSession dataSession, Centro centro, int anyo) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        String sql = "SELECT DISTINCT\n"
                + "  Ciclo.idCiclo AS idCiclo,\n"
                + "  Ciclo.idFamilia AS idFamilia,\n"
                + "  Ciclo.descripcion AS nombreCiclo,\n"
                + "  (SELECT COUNT(*) FROM FormacionAcademica FA   WHERE FA.borrado=0 AND YEAR(FA.Fecha)=YEAR(FormAcad.fecha) AND FA.idCiclo=Ciclo.idCiclo AND FA.idCentro=FormAcad.idCentro AND FA.tipoFormacionAcademica='CICLO_FORMATIVO' ) AS numTitulosTotales,\n"
                + "  (SELECT COUNT(*) FROM FormacionAcademica FA   WHERE FA.borrado=0 AND YEAR(FA.Fecha)=YEAR(FormAcad.fecha) AND FA.idCiclo=Ciclo.idCiclo AND FA.idCentro=FormAcad.idCentro AND FA.tipoFormacionAcademica='CICLO_FORMATIVO'  AND FA.CertificadoTitulo=1) AS numTitulosCertificados\n"
                + "FROM\n"
                + "	FormacionAcademica FormAcad INNER JOIN \n"
                + "	Ciclo ON FormAcad.idCiclo=Ciclo.idCiclo \n"
                + "WHERE\n"
                + "	FormAcad.idCentro=? AND\n"
                + "	YEAR(FormAcad.fecha)=? AND\n"
                + "	FormAcad.borrado=0 AND \n"
                + "	tipoFormacionAcademica='CICLO_FORMATIVO' \n"
                + "ORDER BY Ciclo.idFamilia,Ciclo.descripcion";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        sqlQuery.setInteger(0, centro.getIdCentro());
        sqlQuery.setInteger(1, anyo);

        List<CertificadoCiclo> certificadosAnyos=getDataFromSQLQuery(sqlQuery,CertificadoCiclo.class);

        return certificadosAnyos;
    }

    @Override
    public List<CertificadoTitulo> getCertificadosTituloCentro(DataSession dataSession, Centro centro, int anyo, Ciclo ciclo) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();
        
        
        String sql = "SELECT \n"
                + "  Titulado.tipoDocumento AS tipoDocumento,\n"
                + "  Titulado.numeroDocumento AS nif,\n"
                + "  Usuario.nombre AS nombre,\n"
                + "  Usuario.apellidos AS apellidos,\n"
                + "  FormAcad.certificadoTitulo AS certificadoTitulo,\n"
                + "  FormAcad.idFormacionAcademica AS idFormacionAcademica\n"
                + "FROM\n"
                + "	FormacionAcademica FormAcad INNER JOIN \n"
                + "	Titulado ON  FormAcad.idTitulado=Titulado.idTitulado INNER JOIN \n"
                + "	Usuario ON  Usuario.idTitulado=Titulado.idTitulado \n"
                + "WHERE\n"
                + "	FormAcad.idCentro=? AND\n"
                + "	YEAR(FormAcad.fecha)=? AND\n"
                + "	FormAcad.idCiclo=? AND\n"
                + "	FormAcad.borrado=0 AND \n"
                + "	tipoFormacionAcademica='CICLO_FORMATIVO'\n"
                + "	 \n"                
                + "ORDER BY Usuario.apellidos,Usuario.nombre,FormAcad.idFormacionAcademica";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        sqlQuery.setInteger(0, centro.getIdCentro());
        sqlQuery.setInteger(1, anyo);
        sqlQuery.setInteger(2, ciclo.getIdCiclo());

        List<CertificadoTitulo> certificadosTitulos=getDataFromSQLQuery(sqlQuery,CertificadoTitulo.class);

        return certificadosTitulos;
    }

    private <T> List<T> getDataFromSQLQuery(SQLQuery sqlQuery, Class<T> clazz) {
        try {

            List<T> datos = new ArrayList<>();

            for (Map<String, Object> fila : (List<Map<String, Object>>) sqlQuery.list()) {
                T obj = clazz.newInstance();

                beanMapper(obj, fila);

                datos.add(obj);
            }

            return datos;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void beanMapper(Object obj, Map<String, Object> fila) {

        for (Map.Entry<String, Object> celda : fila.entrySet()) {
            Object value = celda.getValue();
            String columnName = celda.getKey();

            Field field=ReflectionUtil.getField(obj.getClass(), columnName);
            if (field==null) {
                throw new RuntimeException("No existe el campo " + columnName + " en la clase " + obj.getClass());
            }
            Class type=field.getType();
            
             if (value != null) {
            
                if (value instanceof BigInteger) {
                    value = ((BigInteger) value).longValue();
                }
                if (value instanceof Integer) {
                    value = ((Integer) value).longValue();
                }                
                 
                if ((type.equals(Boolean.TYPE)) || (type.equals(Boolean.class))) {
                    if ((Long)value==1) {
                        value=Boolean.TRUE;
                    } else if ((Long)value==0) {
                        value=Boolean.FALSE;
                    } else {
                        throw new RuntimeException("El valor de la columna solo puede ser NULL, 0 o 1." + value );
                    }
                } else if (type.isEnum()) {
                    Enum[] enumerados=(Enum[])type.getEnumConstants();
                    
                    for (Enum enumerado:enumerados) {
                        if (enumerado.name().equals(value)) {
                            value=enumerado;
                        }
                    }
                }
            }
            
            ReflectionUtil.setValueToBean(obj, columnName, value);
        }

    }

    
    
}
