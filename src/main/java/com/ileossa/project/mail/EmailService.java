package com.ileossa.project.mail;

import javax.mail.MessagingException;

/**
 * Created by ileossa on 02/08/2017.
 */
public interface EmailService {

    void sendSimpleMessage(String to, String subject, String message);
    void sendSimpleMessageWithAttachment(String to, String subject, String message, String pathFile)  throws MessagingException;


}
