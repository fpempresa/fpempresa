/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.logongas.fpempresa.service.report.impl;

import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.ix3.dao.DataSession;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.Map.Entry;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author logongas
 */
public class ReportServiceImplJasper implements ReportService {

    @Override
    public byte[] exportToPdf(DataSession dataSession,String reportName,Map<String, Object> parameters) {
        try {
            Connection connection=((org.hibernate.internal.SessionImpl)dataSession.getDataBaseSessionImpl()).connection();
            InputStream jasperReport = ReportResourceLocator.getResource(reportName+".jasper");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException ex) {
            throw new RuntimeException(getStringFromMap(parameters),ex);
        }
    }

    @Override
    public void setEntityType(Class t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Class getEntityType() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }


    private String getStringFromMap(Map<String, Object> parameters)  {
        StringBuilder sb=new StringBuilder();
        
        for(Entry<String, Object> entry:parameters.entrySet()) {
            sb.append(entry.getKey()+"="+entry.getValue()+" , ");
        }
        
        return sb.toString();
    }
    
    

}
