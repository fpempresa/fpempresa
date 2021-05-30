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
package es.logongas.fpempresa.service.centro;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoAnyo;
import es.logongas.fpempresa.modelo.centro.CertificadoCiclo;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import java.util.List;

/**
 *
 * @author logongas
 */
public interface CertificadoService {
    List<CertificadoAnyo> getCertificadosAnyoCentro(DataSession dataSession,Centro centro) throws BusinessException;
    List<CertificadoCiclo> getCertificadosCicloCentro(DataSession dataSession,Centro centro,int anyo) throws BusinessException;
    List<CertificadoTitulo> getCertificadosTituloCentro(DataSession dataSession,Centro centro,int anyo,Ciclo ciclo) throws BusinessException;
    void certificarTituloCentro(DataSession dataSession, Centro centro, int anyo, Ciclo ciclo, TipoDocumento tipoDocumento, String numeroDocumento,boolean certificadoTitulo) throws BusinessException ;
}
