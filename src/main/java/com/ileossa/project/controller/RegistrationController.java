package com.ileossa.project.controller;

import com.ileossa.project.dto.RegistrationDto;
import com.ileossa.project.exception.UserNotExist;
import com.ileossa.project.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

/**
 * Created by ileossa on 24/07/2017.
 */
@Controller
public class RegistrationController extends WebMvcConfigurerAdapter{

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public RegistrationController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/registration")
    public String showForm(RegistrationDto registrationDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public String checkPersonInfo(@Valid RegistrationDto registrationDto, BindingResult bindingResult) {

        // check if Moodel received is conform
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userServiceImpl.save(registrationDto);
        } catch (UserNotExist userNotExist) {
            // nothing, default user doesn't exist
        }
        return "redirect:/results";
    }
}
