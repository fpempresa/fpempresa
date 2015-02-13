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

import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.service.GenericService;

/**
 *
 * @author Lorenzo Gonzalez
 */
public interface UsuarioService extends GenericService<Usuario,Integer> {
    void updatePassword(Usuario usuario,String plainPassword);
    boolean checkPassword(Usuario usuario,String plainPassword);
}
