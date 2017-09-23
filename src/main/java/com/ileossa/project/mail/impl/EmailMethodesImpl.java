package com.ileossa.project.mail.impl;

import com.ileossa.project.api.dao.UserAccount;
import com.ileossa.project.mail.EmailMethodes;
import com.ileossa.project.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by ileossa on 02/08/2017.
 */
@Component("emailMetodes")
public class EmailMethodesImpl implements EmailMethodes {

    public JavaMailSender emailSender;
    private EmailService emailService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public EmailMethodesImpl(@Qualifier("mailSender") JavaMailSender emailSender, EmailService emailService) {
        this.emailSender = emailSender;
        this.emailService = emailService;
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

    @Override
    public void sendTokenRegistrationNewUser(UserAccount user, HttpServletRequest request) {
        String subject = "Registration Confirmation";
        String appUrl = request.getScheme() + "://" + request.getServerName();
        String message = "To confirm your e-mail address, please click the link below:\n"
                + appUrl + ":" + serverPort + "/confirm?token=" + user.getConfirmationToken();
        SimpleMailMessage registrationEmail = getSimpleMailMessage(user, subject, message);
        emailService.sendEmail(registrationEmail);
    }


    @Override
    public void sendTokenResetPasswordUser(UserAccount user, HttpServletRequest request) {
        String subject = "Reset Password";
        String appUrl = request.getScheme() + "://" + request.getServerName();
        String message = "Reset your password, please click the link below:\n"
                + appUrl + ":" + serverPort + "/confirm?token=" + user.getConfirmationToken();
        SimpleMailMessage registrationEmail = getSimpleMailMessage(user, subject, message);
        emailService.sendEmail(registrationEmail);
    }


    //-----------------------
    //   PRIVATE
    // ----------------------
    private SimpleMailMessage getSimpleMailMessage(UserAccount user, String subject, String message) {
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject(subject);
        registrationEmail.setText(message);
        registrationEmail.setFrom("noreply@babar.com");
        return registrationEmail;
    }
}






















