/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
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
package es.logongas.fpempresa.service.download;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import java.util.Date;

/**
 *
 * @author logongas
 */
public interface DownloadService {
    byte [] getHojaCalculoOfertasNoCentro(DataSession dataSession,Date fechaInicio,Date fechaFin) throws BusinessException;
    
    byte[] getHojaCalculoOfertasCentro(DataSession dataSession, Centro centro, Date fechaInicio, Date fechaFin) throws BusinessException;
    
    byte [] getHojaCalculoEmpresasNoCentro(DataSession dataSession,Date fechaInicio,Date fechaFin) throws BusinessException;
    
    byte[] getHojaCalculoEmpresasCentro(DataSession dataSession, Centro centro, Date fechaInicio, Date fechaFin) throws BusinessException;

    byte[] getHojaCalculoUsuariosTituladosCentro(DataSession dataSession, Centro centro, Familia familia, Ciclo ciclo, Date fechaInicio, Date fechaFin) throws BusinessException;
        
}
