package com.vitmedics.vitcheck.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Objects;

public class Mail {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String htmlTemplate;
    private HashMap<String, Object> model;

    public Mail(@NotBlank String to, @NotBlank String subject, @NotBlank String htmlTemplate) {
        this.to = to;
        this.subject = subject;
        this.htmlTemplate = htmlTemplate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "to='" + to + '\'' +
                ", cc='" + cc + '\'' +
                ", bcc='" + bcc + '\'' +
                ", subject='" + subject + '\'' +
                ", htmlTemplate='" + htmlTemplate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return Objects.equals(to, mail.to) && Objects.equals(cc, mail.cc) && Objects.equals(bcc, mail.bcc) && Objects.equals(subject, mail.subject) && Objects.equals(htmlTemplate, mail.htmlTemplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, cc, bcc, subject, htmlTemplate);
    }

    public HashMap<String, Object> getModel() {
        return model;
    }

    public void setModel(HashMap<String, Object> model) {
        this.model = model;
    }
}
