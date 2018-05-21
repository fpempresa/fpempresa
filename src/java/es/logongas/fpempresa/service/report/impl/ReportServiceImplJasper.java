/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.report.impl;

import es.logongas.fpempresa.service.report.ReportService;
import es.logongas.ix3.dao.DataSession;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
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
            throw new RuntimeException(ex);
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



}
