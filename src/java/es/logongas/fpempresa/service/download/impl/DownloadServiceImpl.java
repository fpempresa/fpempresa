/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.download.impl;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.fpempresa.modelo.empresa.Empresa;
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
import es.logongas.ix3.service.CRUDService;
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
    public byte[] getHojaCalculoOfertasNoCentro(DataSession dataSession, Date fechaInicio, Date fechaFin) throws BusinessException {

        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) crudServiceFactory.getService(Oferta.class);

        Filters filters = new Filters();
        List<Order> orders = new ArrayList<Order>();
        SearchResponse searchResponse = new SearchResponse(false);

        filters.add(new Filter("empresa.centro", true, FilterOperator.isnull));
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

    @Override
    public byte[] getHojaCalculoOfertasCentro(DataSession dataSession, Centro centro, Date fechaInicio, Date fechaFin) throws BusinessException {
        if (centro == null) {
            throw new NullPointerException("El argumento centro no puede ser null");
        }

        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) crudServiceFactory.getService(Oferta.class);

        Filters filters = new Filters();
        List<Order> orders = new ArrayList<Order>();
        SearchResponse searchResponse = new SearchResponse(false);

        filters.add(new Filter("empresa.centro.idCentro", centro.getIdCentro(), FilterOperator.eq));

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

    @Override
    public byte[] getHojaCalculoEmpresasNoCentro(DataSession dataSession, Date fechaInicio, Date fechaFin) throws BusinessException {

        CRUDService<Empresa,Integer> empresaCRUDService = (CRUDService<Empresa,Integer>) crudServiceFactory.getService(Empresa.class);

        Filters filters = new Filters();
        List<Order> orders = new ArrayList<Order>();
        SearchResponse searchResponse = new SearchResponse(false);

        filters.add(new Filter("centro", true, FilterOperator.isnull));
        if (fechaInicio != null) {
            filters.add(new Filter("fecha", fechaInicio, FilterOperator.ge));

        }
        if (fechaFin != null) {
            filters.add(new Filter("fecha", fechaFin, FilterOperator.le));

        }

        orders.add(new Order("fecha", OrderDirection.Ascending));

        List<Empresa> empresas = empresaCRUDService.search(dataSession, filters, orders, searchResponse);

        return hojaCalculoService.getHojaCalculo(empresas, "nombreComercial,fecha", "Nombre,Fecha");
    }

    @Override
    public byte[] getHojaCalculoEmpresasCentro(DataSession dataSession, Centro centro, Date fechaInicio, Date fechaFin) throws BusinessException {
        if (centro == null) {
            throw new NullPointerException("El argumento centro no puede ser null");
        }
        
        CRUDService<Empresa,Integer> empresaCRUDService = (CRUDService<Empresa,Integer>) crudServiceFactory.getService(Empresa.class);

        Filters filters = new Filters();
        List<Order> orders = new ArrayList<Order>();
        SearchResponse searchResponse = new SearchResponse(false);

        filters.add(new Filter("centro.idCentro", centro.getIdCentro(), FilterOperator.eq));
        if (fechaInicio != null) {
            filters.add(new Filter("fecha", fechaInicio, FilterOperator.ge));

        }
        if (fechaFin != null) {
            filters.add(new Filter("fecha", fechaFin, FilterOperator.le));

        }

        orders.add(new Order("fecha", OrderDirection.Ascending));

        List<Empresa> empresas = empresaCRUDService.search(dataSession, filters, orders, searchResponse);

        return hojaCalculoService.getHojaCalculo(empresas, "nombreComercial,fecha", "Nombre,Fecha");
    }

    @Override
    public byte[] getHojaCalculoUsuariosTituladosCentro(DataSession dataSession, Centro centro, Familia familia, Ciclo ciclo, Date fechaInicio, Date fechaFin) throws BusinessException {
        if (centro == null) {
            throw new NullPointerException("El argumento centro no puede ser null");
        }
        
        CRUDService<Usuario,Integer> tituladoCRUDService = (CRUDService<Usuario,Integer>) crudServiceFactory.getService(Usuario.class);

        Filters filters = new Filters();
        List<Order> orders = new ArrayList<Order>();
        SearchResponse searchResponse = new SearchResponse(true);

        filters.add(new Filter("tipoUsuario", TipoUsuario.TITULADO, FilterOperator.eq));
        filters.add(new Filter("titulado.formacionesAcademicas.centro.idCentro", centro.getIdCentro(), FilterOperator.eq));
        if (familia != null) {
            filters.add(new Filter("titulado.formacionesAcademicas.ciclo.familia.idFamilia", familia.getIdFamilia(), FilterOperator.eq));

        }        
        if (ciclo != null) {
            filters.add(new Filter("titulado.formacionesAcademicas.ciclo.idCiclo", ciclo.getIdCiclo(), FilterOperator.eq));

        }        
        if (fechaInicio != null) {
            filters.add(new Filter("fecha", fechaInicio, FilterOperator.ge));

        }
        if (fechaFin != null) {
            filters.add(new Filter("fecha", fechaFin, FilterOperator.le));

        }
        
        

        orders.add(new Order( "fecha", OrderDirection.Ascending));

        List<Usuario> empresas = tituladoCRUDService.search(dataSession, filters, orders, searchResponse);

        return hojaCalculoService.getHojaCalculo(empresas, "nombre,apellidos,fecha", "Nombre,Apellidos,Fecha");
  
    }
    
}
