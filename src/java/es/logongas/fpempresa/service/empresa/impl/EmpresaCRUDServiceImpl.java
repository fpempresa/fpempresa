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

import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.empresa.OfertaCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author lgonzalez
 */
public class EmpresaCRUDServiceImpl extends CRUDServiceImpl<Empresa, Integer> implements CRUDService<Empresa, Integer> {

    @Autowired
    protected CRUDServiceFactory serviceFactory;

    @Override
    public Empresa insert(DataSession dataSession, Empresa empresa) throws BusinessException {
        
        fireActionRule_ContactoNoVacio(dataSession, empresa);
        
        
        Empresa newEmpresa=super.insert(dataSession, empresa);
        
        
        return newEmpresa;
    }

    @Override
    public Empresa update(DataSession dataSession, Empresa empresa) throws BusinessException {
        
        fireActionRule_ContactoNoVacio(dataSession, empresa);
        
        Empresa newEmpresa=super.update(dataSession, empresa);
        
        return newEmpresa;
    }



    
    
    
    @Override
    public boolean delete(DataSession dataSession, Empresa entity) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);

        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            
            
            fireActionRule_BorrarOfertasEmpresa(dataSession, entity);
            fireActionRule_BorrarUsuariosEmpresa(dataSession, entity);

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
    

    /********************************************************************/
    /*************************** Action Rules ***************************/
    /********************************************************************/ 
    
    /**
     * Esta regla es debida al siguiente problema:
     * https://forum.hibernate.org/viewtopic.php?p=2329518
     * https://docs.jboss.org/hibernate/core/3.3/reference/en/html/components.html#components-dependentobjects
     * "When reloading the containing object, Hibernate will assume that if all component columns are null, then the entire component is null. "
     * @param dataSession
     * @param empresa
     * @throws BusinessException 
     */
    private void fireActionRule_ContactoNoVacio(DataSession dataSession, Empresa empresa) throws BusinessException  {
        if (empresa.getContacto()==null) {
            empresa.setContacto(new Contacto());
        } 
        if (empresa.getContacto().getTextoLibre()==null) {
            empresa.getContacto().setTextoLibre("");
        }
    }    
    
    private void fireActionRule_BorrarOfertasEmpresa(DataSession dataSession, Empresa empresa) throws BusinessException  {
        OfertaCRUDService ofertaCRUDService = (OfertaCRUDService) serviceFactory.getService(Oferta.class);
        Filters filters = new Filters();
        filters.add(new Filter("empresa.idEmpresa", empresa.getIdEmpresa(), FilterOperator.eq));
        List<Oferta> ofertas = ofertaCRUDService.search(dataSession, filters, null, null);
        for (Oferta oferta : ofertas) {
            ofertaCRUDService.delete(dataSession, oferta);
        }
    }
    
    private void fireActionRule_BorrarUsuariosEmpresa(DataSession dataSession, Empresa empresa) throws BusinessException  {
        UsuarioCRUDService UsuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        Filters filters = new Filters();
        filters.add(new Filter("empresa.idEmpresa", empresa.getIdEmpresa(), FilterOperator.eq));
        List<Usuario> usuarios = UsuarioCRUDService.search(dataSession, filters, null, null);
        for (Usuario usuario : usuarios) {
            UsuarioCRUDService.delete(dataSession, usuario);
        }
    } 
    
}
