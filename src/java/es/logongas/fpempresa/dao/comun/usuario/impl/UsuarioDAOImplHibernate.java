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

package es.logongas.fpempresa.dao.comun.usuario.impl;

import es.logongas.fpempresa.dao.comun.usuario.UsuarioDAO;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 *
 * @author Lorenzo
 */
public class UsuarioDAOImplHibernate extends GenericDAOImplHibernate<Usuario,Integer> implements UsuarioDAO  {

    @Override
    public void updateEncryptedPassword(Usuario usuario,String encriptedPassword){
       
        
        Session session=sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("UPDATE Usuario SET password=? WHERE idIdentity=?");
        query.setString(0, encriptedPassword);
        query.setInteger(1, usuario.getIdIdentity());
        
        query.executeUpdate(); 
    }
    
    @Override  
    public String getEncryptedPassword(Usuario usuario) {
        Session session=sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("SELECT password FROM Usuario WHERE idIdentity=?");
        query.setInteger(0, usuario.getIdIdentity());
        String encryptedPassword=(String)query.uniqueResult();
        
        return encryptedPassword;
    }

 
}
