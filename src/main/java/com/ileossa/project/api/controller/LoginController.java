package com.ileossa.project.api.controller;

import com.ileossa.project.api.dao.UserAccount;
import com.ileossa.project.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by ileossa on 21/09/2017.
 */
@Controller
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = GET)
    public ModelAndView showLoginForm(ModelAndView modelAndView, UserAccount userAccount){
        modelAndView.addObject("user", userAccount);
        modelAndView.setViewName("login");
        return modelAndView;
    }


}
