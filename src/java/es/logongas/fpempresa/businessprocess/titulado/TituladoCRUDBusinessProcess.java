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



package es.logongas.fpempresa.businessprocess.titulado;

import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GnommoStudios
 */
public interface TituladoCRUDBusinessProcess extends CRUDBusinessProcess<Titulado, Integer> {

    void importarTitulados(ImportarTituladosArguments importarTituladosCSVArguments) throws BusinessException;
    
    int getNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta(GetNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta getNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta) throws BusinessException;
    
    
    

    public class ImportarTituladosArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public MultipartFile multipartFile;

        public ImportarTituladosArguments() {
        }

        public ImportarTituladosArguments(Principal principal, DataSession dataSession, MultipartFile multipartFile) {
            super(principal, dataSession);
            this.multipartFile = multipartFile;
        }
    }
    
    public class GetNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Oferta oferta;

        public GetNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta() {
        }

        public GetNumTituladosSuscritosPorProvinciaOfertaYCiclosOferta(Principal principal, DataSession dataSession, Oferta oferta) {
            super(principal, dataSession);
            this.oferta = oferta;
        }

    }
    
}
