/*
 * Copyright 2025 Lorenzo González.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.logongas.fpempresa.security.authorization;

import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcessFactory;
import es.logongas.ix3.businessprocess.security.PermissionTypeBusinessProcess;
import es.logongas.ix3.businessprocess.security.SecureResourceTypeBusinessprocess;
import es.logongas.fpempresa.security.authorization.annotations.ACE;
import es.logongas.fpempresa.security.authorization.annotations.ACEType;
import es.logongas.fpempresa.security.authorization.annotations.PostAuthorization;
import es.logongas.fpempresa.security.authorization.annotations.PreAuthorization;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.dao.metadata.MetaData;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.security.authorization.AuthorizationProvider;
import es.logongas.ix3.security.authorization.AuthorizationType;
import es.logongas.ix3.security.model.GroupMember;
import es.logongas.ix3.security.model.Identity;
import es.logongas.ix3.security.model.SpecialUsers;
import static es.logongas.ix3.util.ReflectionUtil.findUniqueMethodByNameBestNoSyntheticNoBridge;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 *
 * @author logongas
 */
public class AuthorizationProviderImplAnnotation implements AuthorizationProvider {

    @Autowired
    private MetaDataFactory metaDataFactory;
    @Autowired
    private CRUDBusinessProcessFactory crudBusinessProcessFactory;

    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public AuthorizationType authorized(Principal principal, String secureResourceTypeName, String secureResource, String permissionName, Object arguments, DataSession dataSession) {
        Identity identity=(Identity)principal;

        if (secureResourceTypeName.equals(SecureResourceTypeBusinessprocess.BusinessProcess.name()) == true) {

            Class entityType = getEntityClass(secureResource);
            if (entityType==null) {
                return AuthorizationType.Abstain;
            }
            CRUDBusinessProcess crudBusinessProcess = crudBusinessProcessFactory.getBusinessProcess(entityType);         
            String methodName = getMethodName(secureResource);

            if (permissionName.equals(PermissionTypeBusinessProcess.PreExecuteBusinessProcess.name())) {
                PreAuthorization preAuthorization = getMethodAnnotation(crudBusinessProcess, methodName, PreAuthorization.class);
                
                if (preAuthorization!=null) {
                    List<ACE> acl = getACLFromIdentity(preAuthorization.acl(), identity);
                    if ((acl == null) || (acl.isEmpty())) {
                        return AuthorizationType.Abstain;
                    }

                    AuthorizationType authorizationType = authorizedPreExecute(acl,identity, arguments, dataSession);

                    return authorizationType;
                } else {
                    return AuthorizationType.Abstain;
                }
            } else if (permissionName.equals(PermissionTypeBusinessProcess.PostExecuteBusinessProcess.name())) {
                PostAuthorization postAuthorization = getMethodAnnotation(crudBusinessProcess, methodName, PostAuthorization.class);
                                
                if (postAuthorization!=null) {
                    List<ACE> acl = getACLFromIdentity(postAuthorization.acl(), identity);
                    if ((acl == null) || (acl.isEmpty())) {
                        return AuthorizationType.Abstain;
                    }

                    AuthorizationType authorizationType = authorizedPostExecute(acl,identity, arguments, dataSession);

                    return authorizationType;
                } else {
                    return AuthorizationType.Abstain;
                }                    
            } else {
                throw new RuntimeException("Tipo de permiso desconocido:" + permissionName);
            }

        } else {
            return AuthorizationType.Abstain;
        }
    }

    private List<ACE> getACLFromIdentity(ACE[] acl, Identity identity) {
        List<ACE> aclList = new ArrayList<>();
        

        if (acl == null) {
            return aclList;
        } else {
            for (ACE ace : acl) {
                
                
                if ((ace.groupLogin()==null) || (ace.groupLogin().trim().isEmpty())) {
                    throw new RuntimeException("El grupo del ACE no puede estar vacio");
                } 
                if (ace.aceType()==null) {
                    throw new RuntimeException("El tipo del ACE no puede estar vacio");
                } 
                
                if (isNameGroupInSpecialUsers(ace.groupLogin(),identity)) {
                    aclList.add(ace);
                } else if (identity!=null) {
                    if (isNameGroupInIdentity(ace.groupLogin(),identity.getMemberOf())) {
                        aclList.add(ace);
                    } else if (ace.groupLogin().equals(identity.getLogin())) {
                        aclList.add(ace);
                    } 
                }
            }

            return aclList;
        }
    }

    
    private boolean isNameGroupInSpecialUsers(String aceGroupLogin,Identity identity) {
        if (SpecialUsers.All.equals(aceGroupLogin)) {
            return true;
        } else if (SpecialUsers.Authenticated.equals(aceGroupLogin) && (identity!=null)) {
            return true;
        } else {        
            return false;
        }
    }

    
    
    private boolean isNameGroupInIdentity(String aceGroupLogin, Set<GroupMember> groupMembers) {
       
        if (groupMembers != null) {
            for (GroupMember groupMember : groupMembers) {
                
                String groupLogin=groupMember.getGroup().getLogin();
                String identityLogin=groupMember.getIdentity().getLogin();
                
                
                if (aceGroupLogin.equals(groupLogin)) {
                   return true;
                } else if (aceGroupLogin.equals(identityLogin)) {
                   return true; 
                } else {
                    return isNameGroupInIdentity(aceGroupLogin,groupMember.getGroup().getGroupMembers());
                }
            }
            
            return false;
        } else {
            return true;
        }
    }

    
    
    
    private Class getEntityClass(String secureResource) {
        if (secureResource == null || secureResource.isEmpty()) {
            return null;
        }
        String[] palabras = secureResource.split("\\.");
        if (palabras.length >= 2) {
            String entityName = palabras[palabras.length - 2];

            MetaData metaData = metaDataFactory.getMetaData(entityName);
            if (metaData == null) {
                return null;
            }

            return metaData.getType();
        } else {
            return null;
        }
    }

    private String getMethodName(String secureResource) {
        if (secureResource == null || secureResource.isEmpty()) {
            return null;
        }
        String[] palabras = secureResource.split("\\.");
        if (palabras.length > 0) {
            return palabras[palabras.length - 1];
        }
        return null;
    }

    
    private <T extends Annotation> T getMethodAnnotation(Object businessProcessProxy, String methodName, Class<T> annotationClass) {
        Class businessProcessClass=AopProxyUtils.ultimateTargetClass(businessProcessProxy);
                
        Method[] methods;
        T annotationDeclared=null;
        T annotationParent=null;
                
        methods = businessProcessClass.getDeclaredMethods();
        Method methodDeclared=findUniqueMethodByNameBestNoSyntheticNoBridge(methods, methodName);
        if (methodDeclared!=null) {
            annotationDeclared=methodDeclared.getAnnotation(annotationClass);
        }
        
        methods = businessProcessClass.getMethods();
        Method methodParent=findUniqueMethodByNameBestNoSyntheticNoBridge(methods, methodName);
        if (methodDeclared!=null) {
            annotationParent=methodParent.getAnnotation(annotationClass);
        }  


        if ((annotationDeclared==null) && (annotationParent==null)) {
            return null;
        } else if ((annotationDeclared==null) && (annotationParent!=null)) {
            return annotationParent;
        } else if ((annotationDeclared!=null) && (annotationParent==null)) {
            return annotationDeclared;
        } else if ((annotationDeclared!=null) && (annotationParent!=null)) {
            return annotationDeclared;
        } else {
            throw new RuntimeException("Error de lógica:"+ (annotationDeclared==null) + " " + (annotationParent!=null));
        }
        
    }
    
    private AuthorizationType authorizedPostExecute(List<ACE> acl, Identity identity, Object arguments, DataSession dataSession) {
        if ((acl == null) || (acl.isEmpty())) {
            return AuthorizationType.Abstain;
        } else {
            for (ACE ace : acl) {
                AuthorizationType authorizationType = authorizedACE(ace, identity, arguments, dataSession);

                if (authorizationType == AuthorizationType.AccessAllow) {
                    return authorizationType;
                } else if (authorizationType == AuthorizationType.AccessDeny) {
                    return authorizationType;
                }
            }

            return AuthorizationType.Abstain;
        }
    }

    private AuthorizationType authorizedPreExecute(List<ACE> acl,Identity identity, Object arguments, DataSession dataSession) {
        if ((acl == null) || (acl.isEmpty())) {
            return AuthorizationType.Abstain;
        } else {
            for (ACE ace : acl) {
                AuthorizationType authorizationType = authorizedACE(ace,identity, arguments, dataSession);

                if (authorizationType == AuthorizationType.AccessAllow) {
                    return authorizationType;
                } else if (authorizationType == AuthorizationType.AccessDeny) {
                    return authorizationType;
                }
            }

            return AuthorizationType.Abstain;
        }
    }

    private AuthorizationType authorizedACE(ACE ace,Identity identity, Object arguments, DataSession dataSession) {
        AuthorizationType authorizationType;

        if ((existsConditionalExpression(ace.conditionalExpression()) == false) && (existsConditionalScript(ace.conditionalScriptEvaluatorFQCN()) == false)) {
            authorizationType = aceTypeToAuthorizationType(ace.aceType());
        } else if ((existsConditionalExpression(ace.conditionalExpression()) == true) && (existsConditionalScript(ace.conditionalScriptEvaluatorFQCN()) == false)) {
            if (evaluateConditionalExpression(ace.conditionalExpression(), identity, arguments) == true) {
                authorizationType = aceTypeToAuthorizationType(ace.aceType());
            } else {
                authorizationType = AuthorizationType.Abstain;
            }
        } else if ((existsConditionalExpression(ace.conditionalExpression()) == false) && (existsConditionalScript(ace.conditionalScriptEvaluatorFQCN()) == true)) {
            if (evaluateConditionalScript(ace.conditionalScriptEvaluatorFQCN(), identity, arguments) == true) {
                authorizationType = aceTypeToAuthorizationType(ace.aceType());
            } else {
                authorizationType = AuthorizationType.Abstain;
            }
        } else if ((existsConditionalExpression(ace.conditionalExpression()) == true) && (existsConditionalScript(ace.conditionalScriptEvaluatorFQCN()) == true)) {
            throw new RuntimeException("No es posible indicar un Script y una expresion para el ACE:" + ace);
        } else {
            throw new RuntimeException("Error de lógica para el ACE:" + ace);
        }

        return authorizationType;
    }

    private AuthorizationType aceTypeToAuthorizationType(ACEType aceType) {
        switch (aceType) {
            case Allow:
                return AuthorizationType.AccessAllow;
            case Deny:
                return AuthorizationType.AccessDeny;
            default:
                throw new RuntimeException("El tipo de ACE es desconocido:" + aceType);
        }
    }

    /**
     * Evalua el Script de un ACE
     *
     * @param conditionalScriptEvaluatorClass La clase Java que tiene es
     * "Script" a ejecutar
     * @param arguments Los argumentos de la petición
     * @return El resultado de evaluarlo
     */
    private boolean evaluateConditionalScript(String conditionalScriptEvaluatorFQCN,Identity identity, Object arguments) {
        try {

            if (conditionalScriptEvaluatorFQCN == null) {
                throw new RuntimeException("No podemos evaluar un Script si es null");
            }

            Object objScript = ConditionalScriptEvaluator.class.getClassLoader().loadClass(conditionalScriptEvaluatorFQCN).newInstance();
            if (!(objScript instanceof ConditionalScriptEvaluator)) {
                throw new RuntimeException("La clase:" + conditionalScriptEvaluatorFQCN + " no implementa el interfaz " + ConditionalScriptEvaluator.class.getName());
            }
            ConditionalScriptEvaluator conditionalScriptEvaluator = (ConditionalScriptEvaluator) objScript;

            Boolean result = conditionalScriptEvaluator.evaluate(identity,arguments);

            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Evalua una expresión de un ACE
     *
     * @param conditionalExpression Expresión a evaluar
     * @param arguments Los argumentos de la petición
     * @return El resultado de evaluarlo
     */
    private boolean evaluateConditionalExpression(String conditionalExpression,Identity identity, Object arguments) {
        try {
            Object result;

            //Si no hay Script retornamos 'null'
            if ((conditionalExpression == null) || (conditionalExpression.trim().length() == 0)) {
                throw new RuntimeException("No podemos evaluar una expresion vacia del ACE:" + conditionalExpression);
            }

            EvaluationContext context = new StandardEvaluationContext(new ConditionalExpressionSpelContext(identity,arguments));
            result = expressionParser.parseExpression(conditionalExpression).getValue(context, Object.class);
            if (result == null) {
                throw new RuntimeException("La expresion no puede retornar null de:" + conditionalExpression);
            }
            if (!(result instanceof Boolean)) {
                throw new RuntimeException("La expresion no es un boolean de:" + conditionalExpression);
            }

            return (Boolean) result;
        } catch (Exception ex) {
            throw new RuntimeException("conditionalExpression:" + conditionalExpression, ex);
        }
    }

    protected boolean existsConditionalExpression(String conditionalExpression) {
        if ((conditionalExpression == null) || (conditionalExpression.trim().length() == 0)) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean existsConditionalScript(String conditionalScriptEvaluatorFQCN) {
        if ((conditionalScriptEvaluatorFQCN == null) || (conditionalScriptEvaluatorFQCN.trim().length() == 0)) {
            return false;
        } else {
            return true;
        }
    }

}
