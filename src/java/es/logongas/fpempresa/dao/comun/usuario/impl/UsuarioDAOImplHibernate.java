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
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.impl.GenericDAOImplHibernate;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Lorenzo
 */
public class UsuarioDAOImplHibernate extends GenericDAOImplHibernate<Usuario, Integer> implements UsuarioDAO {

    @Override
    public void updateEncryptedPassword(DataSession dataSession, Usuario usuario, String encriptedPassword) {

        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("UPDATE usuario SET password=? WHERE idIdentity=?");
        query.setString(0, encriptedPassword);
        query.setInteger(1, usuario.getIdIdentity());

        int result=query.executeUpdate(); 
        
        if (result!=1) {
            throw new RuntimeException("Se debería haber actualizado una única fila pero se han actualizado:" + result + " idIdentity=" + usuario.getIdIdentity());
        }
    }

    @Override
    public void updateSuccessfulLogin(DataSession dataSession, Usuario usuario) {

        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("UPDATE usuario SET fechaUltimoAcceso=? ,fechaEnvioCorreoAvisoBorrarUsuario=NULL,lockedUntil=null,numFailedLogins=0 WHERE idIdentity=?");
        
        query.setTimestamp(0, new Date());
        query.setInteger(1, usuario.getIdIdentity());

        int result=query.executeUpdate(); 
        
        if (result!=1) {
            throw new RuntimeException("Se debería haber actualizado una única fila pero se han actualizado:" + result + " idIdentity= " + usuario.getIdIdentity());
        }
    } 
    
    @Override
    public void updateFailedLogin(DataSession dataSession, Usuario usuario, Date lockedUntil, int numFailedLogins) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("UPDATE usuario SET lockedUntil=?,numFailedLogins=? WHERE idIdentity=?");
        
        query.setTimestamp(0, lockedUntil);
        query.setInteger(1, numFailedLogins);
        query.setInteger(2, usuario.getIdIdentity());

        int result=query.executeUpdate(); 
        
        if (result!=1) {
            throw new RuntimeException("Se debería haber actualizado una única fila pero se han actualizado:" + result + " idIdentity= " + usuario.getIdIdentity());
        }
    }
    
    @Override
    public void updateFechaEnvioCorreoAvisoBorrarUsuario(DataSession dataSession, Usuario usuario) {

        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("UPDATE usuario SET fechaEnvioCorreoAvisoBorrarUsuario=? WHERE idIdentity=?");
        
        Date fecha=new Date();
        
        query.setTimestamp(0, fecha);
        query.setInteger(1, usuario.getIdIdentity());

        int result=query.executeUpdate(); 
        
        if (result!=1) {
            throw new RuntimeException("Se debería haber actualizado una única fila pero se han actualizado:" + result + " idIdentity= " + usuario.getIdIdentity());
        }
    }      
    
    
    @Override
    public String getEncryptedPassword(DataSession dataSession, Usuario usuario) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("SELECT password FROM usuario WHERE idIdentity=?");
        query.setInteger(0, usuario.getIdIdentity());
        String encryptedPassword = (String) query.uniqueResult();

        return encryptedPassword;
    }

    @Override
    public Usuario getUsuarioPorEmail(DataSession dataSession, String email) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();
        String hql = "SELECT usuario FROM Usuario usuario WHERE usuario.email = :email";
        Query query = session.createQuery(hql);
        query.setString("email", email);
        Usuario usuario = (Usuario) query.uniqueResult();
        return usuario;
    }
    
    @Override
    public void softDelete(DataSession dataSession, int idIdentity) {
        Session session = (Session) dataSession.getDataBaseSessionImpl();

        Query query = session.createSQLQuery("UPDATE Usuario SET borrado=1 WHERE idIdentity=?");
        query.setInteger(0, idIdentity);

        int result=query.executeUpdate(); 
        
        if (result!=1) {
            throw new RuntimeException("Se debería haber actualizado una única fila pero se han actualizado:" + result + " idIdentity= " + idIdentity);
        }
        
    }
    
}
