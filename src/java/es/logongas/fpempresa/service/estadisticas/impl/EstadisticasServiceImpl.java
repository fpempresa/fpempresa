/**
 * FPempresa Copyright (C) 2015 Lorenzo González
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
package es.logongas.fpempresa.service.estadisticas.impl;

import es.logongas.fpempresa.dao.estadisticas.EstadisticaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.service.estadisticas.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class EstadisticasServiceImpl implements EstadisticasService {

    @Autowired
    EstadisticaDAO estadisticaDAO;
    
    @Override
    public Estadisticas getEstadisticas() {
        Estadisticas estadisticasAdministrador=new Estadisticas(estadisticaDAO.getTituladosGroupByFamilia(),estadisticaDAO.getOfertasGroupByFamilia(),estadisticaDAO.getCandidatosGroupByFamilia());
        
        return estadisticasAdministrador;
    }

    @Override
    public Estadisticas getEstadisticas(Centro centro) {
        Estadisticas estadisticas=new Estadisticas(estadisticaDAO.getTituladosGroupByFamilia(centro),estadisticaDAO.getOfertasGroupByFamilia(centro),estadisticaDAO.getCandidatosGroupByFamilia(centro));

        
        return estadisticas; 
    }

    @Override
    public Estadisticas getEstadisticas(Empresa empresa) {
        Estadisticas estadisticas=new Estadisticas(null,estadisticaDAO.getOfertasGroupByFamilia(empresa),estadisticaDAO.getCandidatosGroupByFamilia(empresa));
        
        return estadisticas;   
    }

}
