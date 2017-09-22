package com.ileossa.project.mail;

import com.ileossa.project.api.dao.UserAccount;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ileossa on 02/08/2017.
 */
public interface EmailMethodes {

    void sendSimpleMessage(String to, String subject, String message);
    void sendSimpleMessageWithAttachment(String to, String subject, String message, String pathFile)  throws MessagingException;

    void sendTokenRegistrationNewUser(UserAccount userAccount, HttpServletRequest request);
    void sendTokenResetPasswordUser(UserAccount userAccount, HttpServletRequest request);

}
