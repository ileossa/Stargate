package com.ileossa.project.api.service;

/**
 * Created by ileossa on 21/09/2017.
 */
interface SecurityService {

    String findLoggedInEmail();
    void autologin(String email, String password);
}
