package com.ileossa.project.api.service;

import com.ileossa.project.api.dao.UserAccount;
import com.ileossa.project.api.dao.Role;
import com.ileossa.project.api.dto.*;
import com.ileossa.project.api.repository.UserRolesRepository;
import com.ileossa.project.exception.UserNotExist;
import com.ileossa.project.api.repository.UserRepository;
import com.ileossa.project.mail.EmailMethodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ileossa on 25/07/2017.
 */
@Service("userService")
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private UserRolesRepository userRolesRepository;
    private EmailMethodes emailMethodes;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRolesRepository userRolesRepository, EmailMethodes emailMethodes) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
        this.emailMethodes = emailMethodes;
    }


    @Override
    public UserAccount saveUser(UserAccount userAccount){
        userAccount.setRoles(new HashSet<Role>((Set<? extends Role>) userRolesRepository.findByName("ROLE_USER")));
        UserAccount user = userRepository.save(userAccount);
        return user;
    }


    @Override
    public UserAccount updateUser(UpdateDto updateDto) throws UserNotExist {
        UserAccount userAccount = isExist(updateDto.getEmail());
        userAccount = userRepository.save(userAccount);
        if(userAccount != null){
            // TODO save email
        }
        return userAccount;
    }

    @Override
    public void deleteUser(DeleteDto deleteDto) {
        userRepository.delete(deleteDto.getId());
        // TODO send email

    }

    @Override
    public UserAccount findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserAccount findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }


    @Override
    public void resendMail(ResendEmailDto resendEmailDto) {
        userRepository.findUserDaoByEmailEquals(resendEmailDto.getEmail());
        // TODO send email
    }

    @Override
    public void resetPassword(UserAccount user, HttpServletRequest request) {
       user.setEnabled(false);
       user.setConfirmationToken(UUID.randomUUID().toString());
       saveUser(user);
       emailMethodes.sendTokenResetPasswordUser(user, request);
    }


    private UserAccount isExist(String email) throws UserNotExist {
        UserAccount userAccount = userRepository.findUserDaoByEmailEquals(email);
        if(userAccount == null){
            throw new UserNotExist(email + ", this user doesn't exist");
        }
        return userAccount;
    }
}
