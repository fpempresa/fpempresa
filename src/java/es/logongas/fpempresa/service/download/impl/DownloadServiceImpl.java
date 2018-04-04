/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.download.impl;

import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.download.DownloadService;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.fpempresa.service.hojacalculo.HojaCalculoService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Order;
import es.logongas.ix3.core.OrderDirection;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.dao.SearchResponse;
import es.logongas.ix3.service.CRUDServiceFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private CRUDServiceFactory crudServiceFactory;

    @Autowired
    HojaCalculoService hojaCalculoService;

    @Override
    public byte[] getHojaCalculoOfertas(DataSession dataSession, Date fechaInicio, Date fechaFin) throws BusinessException {

        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) crudServiceFactory.getService(Oferta.class);

        Filters filters = new Filters();
        List<Order> orders = new ArrayList<Order>();
        SearchResponse searchResponse = new SearchResponse(false);

        filters.add(new Filter("empresa.centro", null, FilterOperator.isnull));
        if (fechaInicio != null) {
            filters.add(new Filter("fecha", fechaInicio, FilterOperator.ge));

        }
        if (fechaFin != null) {
            filters.add(new Filter("fecha", fechaFin, FilterOperator.le));

        }

        orders.add(new Order("fecha", OrderDirection.Ascending));

        List<Oferta> ofertas = ofertaCRUDService.search(dataSession, filters, orders, searchResponse);

        return hojaCalculoService.getHojaCalculo(ofertas, "puesto,empresa.nombreComercial,fecha", "Puesto,Empresa,Fecha");
    }

    
    
}
