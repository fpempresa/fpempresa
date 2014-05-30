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
    public void updatePassword(Usuario usuario,String plainPassword){
       
        
        Session session=sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("UPDATE Usuario SET password=? WHERE idIdentity=?");
        query.setString(0, getEncryptedPasswordFromPlainPassword(plainPassword));
        query.setInteger(1, usuario.getIdIdentity());
        
        query.executeUpdate(); 
    };
    
    @Override
    public boolean checkPassword(Usuario usuario,String plainPassword){
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        
        String encryptedPassword=getEncryptedPasswordFromUsuario(usuario);
        
        boolean checkOK=passwordEncryptor.checkPassword(plainPassword, encryptedPassword);
        
        return checkOK;
    };

    @Override
    protected void preInsertBeforeTransaction(Session session, Usuario usuario) {
        //Encriptamos el password antes de guardarlo
        usuario.setPassword(getEncryptedPasswordFromPlainPassword(usuario.getPassword()));
    }   

    @Override
    protected void preUpdateBeforeTransaction(Session session, Usuario usuario) {
        usuario.setPassword(getEncryptedPasswordFromUsuario(usuario));
    }
    
        
    private String getEncryptedPasswordFromUsuario(Usuario usuario) {
        Session session=sessionFactory.getCurrentSession();
        
        Query query = session.createSQLQuery("SELECT password FROM Usuario WHERE idIdentity=?");
        query.setInteger(0, usuario.getIdIdentity());
        String encryptedPassword=(String)query.uniqueResult();
        
        return encryptedPassword;
    }
    
    
    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        
        String encryptedPassword=passwordEncryptor.encryptPassword(plainPassword); 
        
        return encryptedPassword;
    }    
}
