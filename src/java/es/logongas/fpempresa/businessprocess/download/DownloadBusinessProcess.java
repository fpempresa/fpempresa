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



package es.logongas.fpempresa.businessprocess.download;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.educacion.Familia;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.Date;

/**
 *
 * @author logongas
 */
public interface DownloadBusinessProcess extends BusinessProcess  {
    
    byte[] getHojaCalculoOfertasNoCentro(GetHojaCalculoOfertasNoCentroArguments getHojaCalculoOfertasNoCentroArguments) throws BusinessException;

    byte[] getHojaCalculoOfertasCentro(GetHojaCalculoOfertasCentroArguments getHojaCalculoOfertasCentroArguments) throws BusinessException;

    byte[] getHojaCalculoEmpresasNoCentro(GetHojaCalculoEmpresasNoCentroArguments getHojaCalculoEmpresasNoCentroArguments) throws BusinessException;

    byte[] getHojaCalculoEmpresasCentro(GetHojaCalculoEmpresasCentroArguments getHojaCalculoEmpresasCentroArguments) throws BusinessException;

    public byte[] getHojaCalculoUsuariosTituladosCentro(GetHojaCalculoUsuariosTituladosCentroArguments hojaCalculoTituladosCentroArguments) throws BusinessException;

    
    public class GetHojaCalculoOfertasNoCentroArguments extends BusinessProcess.BusinessProcessArguments {

        public Date fechaInicio;
        public Date fechaFin;

        public GetHojaCalculoOfertasNoCentroArguments() {
        }

        public GetHojaCalculoOfertasNoCentroArguments(Principal principal, DataSession dataSession, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }

    public class GetHojaCalculoOfertasCentroArguments extends BusinessProcess.BusinessProcessArguments {

	public Centro centro;
        public Date fechaInicio;
        public Date fechaFin;

        public GetHojaCalculoOfertasCentroArguments() {
        }

        public GetHojaCalculoOfertasCentroArguments(Principal principal, DataSession dataSession,Centro centro, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.centro = centro;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }


    public class GetHojaCalculoEmpresasNoCentroArguments extends BusinessProcess.BusinessProcessArguments {

        public Date fechaInicio;
        public Date fechaFin;

        public GetHojaCalculoEmpresasNoCentroArguments() {
        }

        public GetHojaCalculoEmpresasNoCentroArguments(Principal principal, DataSession dataSession, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }

    public class GetHojaCalculoEmpresasCentroArguments extends BusinessProcess.BusinessProcessArguments {

	public Centro centro;
        public Date fechaInicio;
        public Date fechaFin;

        public GetHojaCalculoEmpresasCentroArguments() {
        }

        public GetHojaCalculoEmpresasCentroArguments(Principal principal, DataSession dataSession,Centro centro, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.centro = centro;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }

    public static class GetHojaCalculoUsuariosTituladosCentroArguments extends BusinessProcess.BusinessProcessArguments {
        
	public Centro centro;
	public Familia familia;
	public Ciclo ciclo;
        public Date fechaInicio;
        public Date fechaFin;

        public GetHojaCalculoUsuariosTituladosCentroArguments(Principal principal, DataSession dataSession, Centro centro, Familia familia, Ciclo ciclo, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.centro = centro;
            this.familia = familia;
            this.ciclo = ciclo;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }


        

    }




}
