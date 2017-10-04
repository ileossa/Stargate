package com.ileossa.project.api.controller;

import com.ileossa.project.api.dao.Roles;
import com.ileossa.project.api.dao.UserAccount;
import com.ileossa.project.api.service.SecurityService;
import com.ileossa.project.api.service.UserService;
import com.ileossa.project.exception.UserNotExist;
import com.ileossa.project.mail.EmailMethodes;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ileossa on 24/07/2017.
 */
@Controller
public class RegistrationController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailMethodes emailMethodes;
    private SecurityService securityService;

    @Autowired
    public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder,
                                  UserService userService, @Qualifier("emailMetodes") EmailMethodes emailMethodes, SecurityService securityService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailMethodes = emailMethodes;
        this.securityService = securityService;
    }

    // Return registration form template
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, UserAccount user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    // Process form input data
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistrationForm(ModelAndView modelAndView, @Valid UserAccount user, BindingResult bindingResult, HttpServletRequest request) throws UserNotExist {

        modelAndView.addObject("user", new UserAccount("", "","","",null, false, ""));
        String urlWithTokenForRegisterPassword = "login";

        // Lookup user in database by e-mail
        UserAccount userExists = userService.findByEmail(user.getEmail());

        log.trace(String.valueOf(userExists));

        if (userExists != null) {
            modelAndView.addObject("errorMessage", "Oops!  There is already a user registered with the email provided.");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors())
        {
        }
        else { // new user so we create user and send confirmation e-mail

            // Disable user until they click on confirmation link in email
            user.setEnabled(false);

            // Generate random 36-character string token for confirmation link
            user.setConfirmationToken(UUID.randomUUID().toString());

            user.setRoles(String.valueOf(Roles.USER));

            userService.saveUser(user);

            urlWithTokenForRegisterPassword = securityService.generateUrlWithTokenForPassword(user, request);

            modelAndView.addObject("confirmationMessage", urlWithTokenForRegisterPassword);
        }
        return "redirect:" + urlWithTokenForRegisterPassword;
//        return modelAndView;
    }

    // Process confirmation link
    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {

        UserAccount user = userService.findByConfirmationToken(token);

        if (user == null) { // No token found in DB
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else { // Token found
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    // Process confirmation link
    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    public ModelAndView confirmRegistration(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) throws UserNotExist {

        modelAndView.setViewName("confirm");

        if( requestParams.get("password").equals(requestParams.get("ConfirmPassword"))){
            Zxcvbn passwordCheck = new Zxcvbn();

            Strength strength = passwordCheck.measure(requestParams.get("password"));

            if (strength.getScore() < 3) {
                //modelAndView.addObject("errorMessage", "Your password is too weak.  Choose a stronger one.");
                bindingResult.reject("password");

                redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

                modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
                log.trace(requestParams.get("token"));
                return modelAndView;
            }

            // Find the user associated with the reset token
            UserAccount user = userService.findByConfirmationToken(requestParams.get("token"));

            // Set new password
            user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            // Set user to enabled
            user.setEnabled(true);

            // Save user
            userService.saveUser(user);

            modelAndView.addObject("successMessage", "Your password has been set! You can close this windows.");
        }else{
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage", "Both passwords must be identical");

            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
            log.trace(requestParams.get("token"));
            return modelAndView;
        }

        return modelAndView;
    }
}
