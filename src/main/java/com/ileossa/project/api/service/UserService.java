package com.ileossa.project.api.service;

import com.ileossa.project.api.dao.UserAccount;
import com.ileossa.project.api.dto.*;
import com.ileossa.project.exception.UserNotExist;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ileossa on 01/08/2017.
 */
public interface UserService{

    public UserAccount saveUser(UserAccount userAccount) throws UserNotExist;
    public UserAccount updateUser(UpdateDto updateDto) throws UserNotExist;
    public void deleteUser(DeleteDto deleteDto);

    public UserAccount findByEmail(String email);
    public UserAccount findByConfirmationToken(String confirmationToken);


//    public void logout();
    public void resendMail(ResendEmailDto resendEmailDto);
    public String resetPassword(UserAccount userAccount, HttpServletRequest request);
}
