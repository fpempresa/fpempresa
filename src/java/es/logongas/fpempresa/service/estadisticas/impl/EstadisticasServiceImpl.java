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
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.estadisticas.Estadisticas;
import es.logongas.fpempresa.modelo.estadisticas.FamiliaOfertasEstadistica;
import es.logongas.fpempresa.modelo.estadisticas.OfertaEstadistica;
import es.logongas.fpempresa.service.estadisticas.EstadisticasService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Order;
import es.logongas.ix3.core.OrderDirection;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.dao.SearchResponse;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class EstadisticasServiceImpl implements EstadisticasService {

    @Autowired
    EstadisticaDAO estadisticaDAO;
    @Autowired
    private CRUDServiceFactory crudServiceFactory;
    
    @Override
    public Estadisticas getEstadisticasAdministrador(DataSession dataSession) {
        Estadisticas estadisticasAdministrador = new Estadisticas(estadisticaDAO.getTitulosFPGroupByFamilia(dataSession), estadisticaDAO.getOfertasGroupByFamilia(dataSession), estadisticaDAO.getCandidatosGroupByFamilia(dataSession));

        return estadisticasAdministrador;
    }

    @Override
    public Estadisticas getEstadisticasCentro(DataSession dataSession, Centro centro,Integer anyoInicio,Integer anyoFin) {
        Estadisticas estadisticas = new Estadisticas(estadisticaDAO.getTitulosFPGroupByFamilia(dataSession, centro, anyoInicio, anyoFin), estadisticaDAO.getOfertasGroupByFamilia(dataSession, centro, anyoInicio, anyoFin), estadisticaDAO.getCandidatosGroupByFamilia(dataSession, centro, anyoInicio, anyoFin));
        return estadisticas;
    }

    @Override
    public Estadisticas getEstadisticasEmpresa(DataSession dataSession, Empresa empresa) {
        Estadisticas estadisticas = new Estadisticas(estadisticaDAO.getTitulosFPGroupByFamilia(dataSession), estadisticaDAO.getOfertasGroupByFamilia(dataSession, empresa), estadisticaDAO.getCandidatosGroupByFamilia(dataSession, empresa));

        return estadisticas;
    }

    @Override
    public Estadisticas getEstadisticasPublicas(DataSession dataSession) {
        Estadisticas estadisticasPublicas = new Estadisticas(estadisticaDAO.getTitulosFPGroupByFamilia(dataSession), estadisticaDAO.getOfertasGroupByFamilia(dataSession), estadisticaDAO.getCandidatosGroupByFamilia(dataSession), estadisticaDAO.getSumCentros(dataSession), estadisticaDAO.getSumEmpresas(dataSession));
        return estadisticasPublicas;
    }
    
    @Override
    public List<FamiliaOfertasEstadistica> getEstadisticasFamiliaOfertasPublicas(DataSession dataSession) throws BusinessException {
        List<FamiliaOfertasEstadistica> familiasOfertasEstadistica=new ArrayList<>();
        int antiguedadDias=120;
        int numOfertas=1000; //Numero de ofertas que vamos a leer de la base de datos
        int maxOfertasFamilia=5;
        
        Map<Integer,FamiliaOfertasEstadistica> mapFamiliasOfertasEstadistica=new HashMap<>();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -antiguedadDias);
        Date fechaInicio = cal.getTime();        
        
        
        //Map con las familias
        CRUDService<Familia, Integer> familiaCrudService = crudServiceFactory.getService(Familia.class);
        List<Familia> familias=familiaCrudService.search(dataSession, null, null, null);
        for(Familia familia:familias) {
            mapFamiliasOfertasEstadistica.put(familia.getIdFamilia(),new FamiliaOfertasEstadistica(familia.getIdFamilia(),familia.getDescripcion()));
        }
        
        //Obtener las ofertas
        CRUDService<Oferta, Integer> ofertaCrudService = crudServiceFactory.getService(Oferta.class);
        Filters filters=new Filters();
        filters.add(new Filter("fecha",fechaInicio , FilterOperator.dge));
        filters.add(new Filter("empresa.centro",true , FilterOperator.isnull));
        PageRequest pageRequest=new PageRequest(0, numOfertas);
        SearchResponse searchResponse=new SearchResponse(false);
        List<Order> orders=new ArrayList<>();
        orders.add(new Order("fecha", OrderDirection.Descending));
        Page<Oferta> pageOfertas=ofertaCrudService.pageableSearch(dataSession, filters, orders, pageRequest, searchResponse);
        //Añadir cada oferta a la familia pero de provincias distintas
        for(Oferta oferta:pageOfertas.getContent()) {
            FamiliaOfertasEstadistica familiaOfertaEstadistica=mapFamiliasOfertasEstadistica.get(oferta.getFamilia().getIdFamilia());
            Provincia provincia=oferta.getMunicipio().getProvincia();
            
            if (familiaOfertaEstadistica.getOfertasEstadistica().size()<maxOfertasFamilia) {
                if (existsOfertaEstadisticaProvincia(familiaOfertaEstadistica, provincia)==false) {
                    familiaOfertaEstadistica.getOfertasEstadistica().add(new OfertaEstadistica(oferta));
                }
            }
        }

        //Añadir ahora con provincias repetidas
        for(Oferta oferta:pageOfertas.getContent()) {
            FamiliaOfertasEstadistica familiaOfertaEstadistica=mapFamiliasOfertasEstadistica.get(oferta.getFamilia().getIdFamilia());
            Provincia provincia=oferta.getMunicipio().getProvincia();
            
            if (familiaOfertaEstadistica.getOfertasEstadistica().size()<maxOfertasFamilia) {
                if (existsOfertaEstadistica(familiaOfertaEstadistica, oferta)==false) {
                    familiaOfertaEstadistica.getOfertasEstadistica().add(new OfertaEstadistica(oferta));
                }
            }
        }
        
        //Solo mostramos las familias que tienen alguna oferta
        for(FamiliaOfertasEstadistica familiaOfertasEstadistica:mapFamiliasOfertasEstadistica.values()) {
            if (familiaOfertasEstadistica.getOfertasEstadistica().size()>0) {
                familiasOfertasEstadistica.add(familiaOfertasEstadistica);
            }
        }
        
        Collections.shuffle(familiasOfertasEstadistica);
        
        return familiasOfertasEstadistica;
    }
        
    

    @Override
    public void setEntityType(Class<Estadisticas> entityType) {
        throw new RuntimeException("No se permite cambiar el tipo de la entidad. Debe ser siempre Estadisticas");
    }

    @Override
    public Class<Estadisticas> getEntityType() {
        return Estadisticas.class;
    }

    
    public boolean existsOfertaEstadisticaProvincia(FamiliaOfertasEstadistica familiaOfertaEstadistica,Provincia provincia) {
        
        for(OfertaEstadistica ofertaEstadistica:familiaOfertaEstadistica.getOfertasEstadistica()) {
            if (ofertaEstadistica.getProvincia().getIdProvincia()==provincia.getIdProvincia()){
                return true;
            }
        }
        
        return false;
        
    }
    
    public boolean existsOfertaEstadistica(FamiliaOfertasEstadistica familiaOfertaEstadistica,Oferta oferta) {
        
        for(OfertaEstadistica ofertaEstadistica:familiaOfertaEstadistica.getOfertasEstadistica()) {
            if (ofertaEstadistica.getIdOferta()==oferta.getIdOferta()){
                return true;
            }
        }
        
        return false;
        
    }    
    
}
