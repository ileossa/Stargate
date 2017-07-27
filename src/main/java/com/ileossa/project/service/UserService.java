package com.ileossa.project.service;

import com.ileossa.project.dao.RoleDao;
import com.ileossa.project.dao.UserDao;
import com.ileossa.project.dao.staticValues.Role;
import com.ileossa.project.dto.UserDto;
import com.ileossa.project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by ileossa on 25/07/2017.
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public UserDao newUser(UserDto userDto){

        UserDao userDao = importToDAO(userDto);
        userDao = userRepository.saveAndFlush(userDao);
        if(userDao != null){
            // TODO save email
        }
        return userDao;
    }


    public UserDao updateUser(UserDto userDto){

        UserDao userDao = importToDAO(userDto);
        userDao = userRepository.saveAndFlush(userDao);
        if(userDao != null){
            // TODO send email
        }
        return userDao;
    }


    public boolean deleteUser(UserDto userDto){
        UserDao userDao = importToDAO(userDto);
        userRepository.delete(userDao);
        if(userDao != null){
            // TODO send mail
        }
        return true;
    }




    private UserDao importToDAO( UserDto userDto){
        UserDao userDao = userRepository.findUserDaoByEmailEquals(userDto.getEmail());

        // FIXME je dois pas laisser comme ca
        Set<UserDao> userList = new HashSet<>();
        RoleDao roleDao = new RoleDao(Role.GHOST, userList);
        Set<RoleDao> role = new HashSet<>();
        role.add(roleDao);

        if(userDao == null){
            userDao = new UserDao();
        }
        userDao.setAge(userDto.getAge());
        userDao.setEmail(userDto.getEmail());
        userDao.setName(userDto.getName());
        userDao.setPassword(userDto.getPassword());
        userDao.setUserRoles(role);
        return userDao;
    }


}
