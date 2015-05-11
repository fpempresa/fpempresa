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
package es.logongas.fpempresa.modelo.empresa;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.ix3.service.rules.ActionRule;
import es.logongas.ix3.service.rules.ConstraintRule;
import es.logongas.ix3.service.rules.RuleContext;
import es.logongas.ix3.service.rules.RuleGroupPredefined;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lorenzo
 */
public class Empresa {

    private int idEmpresa;

    @NotBlank
    private String nombreComercial;

    @NotBlank
    private String razonSocial;

    @NotBlank
    private String cif;

    @NotNull
    @Valid
    private Direccion direccion;

    
    private Centro centro;

    @ConstraintRule(message = "Solo un usuario registrado puede modificar la empresa", priority = -20, groups = RuleGroupPredefined.PostInsertOrUpdateOrDelete.class)
    private boolean usuarioRegistrado(RuleContext<Empresa> ruleContext) {
        Usuario usuario = (Usuario) ruleContext.getPrincipal();

        if (usuario==null) {
            return false;
        }  
        
        return true;
    }
    
  
    @ConstraintRule(message = "Tu tipo de usuario no permite modificar la empresa", groups=RuleGroupPredefined.PostInsertOrUpdateOrDelete.class)
    private boolean soloLoPuedeModificarUnCentroAdministradorOTrabajador(RuleContext<Empresa> ruleContext) {
        Usuario usuario = (Usuario) ruleContext.getPrincipal();
        
        if ((usuario.getTipoUsuario() != TipoUsuario.ADMINISTRADOR) && (usuario.getTipoUsuario() != TipoUsuario.EMPRESA) && (usuario.getTipoUsuario() != TipoUsuario.CENTRO)) {
            return false;
        }

        return true;
    }

    @ConstraintRule(message = "Un trabajador no puede modificar la empresa si no pertences a ella",groups=RuleGroupPredefined.PostInsertOrUpdateOrDelete.class)
    private boolean soloUsuarioDeEmpresaPuedeModificarla(RuleContext<Empresa> ruleContext) {
        Usuario usuario = (Usuario) ruleContext.getPrincipal();

        if (usuario.getTipoUsuario() == TipoUsuario.EMPRESA) {
            if (usuario.getEmpresa().getIdEmpresa() != this.getIdEmpresa()) {
                return false;
            }
        }

        return true;
    }

    @ConstraintRule(message = "Un profesor no puede modificar la empresa si no pertences a su centro",groups=RuleGroupPredefined.PostInsertOrUpdateOrDelete.class)
    private boolean soloProfesorCuyaEmpresaSeaDeSuCentroPuedeModificarla(RuleContext<Empresa> ruleContext) {
        Usuario usuario = (Usuario) ruleContext.getPrincipal();

        if (usuario.getTipoUsuario() == TipoUsuario.CENTRO) {

            if (this.getCentro() == null) {
                return false;
            }

            if (usuario.getCentro().getIdCentro() != this.getCentro().getIdCentro()) {
                return false;
            }
        }

        return true;
    }

    
 
   
    
    
    public Empresa() {
    }

    @Override
    public String toString() {
        return getNombreComercial();
    }

    /**
     * @return the idEmpresa
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the nombreComercial
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * @param nombreComercial the nombreComercial to set
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the cif
     */
    public String getCif() {
        return cif;
    }

    /**
     * @param cif the cif to set
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * @return the direccion
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the centro
     */
    public Centro getCentro() {
        return centro;
    }

    /**
     * @param centro the centro to set
     */
    public void setCentro(Centro centro) {
        this.centro = centro;
    }

}
