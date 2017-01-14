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

import es.logongas.fpempresa.config.Config;
import es.logongas.fpempresa.dao.comun.usuario.UsuarioDAO;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.security.SecureKeyGenerator;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.mail.Mail;
import es.logongas.fpempresa.service.mail.MailService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.dao.GenericDAO;
import es.logongas.ix3.security.model.Group;
import es.logongas.ix3.security.model.GroupMember;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
        getUsuarioDAO().updateEncryptedPassword(dataSession, usuario, getEncryptedPasswordFromPlainPassword(newPassword));
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
            byte[] foto = IOUtils.toByteArray(inputStream);
            usuario.setFoto(foto);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        usuario.setPassword(getEncryptedPasswordFromPlainPassword(usuario.getPassword()));
        usuario.setValidadoEmail(false);
        usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
        usuario.setMemberOf(getMembersOf(dataSession, usuario));
        Usuario resultUsuario = getUsuarioDAO().insert(dataSession, usuario);

        if (resultUsuario != null) {
            enviarMailValidacionCuenta(usuario);
        }

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
            enviarMailValidacionCuenta(usuario);
        }

        return super.update(dataSession, usuario);
    }

    private String getEncryptedPasswordFromPlainPassword(String plainPassword) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        String encryptedPassword = passwordEncryptor.encryptPassword(plainPassword);

        return encryptedPassword;
    }

    @Override
    public Usuario getUsuarioFromTitulado(DataSession dataSession, int idTitulado) throws BusinessException {
        Filters filters = new Filters();
        filters.add(new Filter("titulado.idTitulado", idTitulado));
        List<Usuario> usuarios = this.getDAO().search(dataSession, filters, null, null);

        if (usuarios.size() == 1) {
            return usuarios.get(0);
        } else if (usuarios.size() == 0) {
            return null;
        } else {
            throw new RuntimeException("La consulta retornó mas de un elemento:" + usuarios.size());
        }

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

    @Override
    public boolean validarEmail(DataSession dataSession, String claveValidacionEmail) throws BusinessException {
        Usuario usuario = getUsuarioDAO().getUsuarioPorClaveValidacionEmail(dataSession, claveValidacionEmail);
        if (usuario != null) {
            usuario.setValidadoEmail(true);
            getUsuarioDAO().update(dataSession, usuario);
            return true;
        }
        return false;
    }

    @Override
    public void resetearContrasenya(DataSession dataSession, String claveResetearContrasenya, String nuevaContrasenya) throws BusinessException {
        Usuario usuario = getUsuarioDAO().getUsuarioPorClaveResetearContrasenya(dataSession, claveResetearContrasenya);
        if (usuario != null) {
            if (!usuario.isValidadoEmail()) {
                throw new BusinessException("La cuenta no está activada");
            }
            Date now = new Date();
            long diasClaveResetearPaswordEsValida = Integer.parseInt(Config.getSetting("app.diasClaveResetearPasswordEsValida"));
            if (usuario.getFechaClaveResetearContrasenya().getTime() + diasClaveResetearPaswordEsValida * 1000 * 60 * 60 * 24 > now.getTime()) {
                this.updatePassword(dataSession, usuario, nuevaContrasenya);
                usuario.setFechaClaveResetearContrasenya(null);
                usuario.setClaveResetearContrasenya(null);
                getUsuarioDAO().update(dataSession, usuario);
            } else {
                throw new BusinessException("El token ha caducado");
            }
        } else {
            throw new BusinessException("El token proporcionado es invalido");
        }
    }

    private void enviarMailValidacionCuenta(Usuario usuario) {
        try {
            Mail mail = new Mail();
            mail.addTo(usuario.getEmail());
            mail.setFrom(Config.getSetting("mail.sender"));
            mail.setSubject("Confirma tu correo para acceder a empleaFP");
            mail.setHtmlBody(""
                    + "Bienvenido <strong>" + usuario.getNombre() + " " + usuario.getApellidos() + "</strong>,<br>"
                    + "Acabas de registrarte en <a href=\"http://www.empleafp.com\">empleaFP</a>, la mayor bolsa de trabajo específica de la Formación Profesional.<br> "
                    + "Para poder completar tu registro es necesario que verifiques tu dirección de correo haciendo click en el siguiente enlace: "
                    + "<a href=\"" + (String) Config.getSetting("app.url") + "/fpempresa/site/index.html#/validar-email/" + usuario.getClaveValidacionEmail() + "\">Verificar Email</a>");
            mailService.send(mail);
        } catch (IOException ex) {
            throw new RuntimeException("Error al enviar email de validacion", ex);
        }
    }

    @Override
    public void enviarMailResetearPassword(DataSession dataSession, String email) throws BusinessException {
        try {
            Usuario usuario = getUsuarioDAO().getUsuarioPorEmail(dataSession, email);
            if (usuario != null) {
                usuario.setFechaClaveResetearContrasenya(new Date());
                usuario.setClaveResetearContrasenya(SecureKeyGenerator.getSecureKey());
                getUsuarioDAO().update(dataSession, usuario);
                Mail mail = new Mail();
                mail.addTo(usuario.getEmail());
                mail.setFrom(Config.getSetting("mail.sender"));
                mail.setSubject("Resetear contraseña en empleaFP");
                mail.setHtmlBody(""
                        + "Usted ha solicitado cambiar de contraseña en empleaFP.<br> \n\n"
                        + "Para proceder al cambio de contraseña de su cuenta haga click en el siguiente enlace e introduzca su nueva contraseña: \n"
                        + "<a href=\"" + Config.getSetting("app.url") + "/fpempresa/site/index.html#/resetear-contrasenya/" + usuario.getClaveResetearContrasenya() + "\">Resetear contraseña</a>");
                mailService.send(mail);
            } else {
                throw new BusinessException("No existe el usuario");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error al enviar email de reseteo de password", ex);
        }
    }
}
