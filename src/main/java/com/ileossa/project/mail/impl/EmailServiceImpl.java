package com.ileossa.project.mail.impl;

import com.ileossa.project.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by ileossa on 02/08/2017.
 */
@Component
public class EmailServiceImpl implements EmailService {

    public JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subect, String text){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subect);
        msg.setText(text);
        emailSender.send(msg);
    }

    public void sendSimpleMessageWithAttachment( String to, String subject, String text, String attchement) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        // second param TRUE, it s for activate the multipart option
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file = new FileSystemResource(new File(attchement));
        helper.addAttachment("fileAttchement.jpg", file);

        emailSender.send(message);
    }
}






















