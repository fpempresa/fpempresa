/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.modelo.mail;

import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author GnommoStudios
 */
public class Mail {

    private List<InternetAddress> to;

    private InternetAddress from;

    private String subject;

    private String body;

    public Mail() {
        this.to = new ArrayList();
    }

    public Mail(List<InternetAddress> to, InternetAddress from, String subject, String body) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
    }

    public List<InternetAddress> getTo() {
        return to;
    }

    public void setTo(List<InternetAddress> to) {
        this.to = to;
    }

    public InternetAddress getFrom() {
        return from;
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
    public void addTo(InternetAddress internetAddress){
        this.to.add(internetAddress);
    }

}
