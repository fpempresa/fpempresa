/*
 * FPempresa Copyright (C) 2025 Lorenzo González
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

import es.logongas.fpempresa.modelo.empresa.DominioConocido;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.util.validators.EMailValidator;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.Date;
import org.apache.commons.validator.routines.DomainValidator;
import java.net.IDN;

/**
 *
 * @author logongas
 */
public class DominioConocidoCRUDServiceImpl  extends CRUDServiceImpl<DominioConocido, Integer> {

    @Override
    public DominioConocido insert(DataSession dataSession, DominioConocido dominioConocido) throws BusinessException {
        
        
        
        fireConstraintRule_NombreDelDominioValido(dominioConocido);
        fireActionRule_EstablecerFechaAlta(dominioConocido);
        
        return super.insert(dataSession, dominioConocido); 
    }

    @Override
    public DominioConocido update(DataSession dataSession, DominioConocido dominioConocido) throws BusinessException {
        fireConstraintRule_NombreDelDominioValido(dominioConocido);
        
        return super.update(dataSession, dominioConocido);
    }
    
    
    
    
    /*********************************************************/
    /******************* Reglas de negocio *******************/
    /*********************************************************/
    
    
    
    private void fireActionRule_EstablecerFechaAlta(DominioConocido dominioConocido) throws BusinessException  {
        dominioConocido.setFechaAlta(new Date());
    }
    
    private void fireConstraintRule_NombreDelDominioValido(DominioConocido dominioConocido) throws BusinessException {
        String domino=dominioConocido.getDominio();
        
        if (domino == null || domino.trim().isEmpty()) {
            throw new BusinessException("El nombre del domino no puede estar vacío");
        }

        //Transformamos el dominio en un correo
        String correo="aaaa@"+domino;
        
        EMailValidator eMailValidator=new EMailValidator(correo);
        
        if (eMailValidator.isValid()==false) {
            throw new BusinessException("El formato del domino no es válido");
        }
        
    }
    
    
}
