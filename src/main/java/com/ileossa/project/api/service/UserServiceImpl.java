package com.ileossa.project.api.service;

import com.ileossa.project.api.dao.UserDao;
import com.ileossa.project.api.dto.*;
import com.ileossa.project.exception.UserNotExist;
import com.ileossa.project.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ileossa on 25/07/2017.
 */
@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDao save(RegistrationDto registrationDto) throws UserNotExist {
        UserDao userDao = isExist(registrationDto.getEmail());
        userDao = userRepository.saveAndFlush(userDao);
        if(userDao != null){
            // TODO save email
        }
        return userDao;
    }

    @Override
    public UserDao update(UpdateDto updateDto) throws UserNotExist {
        UserDao userDao = isExist(updateDto.getEmail());
        userDao = userRepository.saveAndFlush(userDao);
        if(userDao != null){
            // TODO save email
        }
        return userDao;
    }

    @Override
    public void delete(DeleteDto deleteDto) {
        userRepository.delete(deleteDto.getId());
        // TODO send email

    }

    @Override
    public void login(LoginDto loginDto) {
        // spring security
    }

    @Override
    public void resend_mail(ResendEmailDto resendEmailDto) {
        userRepository.findUserDaoByEmailEquals(resendEmailDto.getEmail());
        // TODO send email
    }



    private UserDao isExist(String email) throws UserNotExist {
        UserDao userDao = userRepository.findUserDaoByEmailEquals(email);
        if(userDao == null){
            throw new UserNotExist(email + ", this user doesn't exist");
        }
        return userDao;
    }
}
