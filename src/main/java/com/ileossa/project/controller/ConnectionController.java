package com.ileossa.project.controller;

import com.ileossa.project.dto.UserDto;
import com.ileossa.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

/**
 * Created by ileossa on 24/07/2017.
 */
@Controller
public class ConnectionController extends WebMvcConfigurerAdapter{

    private final UserService userService;

    @Autowired
    public ConnectionController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/registration")
    public String showForm(UserDto userDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public String checkPersonInfo(@Valid UserDto userDto, BindingResult bindingResult) {

        // check if Moodel received is conform
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.newUser(userDto);

        return "redirect:/results";
    }
}
