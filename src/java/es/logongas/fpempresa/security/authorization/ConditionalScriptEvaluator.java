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
public interface ConditionalScriptEvaluator {

    public Boolean evaluate(Identity identity,Object arguments);
    
}
