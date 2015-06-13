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
package es.logongas.fpempresa.service.populate;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.EstadoCentro;
import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.fpempresa.modelo.comun.geo.Municipio;
import es.logongas.fpempresa.modelo.comun.geo.Provincia;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DAOFactory;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class PopulateService implements Service {
    
    @Autowired
    protected DAOFactory daoFactory;
    
    @Autowired
    private CRUDServiceFactory crudServiceFactory;
 
    
    
    public Centro createCentroAleatorio() throws BusinessException  {
        String[] tiposCentro={"IES","CIPFP"};
        
        CRUDService<Centro,Integer> centroService=crudServiceFactory.getService(Centro.class);
        
        
        Centro centro=centroService.create();
        
        centro.setContacto(createContactoAleatorio());
        centro.setEstadoCentro(EstadoCentro.PERTENECE_A_FPEMPRESA);
        centro.setNombre(GeneradorDatosAleatorios.getAleatorio(tiposCentro) + " " + GeneradorDatosAleatorios.getNombrePersona() + " " + GeneradorDatosAleatorios.getApellidoPersona());
        centro.setDireccion(createDireccionAleatoria());
        
        return centro;
    }
    
    public Empresa createEmpresaAleatoria() throws BusinessException  {
        String[] tiposEmpresa={"Sociedad Anonima","Sociedad Limitada","Cooperativa","Sociedad Laboral"};
        Centro[] centros={getCentroAleatorio(),null,null,null,null,null};
        
        CRUDService<Empresa,Integer> empresaService=crudServiceFactory.getService(Empresa.class);
        
        String nombreEmpresa=GeneradorDatosAleatorios.getNombreEmpresa() + " " + GeneradorDatosAleatorios.getApellidoPersona();
        Empresa empresa=empresaService.create();
        
       
        empresa.setNombreComercial(nombreEmpresa);
        empresa.setRazonSocial(nombreEmpresa + " " + GeneradorDatosAleatorios.getAleatorio(tiposEmpresa));
        empresa.setDireccion(createDireccionAleatoria());
        empresa.setContacto(createContactoAleatorio());
        empresa.setCentro((Centro)GeneradorDatosAleatorios.getAleatorio(centros));
        empresa.setCif(GeneradorDatosAleatorios.getCif());
        
        return empresa;
    }    

    public Usuario createUsuarioAleatorio() throws BusinessException  {
        TipoUsuario[] tiposUsuario={TipoUsuario.CENTRO,TipoUsuario.EMPRESA,TipoUsuario.EMPRESA,TipoUsuario.TITULADO,TipoUsuario.TITULADO,TipoUsuario.TITULADO};
        Random random = new Random(System.currentTimeMillis());
        CRUDService<Usuario,Integer> usuarioService=crudServiceFactory.getService(Usuario.class);
        String[] correos={"gmail.com","yahoo.com","hotmail.com"};
        
        Usuario usuario=usuarioService.create();
        
        String ape1=GeneradorDatosAleatorios.getApellidoPersona();
        String ape2=GeneradorDatosAleatorios.getApellidoPersona();
        String nombre=GeneradorDatosAleatorios.getNombrePersona();
        String email=ape1.substring(0,3)+ape2.substring(0,3)+nombre.substring(0,3)+random.nextInt(9999)+"@"+GeneradorDatosAleatorios.getAleatorio(correos);
        
        usuario.setApellidos(ape1+" "+ape2);
        usuario.setNombre(nombre);
        usuario.setEmail(email.toLowerCase());
        usuario.setPassword("a");
        usuario.setTipoUsuario((TipoUsuario)GeneradorDatosAleatorios.getAleatorio(tiposUsuario));
        usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        switch (usuario.getTipoUsuario()) {
            case EMPRESA:
                usuario.setEmpresa(getEmpresaAleatoria());
                break;
            case CENTRO:
                usuario.setCentro(getCentroAleatorio());
                break;
            case TITULADO:
                usuario.setTitulado(createTituladoAleatorio());
                break;
        }
        
        
        return usuario;
    }     
    
    private Titulado createTituladoAleatorio() throws BusinessException {
        Titulado titulado=new Titulado();
        String[] permisosConducir={"A","A","B","B","C1","C",null,null,null,null,null,null,null,null,null,null,null,null,null,null};
        
        titulado.setDireccion(createDireccionAleatoria());
        titulado.setFechaNacimiento(GeneradorDatosAleatorios.getFecha(18, 35));
        titulado.setTipoDocumento(TipoDocumento.NIF_NIE);
        titulado.setNumeroDocumento(GeneradorDatosAleatorios.getNif());
        titulado.setPermisosConducir(GeneradorDatosAleatorios.getAleatorio(permisosConducir));
        titulado.setTelefono(GeneradorDatosAleatorios.getTelefono());
        titulado.setTelefonoAlternativo(GeneradorDatosAleatorios.getTelefono());
        
        return titulado;
    }    
    
    
    private Contacto createContactoAleatorio() throws BusinessException {
        Contacto contacto=new Contacto();
        contacto.setPersona(GeneradorDatosAleatorios.getNombrePersona() + " " + GeneradorDatosAleatorios.getApellidoPersona() + " " + GeneradorDatosAleatorios.getApellidoPersona());
        contacto.setTelefono(GeneradorDatosAleatorios.getTelefono());
        
        return contacto;
    }    
    
    
    private Direccion createDireccionAleatoria() throws BusinessException {
        Direccion direccion=new Direccion();
        direccion.setDatosDireccion(GeneradorDatosAleatorios.getDireccion());
        direccion.setMunicipio(getMunicipioAleatorio());
        
        return direccion;
    }
    
    
    
    private Municipio getMunicipioAleatorio() throws BusinessException {
        CRUDService<Provincia,Integer> provinciaService=crudServiceFactory.getService(Provincia.class);
        CRUDService<Municipio,Integer> municipioService=crudServiceFactory.getService(Municipio.class);
        
        Provincia provincia=GeneradorDatosAleatorios.getAleatorio(provinciaService.search(null));
        
        List<Filter> filters=new ArrayList<Filter>();
        filters.add(new Filter("provincia.idProvincia", provincia.getIdProvincia()));
        
        Municipio municipio=GeneradorDatosAleatorios.getAleatorio(municipioService.search(filters));
        
        return municipio;
    }
    
    
    private Centro getCentroAleatorio() throws BusinessException {
        CRUDService<Centro,Integer> centroService=crudServiceFactory.getService(Centro.class);
        
        Centro centro=GeneradorDatosAleatorios.getAleatorio(centroService.search(null));

        return centro;
    }    
    
    private Empresa getEmpresaAleatoria() throws BusinessException {
        CRUDService<Empresa,Integer> empresaService=crudServiceFactory.getService(Empresa.class);
        List<Filter> filters=new ArrayList<Filter>();
        filters.add(new Filter("centro", true,FilterOperator.isnull));
        Empresa empresa=GeneradorDatosAleatorios.getAleatorio(empresaService.search(filters));

        return empresa;
    }     
}
