/**
 *   FPempresa
 *   Copyright (C) 2015  Lorenzo González
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
package es.logongas.fpempresa.service.comun.usuario;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDService;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lorenzo Gonzalez
 */
public interface UsuarioCRUDService extends CRUDService<Usuario, Integer> {

    void updatePassword(DataSession dataSession, Usuario usuario, String newPassword) throws BusinessException;
    
    void updateSuccessfulLogin(DataSession dataSession, Usuario usuario) throws BusinessException;
    
    boolean checkPassword(DataSession dataSession, Usuario usuario, String password) throws BusinessException;

    Usuario getUsuarioFromTitulado(DataSession dataSession, int idTitulado) throws BusinessException;

    void enviarMailResetearContrasenya(DataSession dataSession, Usuario usuario) throws BusinessException;

    void validarEmail(DataSession dataSession,Usuario usuario, String claveValidacionEmail) throws BusinessException;

    void resetearContrasenya(DataSession dataSession,Usuario usuario,  String claveResetearContrasenya, String nuevaContrasenya) throws BusinessException;
    
    byte[] getCurriculum(DataSession dataSession, Usuario usuario) throws BusinessException; 
    
    List<Usuario> getUsuariosFromEmpresa(DataSession dataSession, int idEmpresa) throws BusinessException;

    void notificarUsuarioInactivo(DataSession dataSession, Usuario usuario) throws BusinessException;

    void softDelete(DataSession dataSession, Usuario usuario) throws BusinessException;

    public boolean isLocked(DataSession dataSession, Usuario usuario);
    public Date getLockedUntil(DataSession dataSession, Usuario usuario);
    public void updateFailedLogin(DataSession dataSession, Usuario usuario);
    void cancelarSuscripcion(DataSession dataSession,Usuario usuario, String publicToken) throws BusinessException;
    
    int numUsuariosCentro(DataSession dataSession,Centro centro) throws BusinessException;
    
    void enviarMailValidarEMail(DataSession dataSession, Usuario usuario) throws BusinessException;
    
}
