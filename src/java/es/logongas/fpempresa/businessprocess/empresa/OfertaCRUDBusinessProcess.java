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



package es.logongas.fpempresa.businessprocess.empresa;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import java.util.Date;
import java.util.List;

/**
 *
 * @author logongas
 */
public interface OfertaCRUDBusinessProcess extends CRUDBusinessProcess<Oferta, Integer> {

    public List<Oferta> getOfertasUsuarioTitulado(GetOfertasUsuarioTituladoArguments getOfertasUsuarioTitulado) throws BusinessException;

    public List<Oferta> getOfertasInscritoUsuarioTitulado(GetOfertasInscritoUsuarioTituladoArguments getOfertasInscritoUsuarioTitulado) throws BusinessException;

    public List<Oferta> getOfertasEmpresasCentro(GetOfertasEmpresasCentroArguments getOfertasEmpresasCentro) throws BusinessException;

    public List<Oferta> getOfertasEmpresa(GetOfertasEmpresaArguments getOfertasEmpresa) throws BusinessException;
    
    public void notificacionOferta(NotificacionOfertaArguments notificacionOfertaArguments) throws BusinessException;
    public void cerrarOferta(CerrarOfertaArguments cerrarOfertaArguments) throws BusinessException;

    public class GetOfertasUsuarioTituladoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Usuario usuario;
        public Provincia provincia;
        public Date fechaInicio;
        public Date fechaFin;

        public GetOfertasUsuarioTituladoArguments() {
        }

        public GetOfertasUsuarioTituladoArguments(Principal principal, DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.usuario = usuario;
            this.provincia = provincia;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }

    public class GetOfertasInscritoUsuarioTituladoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Usuario usuario;
        public Provincia provincia;
        public Date fechaInicio;
        public Date fechaFin;

        public GetOfertasInscritoUsuarioTituladoArguments() {
        }

        public GetOfertasInscritoUsuarioTituladoArguments(Principal principal, DataSession dataSession, Usuario usuario, Provincia provincia, Date fechaInicio, Date fechaFin) {
            super(principal, dataSession);
            this.usuario = usuario;
            this.provincia = provincia;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

    }

    public class GetOfertasEmpresasCentroArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Centro centro;

        public GetOfertasEmpresasCentroArguments() {
        }

        public GetOfertasEmpresasCentroArguments(Principal principal, DataSession dataSession, Centro centro) {
            super(principal, dataSession);
            this.centro = centro;
        }

    }

    public class GetOfertasEmpresaArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Empresa empresa;

        public GetOfertasEmpresaArguments() {
        }

        public GetOfertasEmpresaArguments(Principal principal, DataSession dataSession, Empresa empresa) {
            super(principal, dataSession);
            this.empresa = empresa;
        }

    }
    
    public class NotificacionOfertaArguments extends BusinessProcess.BusinessProcessArguments {

        public Oferta oferta;

        public NotificacionOfertaArguments() {
        }

        public NotificacionOfertaArguments(Principal principal, DataSession dataSession, Oferta oferta) {
            super(principal, dataSession);
            this.oferta = oferta;
        }

    }  
    
    public class CerrarOfertaArguments extends BusinessProcess.BusinessProcessArguments {

        final public Oferta oferta;
        final public String publicToken;

        public CerrarOfertaArguments(Principal principal, DataSession dataSession, Oferta oferta, String publicToken) {
            super(principal, dataSession);
            this.oferta = oferta;
            this.publicToken=publicToken;
        }

    }     
    
}
