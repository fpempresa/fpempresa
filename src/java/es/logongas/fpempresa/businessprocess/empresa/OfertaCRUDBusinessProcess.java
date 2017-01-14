/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.empresa;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
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
}
