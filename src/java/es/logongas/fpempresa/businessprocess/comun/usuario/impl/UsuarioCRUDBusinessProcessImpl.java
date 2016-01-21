/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.comun.usuario.impl;

import es.logongas.fpempresa.businessprocess.comun.usuario.UsuarioCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;

/**
 *
 * @author logongas
 */
public class UsuarioCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Usuario,Integer> implements UsuarioCRUDBusinessProcess {

    @Override
    public void updatePassword(UpdatePasswordArguments updatePasswordArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService= (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        Usuario principal = (Usuario) updatePasswordArguments.principal;
        
        if (updatePasswordArguments.usuario == null) {
            throw new BusinessException("No hay usuario al que cambiar la contraseña");
        } else if (principal == null) {
            throw new BusinessException("Debes haber entrado para cambiar la contraseña");
        } else if (updatePasswordArguments.usuario.getIdIdentity() == principal.getIdIdentity()) {
            if (usuarioCRUDService.checkPassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.currentPassword)) {
                usuarioCRUDService.updatePassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.newPassword);
            } else {
                throw new BusinessException("La contraseña actual no es válida");
            }
        } else if (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            //Si eres administrador no necesitas la contraseña actual
            usuarioCRUDService.updatePassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.newPassword);
        } else {
            throw new BusinessException("Solo un Administrador o el propio usuario puede cambiar la contraseña");
        }        

    }

    @Override
    public Usuario getUsuarioFromTitulado(GetUsuarioFromTituladoArguments getUsuarioFromTituladoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService= (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        
        return usuarioCRUDService.getUsuarioFromTitulado(getUsuarioFromTituladoArguments.dataSession, getUsuarioFromTituladoArguments.titulado.getIdTitulado());
    }

    @Override
    public byte[] getFoto(GetFotoArguments getFotoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService= (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        
        return usuarioCRUDService.read(getFotoArguments.dataSession, getFotoArguments.usuario.getIdIdentity()).getFoto();
    }

    @Override
    public void updateFoto(UpdateFotoArguments updateFotoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService= (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        
        updateFotoArguments.usuario.setFoto(updateFotoArguments.foto);
        usuarioCRUDService.update(updateFotoArguments.dataSession, updateFotoArguments.usuario);
    }

    @Override
    public Usuario updateEstadoUsuario(UpdateEstadoUsuarioArguments updateEstadoUsuarioArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService= (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        
        updateEstadoUsuarioArguments.usuario.setEstadoUsuario(updateEstadoUsuarioArguments.estadoUsuario);
        return usuarioCRUDService.update(updateEstadoUsuarioArguments.dataSession, updateEstadoUsuarioArguments.usuario); 
    }

    @Override
    public Usuario updateCentro(UpdateCentroArguments updateCentroArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService= (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        
        updateCentroArguments.usuario.setCentro(updateCentroArguments.centro);
        return usuarioCRUDService.update(updateCentroArguments.dataSession, updateCentroArguments.usuario); 
    }
    
}
