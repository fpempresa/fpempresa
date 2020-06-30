/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.mail;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GnommoStudios
 */
public class Mail {

    private List<String> to;

    private String from;

    private String subject;

    private String htmlBody;

    private String textBody;
    
    private List<Attach> attachs=new ArrayList<Attach>();
    
    private String reply;

    public Mail() {
        this.to = new ArrayList();
        this.reply=null;
    }

    public Mail(List<String> to, String from, String subject, String htmlBody, String textBody) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.htmlBody = htmlBody;
        this.textBody = textBody;
        this.reply=null;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public void addTo(String to) {
        this.to.add(to);
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the attachs
     */
    public List<Attach> getAttachs() {
        return attachs;
    }

    /**
     * @param attachs the attachs to set
     */
    public void setAttachs(List<Attach> attachs) {
        this.attachs = attachs;
    }

    /**
     * @return the reply
     */
    public String getReply() {
        return reply;
    }

    /**
     * @param reply the reply to set
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

}
