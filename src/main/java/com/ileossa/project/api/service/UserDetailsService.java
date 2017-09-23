package com.ileossa.project.api.service;

import com.ileossa.project.api.dao.UserAccount;
import com.ileossa.project.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by ileossa on 21/09/2017.
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userRepository.findByEmail(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for(Role roles : userAccount.getRoles()){
            grantedAuthorities.add((new SimpleGrantedAuthority(userAccount.getRoles())));
//        }

        return new User(userAccount.getEmail(), userAccount.getPassword(), grantedAuthorities);
    }
}