/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.titulado;

import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.dao.DataSession;
import java.util.List;

/**
 *
 * @author GnommoStudios
 */
public interface TituladoCRUDService extends CRUDService<Titulado, Integer> {

    public List<Titulado> getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(DataSession dataSession, Oferta oferta);

}
