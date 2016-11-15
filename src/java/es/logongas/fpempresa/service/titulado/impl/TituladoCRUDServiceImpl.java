/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.titulado.impl;

import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.fpempresa.dao.titulado.TituladoDAO;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.List;

/**
 *
 * @author GnommoStudios
 */
public class TituladoCRUDServiceImpl extends CRUDServiceImpl<Titulado, Integer>  implements TituladoCRUDService {
    
    private TituladoDAO getTituladoDAO() {
        return (TituladoDAO) getDAO();
    }

    @Override
    public List<Titulado> getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(DataSession dataSession, Oferta oferta) {
        return getTituladoDAO().getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(dataSession, oferta);
    }

}
