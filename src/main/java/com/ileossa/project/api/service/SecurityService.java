package com.ileossa.project.api.service;

import com.ileossa.project.api.dao.UserAccount;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ileossa on 21/09/2017.
 */
public interface SecurityService {

    String findLoggedInEmail();
    void autologin(String email, String password);
    String generateUrlWithTokenForPassword(UserAccount user, HttpServletRequest request);

}
