/**
 *   FPempresa
 *   Copyright (C) 2014  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.logongas.fpempresa.persistencia.impl.dao.comun;

import es.logongas.fpempresa.modelo.comun.Usuario;
import es.logongas.fpempresa.persistencia.services.dao.comun.UsuarioDAO;
import es.logongas.ix3.persistence.impl.hibernate.dao.GenericDAOImplHibernate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Lorenzo
 */
public class UsuarioDAOImplHibernate extends GenericDAOImplHibernate<Usuario,Integer> implements UsuarioDAO  {

    @Override
    public String getEncryptedPassword(int idIdentity) {
        Session session=sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("SELECT contrasenya FROM Usuario WHERE idIdentity=?");
        query.setInteger(0, idIdentity);
        String encryptedPassword=(String)query.uniqueResult();
        
        return encryptedPassword;
    }
    
    @Override
    public void setEncryptedPassword(int idIdentity,String encryptedPassword) {
        Session session=sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("UPDATE Usuario SET contrasenya=? WHERE idIdentity=?");
        query.setString(0, encryptedPassword);
        query.setInteger(1, idIdentity);
        
        query.executeUpdate();
        
    }
        

}
