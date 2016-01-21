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
package es.logongas.fpempresa.service.comun.usuario.impl;

import es.logongas.fpempresa.dao.comun.usuario.UsuarioDAO;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.security.SecureKeyGenerator;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.GenericDAO;
import es.logongas.ix3.security.model.Group;
import es.logongas.ix3.security.model.GroupMember;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class UsuarioCRUDServiceImpl extends CRUDServiceImpl<Usuario, Integer> implements UsuarioCRUDService {

    @Autowired
    MailService mailService;

    private UsuarioDAO getUsuarioDAO() {
        return (UsuarioDAO) getDAO();
    }

    @Override
    public void updatePassword(DataSession dataSession, Usuario usuario, String newPassword) throws BusinessException {
        getUsuarioDAO().updateEncryptedPassword(dataSession,usuario, getEncryptedPasswordFromPlainPassword(newPassword));
    }

    @Override
    public boolean checkPassword(DataSession dataSession, Usuario usuario, String password) throws BusinessException {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = getUsuarioDAO().getEncryptedPassword(dataSession, usuario);

        boolean checkOK = passwordEncryptor.checkPassword(password, encryptedPassword);

        return checkOK;
    }

    @Override
    public Usuario insert(DataSession dataSession, Usuario usuario) throws BusinessException {

        InputStream inputStream = UsuarioCRUDServiceImpl.class.getResourceAsStream("fotoDefecto.png");

        try {
            byte[] foto=IOUtils.toByteArray(inputStream);
            usuario.setFoto(foto);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        usuario.setPassword(getEncryptedPasswordFromPlainPassword(usuario.getPassword()));
        usuario.setValidadoEmail(false);
        usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
        sendMailValidacionEMail(usuario);
        usuario.setMemberOf(getMembersOf(dataSession, usuario));
        Usuario resultUsuario=getUsuarioDAO().insert(dataSession, usuario);
        
        
        //La ponemos siemrpe a null una vez insertada para que nunca se pueda ver desde "fuera"
        resultUsuario.setPassword(null);
        
        return resultUsuario;
    }

    @Override
    public Usuario update(DataSession dataSession, Usuario usuario) throws BusinessException {
        Usuario usuarioOriginal = getUsuarioDAO().readOriginal(dataSession, usuario.getIdIdentity());

        
        //REGLA NEGOCIO:Si cambiamos el EMail hay que volver a verificar la nueva dirección
        if (!usuarioOriginal.getEmail()
                .equals(usuario.getEmail())) {
            usuario.setValidadoEmail(false);
            usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
            sendMailValidacionEMail(usuario);
        }

        return super.update(dataSession, usuario);
    }

    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }

    
    @Override
    public Usuario getUsuarioFromTitulado(DataSession dataSession, int idTitulado) throws BusinessException  {
        List<Filter> filters=new ArrayList<>();
        filters.add(new Filter("titulado.idTitulado", idTitulado));
        List<Usuario> usuarios=this.getDAO().search(dataSession,filters,null,null);

        if (usuarios.size()==1) {
            return usuarios.get(0);
        } else if (usuarios.size()==0) {
            return null;
        } else {
            throw new RuntimeException("La consulta retornó mas de un elemento:" + usuarios.size());
        }

    };

    private void sendMailValidacionEMail(Usuario usuario) {
        //Enviar el Mail de Verificación

    }

    private Set<GroupMember> getMembersOf(DataSession dataSession, Usuario usuario) {
        try {
            Set<GroupMember> groupMembers = new HashSet<>();
            GenericDAO<Group, Integer> groupDAO = daoFactory.getDAO(Group.class);

            String GROUP_USUARIOS_NAME = "GUsuarios";
            Group usuarios = groupDAO.readByNaturalKey(dataSession, GROUP_USUARIOS_NAME);
            if (usuarios == null) {
                throw new RuntimeException("No existe el grupo " + GROUP_USUARIOS_NAME);
            }
            groupMembers.add(new GroupMember(0, usuarios, usuario, 0));

            switch (usuario.getTipoUsuario()) {
                case ADMINISTRADOR:
                    String GROUP_ADMINISTRADORES_NAME = "GAdministradores";
                    Group administradores = groupDAO.readByNaturalKey(dataSession, GROUP_ADMINISTRADORES_NAME);
                    if (administradores == null) {
                        throw new RuntimeException("No existe el grupo " + GROUP_ADMINISTRADORES_NAME);
                    }
                    groupMembers.add(new GroupMember(0, administradores, usuario, 0));
                    break;
                case CENTRO:
                    String GROUP_CENTROS_NAME = "GCentros";
                    Group centros = groupDAO.readByNaturalKey(dataSession, GROUP_CENTROS_NAME);
                    if (centros == null) {
                        throw new RuntimeException("No existe el grupo " + GROUP_CENTROS_NAME);
                    }
                    groupMembers.add(new GroupMember(0, centros, usuario, 0));
                    break;
                case EMPRESA:
                    String GROUP_EMPRESAS_NAME = "GEmpresas";
                    Group empresas = groupDAO.readByNaturalKey(dataSession, GROUP_EMPRESAS_NAME);
                    if (empresas == null) {
                        throw new RuntimeException("No existe el grupo " + GROUP_EMPRESAS_NAME);
                    }
                    groupMembers.add(new GroupMember(0, empresas, usuario, 0));
                    break;
                case TITULADO:
                     String GROUP_TITULADOS_NAME = "GTitulados";
                    Group titulados = groupDAO.readByNaturalKey(dataSession, GROUP_TITULADOS_NAME);
                    if (titulados == null) {
                        throw new RuntimeException("No existe el grupo " + GROUP_TITULADOS_NAME);
                    }
                    groupMembers.add(new GroupMember(0, titulados, usuario, 0));
                    break;
                default:
                    throw new RuntimeException();
            }

            return groupMembers;
        } catch (BusinessException ex) {
            throw new RuntimeException(ex);
        }
    }

}
