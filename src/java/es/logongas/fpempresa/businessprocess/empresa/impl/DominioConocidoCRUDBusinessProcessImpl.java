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
package es.logongas.fpempresa.businessprocess.empresa.impl;

import es.logongas.fpempresa.modelo.empresa.DominioConocido;
import es.logongas.fpempresa.security.authorization.annotations.ACE;
import es.logongas.fpempresa.security.authorization.annotations.ACEType;
import es.logongas.fpempresa.security.authorization.annotations.PreAuthorization;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Page;
import java.util.Date;
import java.util.List;

/**
 *
 * @author logongas
 */
public class DominioConocidoCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<DominioConocido, Integer> implements CRUDBusinessProcess<DominioConocido, Integer> {



    @Override
    @PreAuthorization(acl={@ACE(aceType=ACEType.Allow, groupLogin = "GAdministradores")})
    public DominioConocido create(CreateArguments createArguments) throws BusinessException {
        return super.create(createArguments); 
    }

    @Override
    @PreAuthorization(acl={@ACE(aceType=ACEType.Allow, groupLogin = "GAdministradores")})    
    public DominioConocido read(ReadArguments<Integer> readArguments) throws BusinessException {
        return super.read(readArguments); 
    }

    @Override
    @PreAuthorization(acl={@ACE(aceType=ACEType.Allow, groupLogin = "GAdministradores")})    
    public List<DominioConocido> search(SearchArguments searchArguments) throws BusinessException {
        return super.search(searchArguments); 
    }

    @Override
    @PreAuthorization(acl={@ACE(aceType=ACEType.Allow, groupLogin = "GAdministradores")})      
    public Page<DominioConocido> pageableSearch(PageableSearchArguments pageableSearchArguments) throws BusinessException {
        return super.pageableSearch(pageableSearchArguments); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    @PreAuthorization(acl={@ACE(aceType=ACEType.Allow, groupLogin = "GAdministradores")})    
    public DominioConocido insert(InsertArguments<DominioConocido> insertArguments) throws BusinessException {
        return super.insert(insertArguments); 
    }
    
    @Override
    @PreAuthorization(acl={@ACE(aceType=ACEType.Allow, groupLogin = "GAdministradores")})    
    public DominioConocido update(UpdateArguments<DominioConocido> updateArguments) throws BusinessException {
        return super.update(updateArguments); 
    }

    @Override
    @PreAuthorization(acl={@ACE(aceType=ACEType.Allow, groupLogin = "GAdministradores")})    
    public boolean delete(DeleteArguments<DominioConocido> deleteArguments) throws BusinessException {
        return super.delete(deleteArguments); 
    }
    
    
    
    
}
