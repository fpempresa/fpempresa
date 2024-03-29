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
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author logongas
 */
public class FormacionAcademicaDAOImplHibernate extends GenericDAOImplHibernate<FormacionAcademica, Integer> implements FormacionAcademicaDAO {

    @Override
    public void softDelete(DataSession dataSession, int idFormacionAcademica)  {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("UPDATE FormacionAcademica SET borrado=1 WHERE idFormacionAcademica=?");
        query.setInteger(0, idFormacionAcademica);

        int result=query.executeUpdate(); 
        
        if (result!=1) {
            throw new RuntimeException("Se debería haber actualizado una única fila pero se han actualizado:" + result + " , idFormacionAcademica=" + idFormacionAcademica);
        }
    };
    
}
