package com.ileossa.project.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by ileossa on 20/09/2017.
 */
@Service("emailService")
public class EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    // When I called this code, i doesn't have to wait the complete operation
    @Async
    public void sendEmail(SimpleMailMessage email){
        mailSender.send(email);
    }
}
