/*
 * FPempresa Copyright (C) 2021 Lorenzo González
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
package es.logongas.fpempresa.businessprocess.centro;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoAnyo;
import es.logongas.fpempresa.modelo.centro.CertificadoCiclo;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.List;

/**
 *
 * @author logongas
 */
public interface CertificadoBusinessProcess  extends BusinessProcess {

    List<CertificadoAnyo> getCertificadosAnyoCentro(GetCertificadosAnyoCentroArguments getCertificadosAnyoCentroArguments) throws BusinessException;
    List<CertificadoCiclo> getCertificadosCicloCentro(GetCertificadosCicloCentroArguments getCertificadosCicloCentroArguments) throws BusinessException;
    List<CertificadoTitulo> getCertificadosTituloCentro(GetCertificadosTituloCentroArguments getCertificadosTituloCentroArguments) throws BusinessException;
    void certificarTituloCentro(CertificarTituloCentroArguments certificarTituloCentroArguments) throws BusinessException;
    


    public class GetCertificadosAnyoCentroArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Centro centro;

        public GetCertificadosAnyoCentroArguments() {
        }

        public GetCertificadosAnyoCentroArguments(Principal principal, DataSession dataSession, Centro centro) {
            super(principal, dataSession);
            this.centro = centro;          
        }
       

    }

    public class GetCertificadosCicloCentroArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Centro centro;
        public int anyo;

        public GetCertificadosCicloCentroArguments() {
        }

        public GetCertificadosCicloCentroArguments(Principal principal, DataSession dataSession, Centro centro,int anyo) {
            super(principal, dataSession);
            this.centro = centro;          
            this.anyo = anyo;          
        }
       

    }

    public class GetCertificadosTituloCentroArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Centro centro;
        public int anyo;
        public Ciclo ciclo;

        public GetCertificadosTituloCentroArguments() {
        }

        public GetCertificadosTituloCentroArguments(Principal principal, DataSession dataSession, Centro centro,int anyo,Ciclo ciclo) {
            super(principal, dataSession);
            this.centro = centro;          
            this.anyo = anyo;          
            this.ciclo = ciclo;          
        }
       

    }
    public class CertificarTituloCentroArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Centro centro;
        public int anyo;
        public Ciclo ciclo;
        public TipoDocumento tipoDocumento;
        public String numeroDocumento;
        public boolean certificadoTitulo;

        public CertificarTituloCentroArguments() {
        }

        public CertificarTituloCentroArguments(Principal principal, DataSession dataSession, Centro centro,int anyo,Ciclo ciclo,TipoDocumento tipoDocumento,String numeroDocumento,boolean certificadoTitulo) {
            super(principal, dataSession);
            this.centro = centro;          
            this.anyo = anyo;          
            this.ciclo = ciclo;          
            this.tipoDocumento = tipoDocumento;          
            this.numeroDocumento = numeroDocumento;          
            this.certificadoTitulo = certificadoTitulo;          
        }
       

    }    
}
