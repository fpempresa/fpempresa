/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.titulado.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.fpempresa.dao.titulado.TituladoDAO;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import es.logongas.ix3.web.json.JsonFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.jasypt.util.password.StrongPasswordEncryptor;
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
    public void importarTituladosCSV(DataSession dataSession,Usuario[] listaUsuarios) throws BusinessException {
        TituladoDAO tituladosDAO = this.getTituladoDAO();
        for (Usuario t: listaUsuarios) {
            t.setPassword(this.getEncryptedPasswordFromPlainPassword(t.getPassword()));
            tituladosDAO.insert(dataSession, t.getTitulado());
        }
    }
   private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }
    

}
