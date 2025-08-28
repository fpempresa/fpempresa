/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.security.authorization;

import es.logongas.ix3.security.model.Identity;

/**
 *
 * @author logongas
 */
public class ConditionalExpressionSpelContext {

    private final Identity identity;
    public final Object arguments;

    /**
     * @param identity
     * @param arguments Los argumentos de la petición
     */
    public ConditionalExpressionSpelContext(Identity identity, Object arguments) {
        this.identity = identity;
        this.arguments = arguments;
    }

    /**
     * @return the arguments
     */
    public Object getArguments() {
        return arguments;
    }

    /**
     * @return the identity
     */
    public Identity getIdentity() {
        return identity;
    }



}
