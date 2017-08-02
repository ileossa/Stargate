package com.ileossa.project.config;

import com.ileossa.project.mail.EmailService;
import com.ileossa.project.mail.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by ileossa on 02/08/2017.
 */
@Configuration
@ImportResource(value = "classpath:/Tests/spring/appXMLContext.xml")
@PropertySource(value = "classpath:/application.yml")
@ActiveProfiles("test")
public class AppConfig {

    // You don't need this config, only if you want specific properties for test
//    @Bean
//    public JavaMailSenderImpl emailSender(@Value("${email.host}") String emailHost,
//                                          @Value("${email.port}") Integer emailPort,
//                                          @Value("${email.username}") String username,
//                                          @Value("${email.pass}") String password){
//        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
//        emailSender.setHost(emailHost);
//        emailSender.setPort(emailPort);
//        emailSender.setUsername(username);
//        emailSender.setPassword(password);
//        //emailSender.setDefaultEncoding("UTF_8");
//        Properties mailProps = new Properties();
//        mailProps.setProperty("mail.transport.protocol","smtp");
//        mailProps.setProperty("mail.smtp.auth","true");
//        mailProps.setProperty("mail.smtp.starttls.enable","true");
//        mailProps.setProperty("mail.debug","false");
//        emailSender.setJavaMailProperties(mailProps);
//        return emailSender;
//    }


    @Bean
    public JavaMailSenderImpl javaMailSender(){
        return new JavaMailSenderImpl();
    }

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Bean
    public EmailService emailService(){
        return new EmailServiceImpl(javaMailSender);
    }


}
