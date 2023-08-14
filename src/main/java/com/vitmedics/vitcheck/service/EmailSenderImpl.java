package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.model.Mail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Component
public class EmailSenderImpl implements EmailSender {

    private static final Logger log = LogManager.getLogger(EmailSenderImpl.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void sendHtmlEmail(Mail mail, ClientSmtpManager smtpManager) throws MessagingException, IllegalStateException {

        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost(smtpManager.getSmtpHost());
        emailSender.setPort(smtpManager.getSmtpPort());
        emailSender.setUsername(smtpManager.getUsername());
        emailSender.setPassword(smtpManager.getPassword());

        MimeMessage msg = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        helper.setTo(mail.getTo());
        helper.setFrom(smtpManager.getSenderAddress());
        helper.setSubject(mail.getSubject());

        Context context = new Context();
        context.setVariables(mail.getModel());

        String html = templateEngine.process(mail.getHtmlTemplate(), context);
        helper.setText(html, true);

        if(Strings.isNotEmpty(mail.getCc()))
           helper.setCc(mail.getCc());

        if(Strings.isNotEmpty(mail.getBcc()))
            helper.setCc(mail.getBcc());

        log.info(String.format("Sending mail to: %s. Model was: %s", mail.getTo(), mail.getModel()));
        emailSender.send(msg);
    }
}
