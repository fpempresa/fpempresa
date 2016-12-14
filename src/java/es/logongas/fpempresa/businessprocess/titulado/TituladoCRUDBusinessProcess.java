/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.titulado;

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

    void importarTituladosCSV(ImportarTituladosCSVArguments importarTituladosCSVArguments) throws BusinessException;

    public class ImportarTituladosCSVArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public MultipartFile multipartFile;

        public ImportarTituladosCSVArguments() {
        }

        public ImportarTituladosCSVArguments(Principal principal, DataSession dataSession, MultipartFile multipartFile) {
            super(principal, dataSession);
            this.multipartFile = multipartFile;
        }
    }
}
