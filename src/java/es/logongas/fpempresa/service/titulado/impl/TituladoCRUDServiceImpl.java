/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.titulado.impl;

import com.opencsv.CSVReader;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.fpempresa.dao.titulado.TituladoDAO;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GnommoStudios
 */
public class TituladoCRUDServiceImpl extends CRUDServiceImpl<Titulado, Integer> implements TituladoCRUDService {

    private TituladoDAO getTituladoDAO() {
        return (TituladoDAO) getDAO();
    }

    @Override
    public List<Titulado> getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(DataSession dataSession, Oferta oferta) {
        return getTituladoDAO().getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(dataSession, oferta);
    }

    @Override
    public void importarTituladosCSV(DataSession dataSession, MultipartFile multipartFile) throws BusinessException {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader csvReader = new CSVReader(bufferedReader);
        } catch (IOException ex) {
            throw new RuntimeException("Error al leer el archivo csv", ex);
        }

    }

}
