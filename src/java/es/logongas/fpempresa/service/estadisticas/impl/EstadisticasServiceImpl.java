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
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.service.estadisticas.EstadisticasService;
import es.logongas.ix3.dao.DataSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class EstadisticasServiceImpl implements EstadisticasService {

    @Autowired
    EstadisticaDAO estadisticaDAO;

    @Override
    public Estadisticas getEstadisticasAdministrador(DataSession dataSession) {
        Estadisticas estadisticasAdministrador = new Estadisticas(estadisticaDAO.getTituladosGroupByFamilia(dataSession), estadisticaDAO.getOfertasGroupByFamilia(dataSession), estadisticaDAO.getCandidatosGroupByFamilia(dataSession));

        return estadisticasAdministrador;
    }

    @Override
    public Estadisticas getEstadisticasCentro(DataSession dataSession, Centro centro) {
        Estadisticas estadisticas = new Estadisticas(estadisticaDAO.getTituladosGroupByFamilia(dataSession, centro), estadisticaDAO.getOfertasGroupByFamilia(dataSession, centro), estadisticaDAO.getCandidatosGroupByFamilia(dataSession, centro));
        return estadisticas;
    }

    @Override
    public Estadisticas getEstadisticasEmpresa(DataSession dataSession, Empresa empresa) {
        Estadisticas estadisticas = new Estadisticas(estadisticaDAO.getTituladosGroupByFamilia(dataSession), estadisticaDAO.getOfertasGroupByFamilia(dataSession, empresa), estadisticaDAO.getCandidatosGroupByFamilia(dataSession, empresa));

        return estadisticas;
    }

    @Override
    public Estadisticas getEstadisticasPublicas(DataSession dataSession) {
        Estadisticas estadisticasPublicas = new Estadisticas(estadisticaDAO.getTituladosGroupByFamilia(dataSession), estadisticaDAO.getOfertasGroupByFamilia(dataSession), estadisticaDAO.getCandidatosGroupByFamilia(dataSession), estadisticaDAO.getSumCentros(dataSession), estadisticaDAO.getSumEmpresas(dataSession));
        return estadisticasPublicas;
    }

    @Override
    public void setEntityType(Class<Estadisticas> entityType) {
        throw new RuntimeException("No se permite cambiar el tipo de la entidad. Debe ser siempre Estadisticas");
    }

    @Override
    public Class<Estadisticas> getEntityType() {
        return Estadisticas.class;
    }

}
