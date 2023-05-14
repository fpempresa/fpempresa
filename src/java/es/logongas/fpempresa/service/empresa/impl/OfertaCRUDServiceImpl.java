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
package es.logongas.fpempresa.service.empresa.impl;

import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.dao.empresa.OfertaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.security.publictoken.PublicTokenCerrarOferta;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.service.titulado.TituladoCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import es.logongas.ix3.web.security.jwt.Jws;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class OfertaCRUDServiceImpl extends CRUDServiceImpl<Oferta, Integer> implements OfertaCRUDService {

    private static final Logger logException = LogManager.getLogger(Exception.class);
    
    @Autowired
    protected CRUDServiceFactory serviceFactory;

    @Autowired
    Notification notification;
    
    @Autowired
    Executor executor;

    @Autowired
    private DataSessionFactory dataSessionFactory;

    private OfertaDAO getOfertaDAO() {
        return (OfertaDAO) getDAO();
    }

    @Autowired
    Jws jws;    

    @Override
    public Oferta insert(DataSession dataSession, Oferta entity) throws BusinessException {
        
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);
        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            CRUDService<Empresa, Integer> empresaCRUDService = (CRUDService<Empresa, Integer>) serviceFactory.getService(Empresa.class);
            Empresa empresa=empresaCRUDService.read(dataSession, entity.getEmpresa().getIdEmpresa());
            int numOfertasPublicadas=empresa.getNumOfertasPublicadas();
            
            int maxOfertasPublicadasEmpresa=Integer.parseInt(Config.getSetting("app.maxOfertasPublicadasEmpresa"));
            if (numOfertasPublicadas>=maxOfertasPublicadasEmpresa) {
                List<BusinessMessage> businessMessages=new ArrayList<BusinessMessage>();
                businessMessages.add(new BusinessMessage("No es posible publicar más ofertas. Ha alcanzado el límite máximo."));
                businessMessages.add(new BusinessMessage("Si desea publicar más ofertas, póngase en contacto con el soporte de EmpleaFP."));
                throw new BusinessException(businessMessages);
            }
            

            Oferta oferta=super.insert(dataSession, entity);

            empresa.setNumOfertasPublicadas(empresa.getNumOfertasPublicadas()+1);
            empresaCRUDService.update(dataSession, empresa);
            
            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            return oferta;
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }

    }
    
    
    
    
    @Override
    public boolean delete(DataSession dataSession, Oferta entity) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);
        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);

            Filters filters = new Filters();
            filters.add(new Filter("oferta.idOferta", entity.getIdOferta(), FilterOperator.eq));

            List<Candidato> candidatos = candidatoCRUDService.search(dataSession, filters, null, null);
            for (Candidato candidato : candidatos) {
                candidatoCRUDService.delete(dataSession, candidato);
            }

            boolean success = super.delete(dataSession, entity);

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            return success;
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }

    }

    @Override
    public List<Oferta> getOfertasUsuarioTitulado(DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) throws BusinessException {
        return getOfertaDAO().getOfertasUsuarioTitulado(dataSession, usuario, provincia, fechaInicio, fechaFin);
    }

    @Override
    public List<Oferta> getOfertasInscritoUsuarioTitulado(DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) throws BusinessException {
        return getOfertaDAO().getOfertasInscritoUsuarioTitulado(dataSession, usuario, provincia, fechaInicio, fechaFin);
    }

    @Override
    public List<Oferta> getOfertasEmpresasCentro(DataSession dataSession, Centro centro) throws BusinessException {
        return getOfertaDAO().getOfertasEmpresasCentro(dataSession, centro);
    }

    @Override
    public List<Oferta> getOfertasEmpresa(DataSession dataSession, Empresa empresa) throws BusinessException {
        return getOfertaDAO().getOfertasEmpresa(dataSession, empresa);
    }

    @Override
    public void notificarOfertaATitulados(DataSession dataSession, Oferta oferta) throws BusinessException {
        executor.execute(new NotificarOfertaATituladosImplRunnable(dataSessionFactory, oferta.getIdOferta()));
    }
    
    @Override
    public void cerrarOferta(DataSession dataSession,Oferta oferta, String publicToken) throws BusinessException {
        
        if (oferta==null) {
            throw new BusinessException("No existe la oferta");
        }
        byte[] secretToken=oferta.getSecretToken().getBytes(Charset.forName("utf-8"));
        
        
        PublicTokenCerrarOferta publicTokenCerrarOferta;
                
        try {
            publicTokenCerrarOferta=new PublicTokenCerrarOferta(publicToken, jws, secretToken);
        } catch (Exception ex) {
            throw new BusinessException("El token no tiene el formato adecuado");
        }
        
        if (publicTokenCerrarOferta.isValid()==false) {
            throw new BusinessException("El token no es válido o ha caducado.");
        }
        
        if (oferta.getIdOferta()!=publicTokenCerrarOferta.getIdOferta()) {
            throw new BusinessException("El token no es válido para esa oferta.");
        }
        
        
        
        oferta.setCerrada(true);
        
        log.info("Cerrada oferta desde el link del correo:"+ oferta.getIdOferta());
        
        super.update(dataSession, oferta);
        
    }    
    
    

    private class NotificarOfertaATituladosImplRunnable implements Runnable {

        private final DataSessionFactory dataSessionFactory;
        private final int idOferta;

        public NotificarOfertaATituladosImplRunnable(DataSessionFactory dataSessionFactory, int idOferta) {
            this.dataSessionFactory = dataSessionFactory;
            this.idOferta = idOferta;
        }

        @Override
        public void run() {
            try (DataSession dataSession = dataSessionFactory.getDataSession()) {
                TituladoCRUDService tituladoCRUDService = (TituladoCRUDService) serviceFactory.getService(Titulado.class);
                OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);
                UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

                Oferta oferta = ofertaCRUDService.read(dataSession, idOferta);
                List<Titulado> tituladosSuscritos = tituladoCRUDService.getTituladosSuscritosPorProvinciaOfertaYCiclosOferta(dataSession, oferta);
                for (Titulado titulado : tituladosSuscritos) {
                    Usuario usuario = usuarioCRUDService.getUsuarioFromTitulado(dataSession, titulado.getIdTitulado());
                    if (usuario != null) {
                        notification.nuevaOferta(usuario, oferta);
                    }
                }
            } catch (Exception ex) {
                logException.error(ex);
            }
        }

    }

}
