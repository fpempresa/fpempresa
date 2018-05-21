
package es.logongas.fpempresa.service.report.impl;

import java.io.InputStream;

/**
 *
 * @author logongas
 */
public class ReportResourceLocator {
    
    public static InputStream getResource(String resourceName) {
        return ReportResourceLocator.class.getResourceAsStream("../files/"+resourceName);
    }
    
}
