/**
 * FPempresa Copyright (C) 2020 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */



package es.logongas.fpempresa.businessprocess.empresa.impl;

import es.logongas.fpempresa.businessprocess.empresa.OfertaCRUDBusinessProcess;
import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.comun.usuario.TipoUsuarioEmpresa;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.BusinessMessage;
import es.logongas.ix3.core.Page;
import es.logongas.ix3.core.PageRequest;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.service.CRUDService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class OfertaCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Oferta, Integer> implements OfertaCRUDBusinessProcess {

    @Autowired
    Notification notification;    
    
    @Override
    public List<Oferta> getOfertasUsuarioTitulado(GetOfertasUsuarioTituladoArguments getOfertasUsuarioTituladoArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasUsuarioTitulado(getOfertasUsuarioTituladoArguments.dataSession, getOfertasUsuarioTituladoArguments.usuario, getOfertasUsuarioTituladoArguments.provincia, getOfertasUsuarioTituladoArguments.fechaInicio, getOfertasUsuarioTituladoArguments.fechaFin);
    }

    @Override
    public List<Oferta> getOfertasInscritoUsuarioTitulado(GetOfertasInscritoUsuarioTituladoArguments getOfertasInscritoUsuarioTituladoArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasInscritoUsuarioTitulado(getOfertasInscritoUsuarioTituladoArguments.dataSession, getOfertasInscritoUsuarioTituladoArguments.usuario, getOfertasInscritoUsuarioTituladoArguments.provincia, getOfertasInscritoUsuarioTituladoArguments.fechaInicio, getOfertasInscritoUsuarioTituladoArguments.fechaFin);
    }

    @Override
    public List<Oferta> getOfertasEmpresasCentro(GetOfertasEmpresasCentroArguments getOfertasEmpresasCentroArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasEmpresasCentro(getOfertasEmpresasCentroArguments.dataSession, getOfertasEmpresasCentroArguments.centro);
    }

    @Override
    public List<Oferta> getOfertasEmpresa(GetOfertasEmpresaArguments getOfertasEmpresaArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        return ofertaCRUDService.getOfertasEmpresa(getOfertasEmpresaArguments.dataSession, getOfertasEmpresaArguments.empresa);
    }

    @Override
    public Oferta insert(InsertArguments<Oferta> insertArguments) throws BusinessException {

        fireConstraintRule_InsertAlcanzadoMaxOfertasPublicadasEmpresa(insertArguments.dataSession, (Usuario)insertArguments.principal, insertArguments.entity);
        fireConstraintRule_NoRepetidaOferta(insertArguments.dataSession, (Usuario)insertArguments.principal, insertArguments.entity,null);        
        
        Oferta oferta = super.insert(insertArguments);
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);
        ofertaCRUDService.notificarOfertaATitulados(insertArguments.dataSession, oferta);
        return oferta;
    }

    @Override
    public Oferta update(UpdateArguments<Oferta> updateArguments) throws BusinessException {
        fireConstraintRule_NoRepetidaOferta(updateArguments.dataSession, (Usuario)updateArguments.principal, updateArguments.entity, updateArguments.originalEntity);        
        
        return super.update(updateArguments);
    }
    
    @Override
    public void notificacionOferta(NotificacionOfertaArguments notificacionOfertaArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);
        CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) serviceFactory.getService(Candidato.class);
        
        Oferta oferta=notificacionOfertaArguments.oferta;
        DataSession dataSession=notificacionOfertaArguments.dataSession;
        
        ofertaCRUDService.notificarOfertaATitulados(dataSession, oferta);
        
        PageRequest pageRequest=new PageRequest(0, 10000);

        Page<Candidato> page=candidatoCRUDService.getCandidatosOferta(dataSession, oferta, true, false, Integer.MAX_VALUE, pageRequest);
        
        for(Candidato candidato:page.getContent()) {
            candidatoCRUDService.notificarAEmpresaInscritoCandidato(dataSession, candidato);
        }
        
    }

    @Override
    public void cerrarOferta(CerrarOfertaArguments cerrarOfertaArguments) throws BusinessException {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);

        Oferta oferta=cerrarOfertaArguments.oferta;
        DataSession dataSession=cerrarOfertaArguments.dataSession;
        String publicToken=cerrarOfertaArguments.publicToken;
        
        ofertaCRUDService.cerrarOferta(dataSession, oferta, publicToken);
        
    }

   
    /************************************************************************/
    /*************************** Constraint Rules ***************************/
    /************************************************************************/ 
    
    
    private void fireConstraintRule_InsertAlcanzadoMaxOfertasPublicadasEmpresa(DataSession dataSession, Usuario principal, Oferta oferta) throws BusinessException {
        
        CRUDService<Empresa, Integer> empresaCRUDService = (CRUDService<Empresa, Integer>) serviceFactory.getService(Empresa.class);
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        Empresa empresa=empresaCRUDService.read(dataSession, oferta.getEmpresa().getIdEmpresa());

        if (empresa.getCentro()!=null) {
            //la regla no se aplica para empresas de centros
            return;
        }
        
        
        if (principal.getTipoUsuario()==TipoUsuario.EMPRESA) {
            if (usuarioCRUDService.getTipoUsuarioEmpresa(dataSession, principal)==TipoUsuarioEmpresa.CONFIABLE) {
                //la regla no se aplica para usuarios de empresa confiables
                return;
            }
        }
        
        int numOfertasPublicadas=empresa.getNumOfertasPublicadas();
        int maxOfertasPublicadasEmpresa=Integer.parseInt(Config.getSetting("app.maxOfertasPublicadasEmpresa"));
        if (numOfertasPublicadas>=maxOfertasPublicadasEmpresa) {
            List<BusinessMessage> businessMessages=new ArrayList<BusinessMessage>();
            businessMessages.add(new BusinessMessage("No es posible publicar más ofertas. Ha alcanzado el límite máximo."));
            businessMessages.add(new BusinessMessage("No debe borrar las oferta que ya tiene publicadas ya que eso no hará que pueda publicar más ofertas."));
            businessMessages.add(new BusinessMessage("Si desea publicar más ofertas, póngase en contacto con el soporte de EmpleaFP."));
            
            BusinessException businessException=new BusinessException(businessMessages);
            notification.exceptionToAdministrador("Alcanzado limite ofertas."+oferta.getEmpresa().getIdEmpresa()+" ("+oferta.getEmpresa().getNombreComercial()+")", "Empresa="+oferta.getEmpresa().getIdEmpresa() + " ("+oferta.getEmpresa().getNombreComercial()+") numOfertasPublicadas="+numOfertasPublicadas+ " maxOfertasPublicadasEmpresa="+maxOfertasPublicadasEmpresa, businessException);
            throw businessException;
        }

    }
    
    private void fireConstraintRule_NoRepetidaOferta(DataSession dataSession, Usuario principal, Oferta oferta,Oferta ofertaOriginal) throws BusinessException {
        
        CRUDService<Empresa, Integer> empresaCRUDService = (CRUDService<Empresa, Integer>) serviceFactory.getService(Empresa.class);
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        CRUDService<Oferta, Integer> ofertaCRUDService = (CRUDService<Oferta, Integer>) serviceFactory.getService(Oferta.class);
        Empresa empresa=empresaCRUDService.read(dataSession, oferta.getEmpresa().getIdEmpresa());
        
        if (empresa.getCentro()!=null) {
            //la regla no se aplica para empresas de centros
            return;
        }
        
        if (principal.getTipoUsuario()==TipoUsuario.EMPRESA) {
            if (usuarioCRUDService.getTipoUsuarioEmpresa(dataSession, principal)==TipoUsuarioEmpresa.CONFIABLE) {
                //la regla no se aplica para usuarios de empresa confiables
                return;
            }
        }
        
        if (oferta.getMunicipio()==null) {
            //Si no hay municipio aun no se puede validar esta regla
            return;
        }            
        if (oferta.getFamilia()==null) {
            //Si no hay familia no se puede validar la oferta
            return;
        }
        
        
        //Ciclos originales de la oferta antes de la modificación
        Set<Ciclo> ciclosOriginalesOferta=new HashSet<>();
        if (ofertaOriginal!=null) {
            ciclosOriginalesOferta=ofertaOriginal.getCiclos();
        }
            
        int diasPermitidosRepetirOferta = Integer.parseInt(Config.getSetting("app.diasPermitidosRepetirOferta"));
        Date dayUntil=DateUtil.add(new Date(), DateUtil.Interval.DAY, -diasPermitidosRepetirOferta);

        Filters filters = new Filters();
        filters.add(new Filter("empresa.idEmpresa",oferta.getEmpresa().getIdEmpresa() ));
        filters.add(new Filter("fecha",dayUntil ,FilterOperator.dge));
        filters.add(new Filter("idOferta",oferta.getIdOferta() ,FilterOperator.ne));

        List<Oferta> ofertasAnteriores = ofertaCRUDService.search(dataSession, filters, null, null);

        
        List<BusinessMessage> businessMessages=new ArrayList<>();
        for (Oferta ofertaAnterior:ofertasAnteriores) {
            if ((oferta.getFamilia().getIdFamilia()==ofertaAnterior.getFamilia().getIdFamilia()) && (oferta.getMunicipio().getProvincia().getIdProvincia()==ofertaAnterior.getMunicipio().getProvincia().getIdProvincia())) {
                Set<Ciclo> ciclos=oferta.getCiclos();
                Set<Ciclo> ciclosAnteriores=ofertaAnterior.getCiclos();

                if (existsAnyCicloEnComun(ciclos, ciclosAnteriores)) {
                    
                    Set<Ciclo> ciclosRepetidos=getCiclosEnComun(ciclos, ciclosAnteriores);
                    for (Ciclo cicloRepetido:ciclosRepetidos) {
                        
                        //Si el ciclo ya existía originalmente en la oferta, si que se permite que esté.
                        //Esto ocurre al modificar una oferta y en los últimos "n" días ya hay alguna otra oferta con ese ciclo.
                        if (existsCiclo(ciclosOriginalesOferta,cicloRepetido.getIdCiclo())==false) {
                            businessMessages.add(new BusinessMessage("Ciclo","No es posible publicar esta oferta puesto que has publicado ya una oferta con el ciclo de '" + cicloRepetido.getDescripcion() +  "' en la provincia de '" + oferta.getMunicipio().getProvincia().getDescripcion() + "' en los últimos "+ diasPermitidosRepetirOferta + " días."));
                            log.info("Oferta no publicada al estar repetida. idOferta anterior="+ofertaAnterior.getIdOferta()+ " Empresa="+oferta.getEmpresa().getIdEmpresa() + " ciclo=" + cicloRepetido.getDescripcion());
                        }
                    }
                    
                }
            }
        }
        
        if (businessMessages.size()>0) {
            BusinessException businessException=new BusinessException(businessMessages);

            throw businessException;            
        }
        

    } 
    
    /******************************************************************/
    /*************************** Utilidades ***************************/
    /******************************************************************/  
    
    
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
