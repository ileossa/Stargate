package com.ileossa.project.api.controller;

import com.ileossa.project.api.dao.UserAccount;
import com.ileossa.project.api.service.SecurityService;
import com.ileossa.project.api.service.UserService;
import com.ileossa.project.exception.UserNotExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by ileossa on 21/09/2017.
 */
@Controller
public class ResetPasswordController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserService userService;
    private SecurityService securityService;

    @Autowired
    public ResetPasswordController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "/reset", method = GET)
    public ModelAndView showLoginForm(ModelAndView modelAndView){
        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    @RequestMapping(value = "/reset", method = POST)
    public String processResetPassword(ModelAndView modelAndView, @Valid UserAccount userAccount, BindingResult bindingResult, HttpServletRequest request) throws UserNotExist {
        // Lookup user in database by e-mail
        UserAccount userExists = userService.findByEmail(userAccount.getEmail());

        log.debug(String.valueOf(userExists));

        String urlWithTokenForRegisterPassword = "/home";
        if(userExists != null){
            urlWithTokenForRegisterPassword = securityService.generateUrlWithTokenForPassword(userExists, request);
        }
        return "redirect:" + urlWithTokenForRegisterPassword;
    }
}
