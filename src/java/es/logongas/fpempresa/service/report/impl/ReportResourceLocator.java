
package es.logongas.fpempresa.service.report.impl;

import java.io.InputStream;

/**
 *
 * @author logongas
 */
public class ReportResourceLocator {
    
    public static InputStream getResource(String resourceName) {
        String fullPath="../files/"+resourceName;
        InputStream inputStream=ReportResourceLocator.class.getResourceAsStream(fullPath);
        
        if (inputStream==null) {
                throw new RuntimeException("No existe  el recurso:"+fullPath);
        }        
        
        return inputStream;
    }
    
}
