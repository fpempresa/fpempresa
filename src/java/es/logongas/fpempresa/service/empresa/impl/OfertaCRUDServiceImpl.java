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
import es.logongas.fpempresa.modelo.educacion.Ciclo;
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
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.DataSessionFactory;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.Service;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import es.logongas.ix3.web.security.jwt.Jws;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private static final Logger log = LogManager.getLogger(Service.class);
    
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
    public Oferta insert(DataSession dataSession, Oferta oferta) throws BusinessException {
        
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);
        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            
            
            fireConstraintRule_InsertAlcanzadoMaxOfertasPublicadasEmpresa(dataSession, oferta);
            fireConstraintRule_NoRepetidaOferta(dataSession, oferta);
            fireConstraintRule_CicloRequerido(dataSession, oferta);
            
            oferta=super.insert(dataSession, oferta);
            
            fireActionRule_IncNumOfertasPublicadasTotalEmpresa(dataSession, oferta);

            
            
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
    public Oferta update(DataSession dataSession, Oferta oferta) throws BusinessException {
        
        fireConstraintRule_NoRepetidaOferta(dataSession, oferta);
        fireConstraintRule_CicloRequerido(dataSession, oferta);
        
        return super.update(dataSession, oferta); 
    }


    @Override
    public boolean delete(DataSession dataSession, Oferta oferta) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);
        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            fireActionRule_DeleteCandidatosOferta(dataSession, oferta);

            boolean success = super.delete(dataSession, oferta);

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
    

    
    
    /************************************************************************/
    /*************************** Constraint Rules ***************************/
    /************************************************************************/ 

    
    private void fireConstraintRule_InsertAlcanzadoMaxOfertasPublicadasEmpresa(DataSession dataSession, Oferta oferta) throws BusinessException {
        
        CRUDService<Empresa, Integer> empresaCRUDService = (CRUDService<Empresa, Integer>) serviceFactory.getService(Empresa.class);
        Empresa empresa=empresaCRUDService.read(dataSession, oferta.getEmpresa().getIdEmpresa());

        if (empresa.getCentro()!=null) {
            //la regla no se aplica para empresas de centros
            return;
        }
        
        int numOfertasPublicadas=empresa.getNumOfertasPublicadas();
        int maxOfertasPublicadasEmpresa=Integer.parseInt(Config.getSetting("app.maxOfertasPublicadasEmpresa"));
        if (numOfertasPublicadas>=maxOfertasPublicadasEmpresa) {
            List<BusinessMessage> businessMessages=new ArrayList<BusinessMessage>();
            businessMessages.add(new BusinessMessage("No es posible publicar más ofertas. Ha alcanzado el límite máximo."));
            businessMessages.add(new BusinessMessage("Si desea publicar más ofertas, póngase en contacto con el soporte de EmpleaFP."));
            
            BusinessException businessException=new BusinessException(businessMessages);
            notification.exception("Alcanzado limite ofertas."+oferta.getEmpresa().getIdEmpresa(), "Empresa="+oferta.getEmpresa().getIdEmpresa() + " numOfertasPublicadas="+numOfertasPublicadas+ " maxOfertasPublicadasEmpresa="+maxOfertasPublicadasEmpresa, businessException);
            throw businessException;
        }

    }
    
    private void fireConstraintRule_NoRepetidaOferta(DataSession dataSession, Oferta oferta) throws BusinessException {
        
        CRUDService<Empresa, Integer> empresaCRUDService = (CRUDService<Empresa, Integer>) serviceFactory.getService(Empresa.class);
        Empresa empresa=empresaCRUDService.read(dataSession, oferta.getEmpresa().getIdEmpresa());

        if (empresa.getCentro()!=null) {
            //la regla no se aplica para empresas de centros
            return;
        }
        if (oferta.getMunicipio()==null) {
            //Si no hay municipio aun no se puede validar esta regla
            return;
        }            
        if (oferta.getFamilia()==null) {
            //Si no hay familia no se puede validar la oferta
            return;
        }
            
        int diasPermitidosRepetirOferta = Integer.parseInt(Config.getSetting("app.diasPermitidosRepetirOferta"));
        Date dayUntil=DateUtil.add(new Date(), DateUtil.Interval.DAY, -diasPermitidosRepetirOferta);

        Filters filters = new Filters();
        filters.add(new Filter("empresa.idEmpresa",oferta.getEmpresa().getIdEmpresa() ));
        filters.add(new Filter("fecha",dayUntil ,FilterOperator.dge));
        filters.add(new Filter("idOferta",oferta.getIdOferta() ,FilterOperator.ne));

        List<Oferta> ofertasAnteriores = this.search(dataSession, filters, null, null);

        
        List<BusinessMessage> businessMessages=new ArrayList<>();
        for (Oferta ofertaAnterior:ofertasAnteriores) {
            if ((oferta.getFamilia().getIdFamilia()==ofertaAnterior.getFamilia().getIdFamilia()) && (oferta.getMunicipio().getProvincia().getIdProvincia()==ofertaAnterior.getMunicipio().getProvincia().getIdProvincia())) {
                Set<Ciclo> ciclos=oferta.getCiclos();
                Set<Ciclo> ciclosAnteriores=ofertaAnterior.getCiclos();

                if (existsAnyCicloEnComun(ciclos, ciclosAnteriores)) {
                    
                    Set<Ciclo> ciclosRepetidos=getCiclosEnComun(ciclos, ciclosAnteriores);
                    for (Ciclo cicloRepetido:ciclosRepetidos) {
                        businessMessages.add(new BusinessMessage("Ciclo","No es posible publicar esta oferta puesto que has publicado ya una oferta con el ciclo de '" + cicloRepetido.getDescripcion() +  "' en la provincia de '" + oferta.getMunicipio().getProvincia().getDescripcion() + "' en los últimos "+ diasPermitidosRepetirOferta + " días."));
                        log.info("Oferta no publicada al estar repetida. idOferta anterior="+ofertaAnterior.getIdOferta()+ " Empresa="+oferta.getEmpresa().getIdEmpresa() + " ciclo=" + cicloRepetido.getDescripcion());
                    }
                    
                }
            }
        }
        
        if (businessMessages.size()>0) {
            BusinessException businessException=new BusinessException(businessMessages);

            throw businessException;            
        }
        

    } 
    private void fireConstraintRule_CicloRequerido(DataSession dataSession, Oferta oferta) throws BusinessException {
        
        if ((oferta.getCiclos() == null) || (oferta.getCiclos().isEmpty())) {
            throw new BusinessException("Es necesario indicar al menos un ciclo formativo.");
        }
        
    }
    /********************************************************************/
    /*************************** Action Rules ***************************/
    /********************************************************************/ 
    
    
    private void fireActionRule_IncNumOfertasPublicadasTotalEmpresa(DataSession dataSession, Oferta oferta) throws BusinessException {
        CRUDService<Empresa, Integer> empresaCRUDService = (CRUDService<Empresa, Integer>) serviceFactory.getService(Empresa.class);
        Empresa empresa=empresaCRUDService.read(dataSession, oferta.getEmpresa().getIdEmpresa());
        empresa.setNumOfertasPublicadas(empresa.getNumOfertasPublicadas()+1);
        empresaCRUDService.update(dataSession, empresa);  
    }
     
    
    private void fireActionRule_DeleteCandidatosOferta(DataSession dataSession, Oferta oferta) throws BusinessException {
        CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);

        Filters filters = new Filters();
        filters.add(new Filter("oferta.idOferta", oferta.getIdOferta(), FilterOperator.eq));

        List<Candidato> candidatos = candidatoCRUDService.search(dataSession, filters, null, null);
        for (Candidato candidato : candidatos) {
            candidatoCRUDService.delete(dataSession, candidato);
        }
    }
    
    
    
    
    /******************************************************************/
    /*************************** Utilidades ***************************/
    /******************************************************************/    
    
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
    
    
    private boolean existsAnyCicloEnComun(Set<Ciclo> ciclosA,Set<Ciclo> ciclosB) {
        for(Ciclo cicloA:ciclosA) {
            if (existsCiclo(ciclosB,cicloA.getIdCiclo())) {
                return true;
            }
        }
        
        return false;
    }
    
    private Set<Ciclo> getCiclosEnComun(Set<Ciclo> ciclosA,Set<Ciclo> ciclosB) {
        Set<Ciclo> ciclosComun=new HashSet<>();
        
        for(Ciclo cicloA:ciclosA) {
            if (existsCiclo(ciclosB,cicloA.getIdCiclo())) {
                ciclosComun.add(cicloA);
            }
        }
        
        return ciclosComun;
    }
    
    
    private boolean existsCiclo(Set<Ciclo> ciclos,int idCiclo) {
        for(Ciclo ciclo:ciclos) {
            if (ciclo.getIdCiclo()==idCiclo) {
                return true;
            }
        }
        
        return false;
    }  
    
    
}
