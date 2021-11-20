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
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Candidato;
import es.logongas.fpempresa.modelo.titulado.ExperienciaLaboral;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.fpempresa.security.SecureKeyGenerator;
import es.logongas.fpempresa.security.publictoken.PublicTokenCancelarSubcripcion;
import es.logongas.fpempresa.util.validators.PasswordValidator;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.fpempresa.service.empresa.CandidatoCRUDService;
import es.logongas.fpempresa.service.notification.Notification;
import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.fpempresa.service.titulado.FormacionAcademicaCRUDService;
import es.logongas.fpempresa.util.DateUtil;
import es.logongas.fpempresa.util.RandomUtil;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.Filter;
import es.logongas.ix3.dao.FilterOperator;
import es.logongas.ix3.dao.Filters;
import es.logongas.ix3.dao.GenericDAO;
import es.logongas.ix3.security.model.Group;
import es.logongas.ix3.security.model.GroupMember;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import es.logongas.ix3.web.security.jwt.Jws;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author logongas
 */
public class UsuarioCRUDServiceImpl extends CRUDServiceImpl<Usuario, Integer> implements UsuarioCRUDService {

    @Autowired
    Notification notification;

    @Autowired
    Conversion conversion;

    @Autowired
    CRUDServiceFactory crudServiceFactory;

    @Autowired
    ReportService reportService;
    
    @Autowired
    Jws jws;  

    private UsuarioDAO getUsuarioDAO() {
        return (UsuarioDAO) getDAO();
    }

    @Override
    public void updatePassword(DataSession dataSession, Usuario usuario, String newPassword) throws BusinessException {
        
        PasswordValidator passwordValidator=new PasswordValidator(newPassword,"Contraseña");
        if (passwordValidator.isValid()==false) {
            throw new BusinessException(passwordValidator.getBusinessMessages());
        }        
        
        getUsuarioDAO().updateEncryptedPassword(dataSession, usuario, getEncryptedPasswordFromPlainPassword(newPassword));
    }

    @Override
    public void updateSuccessfulLogin(DataSession dataSession, Usuario usuario) throws BusinessException {
       getUsuarioDAO().updateSuccessfulLogin(dataSession, usuario);
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

        PasswordValidator passwordValidator=new PasswordValidator(usuario.getPassword(),"Contraseña");
        if (passwordValidator.isValid()==false) {
            throw new BusinessException(passwordValidator.getBusinessMessages());
        }
        
        
        Boolean autoValidateEMail = (Boolean) conversion.convertFromString(Config.getSetting("app.autoValidateEMail"), Boolean.class);
        if (autoValidateEMail == null) {
            autoValidateEMail = false;
        }

        usuario.setPassword(getEncryptedPasswordFromPlainPassword(usuario.getPassword()));
        if (autoValidateEMail == true) {
            usuario.setValidadoEmail(true);
        } else {
            usuario.setValidadoEmail(false);
        }
        usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
        usuario.setMemberOf(getMembersOf(dataSession, usuario));
        Usuario resultUsuario = getUsuarioDAO().insert(dataSession, usuario);

        if (resultUsuario != null) {
            if (autoValidateEMail == true) {
                //No es necesario enviar el EMail de confirmación ya que auto valida el email
            } else {
                notification.validarCuenta(usuario);
            }
        }

        //La ponemos siemrpe a null una vez insertada para que nunca se pueda ver desde "fuera"
        resultUsuario.setPassword(null);

        return resultUsuario;
    }

    @Override
    public Usuario update(DataSession dataSession, Usuario usuario) throws BusinessException {
        Usuario usuarioOriginal = getUsuarioDAO().readOriginal(dataSession, usuario.getIdIdentity());

        //REGLA NEGOCIO:Si cambiamos el EMail hay que volver a verificar la nueva dirección
        if (!usuarioOriginal.getEmail().equals(usuario.getEmail())) {
            
            usuario.setValidadoEmail(false);
            usuario.setClaveValidacionEmail(SecureKeyGenerator.getSecureKey());
            
            //REGLA DE NEGOCIO: No hay que notificar si es una cuenta del estilo de empleafp porque es de los usuarios que se borran.Issue #236. Si se cambia esta linea cambiar el método. SoftDelete
            if (usuarioOriginal.getEmail().matches("nobody_[0-9]*_[0-9]*@empleafp.com")==false) {
                notification.validarCuenta(usuario);
            }
        }

        return super.update(dataSession, usuario);
    }

    @Override
    public boolean delete(DataSession dataSession, Usuario entity) throws BusinessException {
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);

        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            if (entity.getTipoUsuario() == TipoUsuario.TITULADO) {
                CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) crudServiceFactory.getService(Candidato.class);

                Filters filters = new Filters();
                filters.add(new Filter("usuario.idIdentity", entity.getIdIdentity(), FilterOperator.eq));

                List<Candidato> candidatos = candidatoCRUDService.search(dataSession, filters, null, null);
                for (Candidato candidato : candidatos) {
                    candidatoCRUDService.delete(dataSession, candidato);
                }
                
                CRUDService<Titulado,Integer> tituladoCRUDService = (CRUDService<Titulado,Integer>) crudServiceFactory.getService(Titulado.class);
                
                if (entity.getTitulado()!=null) {
                    Titulado titulado=tituladoCRUDService.read(dataSession, entity.getTitulado().getIdTitulado());
                    tituladoCRUDService.delete(dataSession, titulado);
                }

            }
            boolean success = super.delete(dataSession, entity);

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            log.warn("Delete del usuario:"+entity.getIdIdentity());
            
            return success;
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }

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
    
    @Override
    public List<Usuario> getUsuariosFromEmpresa(DataSession dataSession, int idEmpresa) throws BusinessException {
        Filters filters = new Filters();
        filters.add(new Filter("empresa.idEmpresa", idEmpresa));
        List<Usuario> usuarios = this.getDAO().search(dataSession, filters, null, null);

        return usuarios;

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
    public void validarEmail(DataSession dataSession, Usuario usuario, String claveValidacionEmail) throws BusinessException {
        if (usuario != null) {
            if (equalsClavesSeguras(claveValidacionEmail,usuario.getClaveValidacionEmail())==false) {
                throw new BusinessException("El token no es válido");
            }
            
            usuario.setValidadoEmail(true);
            getUsuarioDAO().update(dataSession, usuario);
        } else {
            throw new BusinessException("El usuario no existe");
        }
    }

    @Override
    public void cancelarSuscripcion(DataSession dataSession,Usuario usuario, String publicToken) throws BusinessException {
        
        if (usuario==null) {
            throw new BusinessException("No existe el usuario");
        }
        byte[] secretToken=usuario.getSecretToken().getBytes(Charset.forName("utf-8"));

        
        PublicTokenCancelarSubcripcion publicTokenCancelarSubcripcion;
                
        try {
            publicTokenCancelarSubcripcion=new PublicTokenCancelarSubcripcion(publicToken, jws, secretToken);
        } catch (Exception ex) {
            throw new BusinessException("El token no tiene el formato adecuado");
        }
        
        if (publicTokenCancelarSubcripcion.isValid()==false) {
            throw new BusinessException("El token no es válido o ha caducado.");
        }
        
        if (usuario.getIdIdentity()!=publicTokenCancelarSubcripcion.getIdIdentity()) {
            throw new BusinessException("El token no es válido para ese usuario.");
        }
        
        if (usuario.getTipoUsuario()!=TipoUsuario.TITULADO) {
            throw new BusinessException("Esta acción solo es posible para titulados");
        }
        
        if (usuario.getTitulado()==null) {
            throw new BusinessException("El usuario aun no es un titulado");
        }
        
        log.warn("No notificar por email a un titulado las nuevas ofertas desde el link del correo."+usuario.getEmail());
        
        CRUDService<Titulado,Integer> tituladoCRUDService = (CRUDService<Titulado,Integer>) crudServiceFactory.getService(Titulado.class);
        Titulado titulado=tituladoCRUDService.read(dataSession, usuario.getTitulado().getIdTitulado());
        titulado.getConfiguracion().getNotificacionOferta().setNotificarPorEmail(false);
        tituladoCRUDService.update(dataSession, titulado);
    }    
    
    
    @Override
    public void resetearContrasenya(DataSession dataSession,Usuario usuario, String claveResetearContrasenya, String nuevaContrasenya) throws BusinessException {
        if (usuario != null) {
            if (!usuario.isValidadoEmail()) {
                log.warn("resetearContrasenya:La cuenta que no está validada."+usuario.getEmail());
                throw new BusinessException("La cuenta no está activada");
            }
            
            if (equalsClavesSeguras(claveResetearContrasenya,usuario.getClaveResetearContrasenya())==false) {
                log.warn("resetearContrasenya:El token no es válido."+usuario.getEmail());
                throw new BusinessException("El token no es válido");
            }           
            
            Date now = new Date();
            int diasClaveResetearPaswordEsValida = Integer.parseInt(Config.getSetting("app.diasClaveResetearPasswordEsValida"));
            if (now.before(DateUtil.add(usuario.getFechaClaveResetearContrasenya(), DateUtil.Interval.DAY, diasClaveResetearPaswordEsValida)))  {
                this.updatePassword(dataSession, usuario, nuevaContrasenya);
                usuario.setFechaClaveResetearContrasenya(null);
                usuario.setClaveResetearContrasenya(null);
                
                //Tambien desbloqueamos la cuenta
                usuario.setLockedUntil(null);
                usuario.setNumFailedLogins(0);
                
                getUsuarioDAO().update(dataSession, usuario);
            } else {
                log.warn("resetearContrasenya:El token ha caducado."+usuario.getEmail());
                throw new BusinessException("El token ha caducado");
            }
        } else {
            log.warn("resetearContrasenya:El usuario no existe");
            throw new BusinessException("El usuario no existe");
        }
    }

    @Override
    public void enviarMailResetearContrasenya(DataSession dataSession, String email) throws BusinessException {

        Usuario usuario = getUsuarioDAO().getUsuarioPorEmail(dataSession, email);
        if (usuario != null) {
            usuario.setFechaClaveResetearContrasenya(new Date());
            usuario.setClaveResetearContrasenya(SecureKeyGenerator.getSecureKey());
            getUsuarioDAO().update(dataSession, usuario);
            notification.resetearContrasenya(usuario);
            
            log.warn("Enviado correo para resetear contraseña a " + email);
        } else {
            log.warn("No existe el correo al que resetear con contraseña" + email);
            throw new BusinessException("No existe el usuario");
        }

    }

    @Override
    public int numUsuariosCentro(DataSession dataSession, Centro centro) throws BusinessException {
            Filters filters = new Filters();
            filters.add(new Filter("centro.idCentro", centro.getIdCentro(), FilterOperator.eq));
            filters.add(new Filter("validadoEmail", true, FilterOperator.eq));
            List<Usuario> usuarios = this.search(dataSession, filters, null, null);
            if (usuarios!=null) {
                return usuarios.size();
            } else {
                return 0;
            } 
    }

    @Override
    public byte[] getCurriculum(DataSession dataSession, Usuario usuario) throws BusinessException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idIdentity", usuario.getIdIdentity());

        byte[] curriculum;
                
        try {
            curriculum=reportService.exportToPdf(dataSession, "curriculum", parameters);
        } catch (Exception ex) {
            throw new RuntimeException("idIdentity="+usuario.getIdIdentity(),ex);
        }
        
        return curriculum;
    }
    
    @Override
    public void notificarUsuarioInactivo(DataSession dataSession, Usuario usuario) throws BusinessException {
        
        if (usuario==null) { 
            throw new RuntimeException("El usuario no puede ser null");
        }        

        if (usuario.getTipoUsuario() != TipoUsuario.TITULADO) { 
            throw new RuntimeException("Solo se puede notificar a usuarios de tipo Titulado. Pero el tipo es " + usuario.getTipoUsuario() + " para el usuario " + usuario.getIdIdentity() );
        }
        
        notification.usuarioInactivo(usuario);
        getUsuarioDAO().updateFechaEnvioCorreoAvisoBorrarUsuario(dataSession, usuario);
    }

    @Override
    public void softDelete(DataSession dataSession, Usuario usuario) throws BusinessException{
        
        if (usuario==null) { 
            throw new RuntimeException("El usuario no puede ser null");
        }        

        if (usuario.getTipoUsuario() != TipoUsuario.TITULADO) { 
            throw new RuntimeException("Solo se puede hacer un softDelete a usuarios de tipo Titulado. Pero el tipo es " + usuario.getTipoUsuario() + " para el usuario " + usuario.getIdIdentity() );
        }
        
     
        
        boolean isActivePreviousTransaction = transactionManager.isActive(dataSession);

        try {
            if (isActivePreviousTransaction == false) {
                transactionManager.begin(dataSession);
            }

            CandidatoCRUDService candidatoCRUDService = (CandidatoCRUDService) crudServiceFactory.getService(Candidato.class);
            Filters filters = new Filters();
            filters.add(new Filter("usuario.idIdentity", usuario.getIdIdentity(), FilterOperator.eq));
            List<Candidato> candidatos = candidatoCRUDService.search(dataSession, filters, null, null);
            for (Candidato candidato : candidatos) {
                candidatoCRUDService.softDelete(dataSession, candidato);
            }
            
            
              
            if (usuario.getTitulado()!=null) { 
  
                CRUDService<ExperienciaLaboral,Integer> experienciaLaboralCRUDService = crudServiceFactory.getService(ExperienciaLaboral.class);
                filters = new Filters();
                filters.add(new Filter("titulado.idTitulado", usuario.getTitulado().getIdTitulado(), FilterOperator.eq));
                List<ExperienciaLaboral> experienciasLaborales = experienciaLaboralCRUDService.search(dataSession, filters, null, null);
                for (ExperienciaLaboral experienciaLaboral : experienciasLaborales) {
                    experienciaLaboral.setDescripcion("Vacio");
                    experienciaLaboral.setNombreEmpresa("Vacio");
                    experienciaLaboral.setPuestoTrabajo("Vacio");
                    experienciaLaboralCRUDService.update(dataSession, experienciaLaboral);
                } 

                FormacionAcademicaCRUDService formacionAcademicaCRUDService = (FormacionAcademicaCRUDService)crudServiceFactory.getService(FormacionAcademica.class);
                filters = new Filters();
                filters.add(new Filter("titulado.idTitulado", usuario.getTitulado().getIdTitulado(), FilterOperator.eq));
                List<FormacionAcademica> formacionesAcademicas = formacionAcademicaCRUDService.search(dataSession, filters, null, null);
                for (FormacionAcademica formacionAcademica : formacionesAcademicas) {
                    formacionAcademicaCRUDService.softDelete(dataSession, formacionAcademica);
                } 


                CRUDService<Titulado,Integer> tituladoCRUDService = (CRUDService<Titulado,Integer>) crudServiceFactory.getService(Titulado.class);

                Titulado titulado=tituladoCRUDService.read(dataSession, usuario.getTitulado().getIdTitulado());

                {//Se cambia la fecha de nacimiento pq hay fechas errorneas y no dejaba borrar al titulado.
                    Date minDate=DateUtil.add(new Date(),DateUtil.Interval.YEAR,-16);
                    
                    if (minDate.before(titulado.getFechaNacimiento())) {
                        titulado.setFechaNacimiento(minDate);
                    }
                }
                
                titulado.getDireccion().setDatosDireccion("Sin dirección");
                titulado.setNumeroDocumento("2721C3A8");
                titulado.setTipoDocumento(TipoDocumento.OTRO);
                titulado.setOtrasCompetencias(null);
                titulado.setPermisosConducir(null);
                titulado.setResumen(null);
                titulado.setTelefono(null);
                titulado.setTelefonoAlternativo(null);
                titulado.getConfiguracion().getNotificacionOferta().setNotificarPorEmail(false);

                tituladoCRUDService.update(dataSession, titulado);

            }   
                
            Random random=new Random();
            usuario.setNombre("Nadie");
            usuario.setApellidos("Nobody");
            usuario.setEmail("nobody_" + usuario.getIdIdentity() + "_" +  random.nextInt(1000) + "@empleafp.com"); //Si se cambia esta linea, cambiar el método update. Issue #236
            /******** Es importate poner una fecha MUY a futuro para que no aparezcan en la pantalla de últimos accesos **********/
            usuario.setFechaUltimoAcceso(new GregorianCalendar(2050, Calendar.JANUARY, 1).getTime());
            usuario.setFechaEnvioCorreoAvisoBorrarUsuario(null);
            usuario.setFoto(new byte[]{});
            this.update(dataSession, usuario);
            
            String newPassword=RandomUtil.createRandomPaswword(12);
            this.updatePassword(dataSession, usuario, newPassword);
            getUsuarioDAO().softDelete(dataSession, usuario.getIdIdentity());

            if (isActivePreviousTransaction == false) {
                transactionManager.commit(dataSession);
            }

            log.warn("SoftDelete del titulado:"+usuario.getIdIdentity());
            
        } finally {
            if ((transactionManager.isActive(dataSession) == true) && (isActivePreviousTransaction == false)) {
                transactionManager.rollback(dataSession);
            }
        }   
        
    }
    
    
    private boolean equalsClavesSeguras(String clave1,String clave2) {
        if ((clave1==null) || (clave2==null)) {
            return false;
        }
        
        if ((clave1.trim().isEmpty()) || (clave2.trim().isEmpty())) {
            return false;
        }
        
        return clave1.equals(clave2);
        
    }

    @Override
    public boolean isLocked(DataSession dataSession, Usuario usuario) {
        if (usuario.getLockedUntil()==null) {
            return false;
        }
        
        Date ahora=new Date();
        
        if (ahora.after(usuario.getLockedUntil())) {
            return false;
        } else {
            return true;
        }
        
    }

    @Override
    public Date getLockedUntil(DataSession dataSession, Usuario usuario) {
        if (isLocked(dataSession, usuario)==false) {
            return null;
        } else {
            return usuario.getLockedUntil();
        }
    }

    @Override
    public void updateFailedLogin(DataSession dataSession, Usuario usuario) {
        int currentNumFailedLogins=usuario.getNumFailedLogins();
        
        int numFailedLogins=currentNumFailedLogins+1;
        int numMinutesLockedAccount=calculateMinutesLockedAccount(numFailedLogins);

        Date lockedUntil;
        if (numMinutesLockedAccount>0) {
            Date ahora=new Date();
            lockedUntil=DateUtil.add(ahora, DateUtil.Interval.MINUTE, numMinutesLockedAccount);
            
            String stringLockedUntil;
            if (DateUtils.isSameDay(lockedUntil, new Date())) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("'las 'HH:mm:ss");
                stringLockedUntil=simpleDateFormat.format(lockedUntil);
            } else {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("'el 'dd/MM/yyyy' a las 'HH:mm:ss");
                stringLockedUntil=simpleDateFormat.format(lockedUntil);
            }
            
            log.warn("Bloqueada cuenta " + usuario.getEmail() + " durante " + numMinutesLockedAccount + " minutos hasta " + stringLockedUntil + ". Ha fallado ya " + numFailedLogins + " veces");
        } else {
            lockedUntil=null;
        }
        
        getUsuarioDAO().updateFailedLogin(dataSession,usuario, lockedUntil, numFailedLogins);
        
    }
    
    /**
     * https://en.wikipedia.org/wiki/Logistic_function
     * Los minutos de bloqueo son: 1,2,6,16,39,93,207,....
     * @param numFailedLogins
     * @return 
     */
    private int calculateMinutesLockedAccount(int numFailedLogins) {
        int maxMinutesLocked=Integer.parseInt(Config.getSetting("app.account.maxMinutesLocked"));
        int minFailsUntilLock= Integer.parseInt(Config.getSetting("app.account.minFailsUntilLock"));
        
        double k=0.891;
        int x0=8;
        
        int minutesLockedAccount;
        
        if (numFailedLogins<minFailsUntilLock) {
            minutesLockedAccount=0;
        } else {
            minutesLockedAccount=(int)Math.floor(maxMinutesLocked/(1+Math.exp(-k*((numFailedLogins-minFailsUntilLock)-x0))));
        }
        
        if (minutesLockedAccount>maxMinutesLocked) {
            minutesLockedAccount=maxMinutesLocked;
        }
        
        return minutesLockedAccount;
    }


}
