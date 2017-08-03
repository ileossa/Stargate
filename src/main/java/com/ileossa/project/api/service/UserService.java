package com.ileossa.project.api.service;

import com.ileossa.project.api.dao.UserDao;
import com.ileossa.project.api.dto.*;
import com.ileossa.project.exception.UserNotExist;

/**
 * Created by ileossa on 01/08/2017.
 */
public interface UserService {

    public UserDao save(RegistrationDto registrationDto) throws UserNotExist;
    public UserDao update(UpdateDto updateDto) throws UserNotExist;
    public void delete(DeleteDto deleteDto);

    // API
    public void login(LoginDto loginDto);
//    public void logout();
    public void resend_mail(ResendEmailDto resendEmailDto);
}
