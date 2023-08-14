package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.model.Mail;

import javax.mail.MessagingException;
import java.util.Optional;

public interface EmailSender {
    void sendHtmlEmail(Mail mail, ClientSmtpManager smtpManager) throws MessagingException, IllegalStateException;
}
