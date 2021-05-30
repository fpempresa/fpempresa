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
package es.logongas.fpempresa.dao.titulado.impl;

import es.logongas.fpempresa.dao.titulado.FormacionAcademicaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class FormacionAcademicaDAOImplHibernate extends GenericDAOImplHibernate<FormacionAcademica, Integer> implements FormacionAcademicaDAO {

    @Override
    public FormacionAcademica findByCentroAnyoCicloNumeroDocumento(DataSession dataSession, Centro centro, int anyo, Ciclo ciclo, TipoDocumento tipoDocumento, String numeroDocumento) throws BusinessException{
        FormacionAcademica formacionAcademica;
        
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        if (centro==null) {
            return null;
        }
        if (ciclo==null) {
            return null;
        }
        
        String sql = "SELECT\n" +
            "	idFormacionAcademica\n" +
            "FROM\n" +
            "	FormacionAcademica INNER JOIN\n" +
            "	Titulado ON Titulado.idTitulado=FormacionAcademica.idTitulado\n" +
            "WHERE\n" +
            "	FormacionAcademica.idCentro=? AND\n" +
            "	Year(FormacionAcademica.fecha)=? AND\n" +
            "	FormacionAcademica.idCiclo=? AND\n" +
            "	Titulado.tipoDocumento=? AND\n" +
            "	Titulado.numeroDocumento=?";

        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, centro.getIdCentro());
        sqlQuery.setInteger(1, anyo);
        sqlQuery.setInteger(2, ciclo.getIdCiclo());
        sqlQuery.setString(3, tipoDocumento.name());
        sqlQuery.setString(4, numeroDocumento);

        Integer idFormacionAcademica=(Integer)sqlQuery.uniqueResult();
        
        if (idFormacionAcademica==null) {
            formacionAcademica=null;
        } else {
            formacionAcademica=this.read(dataSession, idFormacionAcademica);
        }

        return formacionAcademica;
    }
    
}
