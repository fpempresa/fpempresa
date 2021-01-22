/**
 * FPempresa Copyright (C) 2019 Lorenzo González
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
package es.logongas.fpempresa.service.notification;

import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.Service;

/**
 * Servicio de notificación a usuarios. Lo normal es que se le envie un eMail
 * @author logongas
 */
public interface Notification extends Service  {
    
    void nuevaOferta(Usuario usuario,Oferta oferta);
    void nuevoCandidato(DataSession dataSession,Candidato candidato);
    void resetearContrasenya(Usuario usuario);    
    void validarCuenta(Usuario usuario);
    void mensajeSoporte( String nombre, String correo,String mensaje);
    void usuarioInactivo(Usuario usuario);
    void exception(String subject, String msg, Throwable throwable);
}
