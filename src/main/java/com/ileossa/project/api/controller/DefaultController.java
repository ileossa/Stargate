package com.ileossa.project.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ileossa on 24/07/2017.
 */
@Controller
public class DefaultController {

    @GetMapping("/")
    public String home1() {
        return "/index";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "/logout";
    }

    @GetMapping("/gallerie")
    public String gallerie(){
        return "/gallerie";
    }

    @GetMapping("/index")
    public String index(){
        return "/index";
    }

    @GetMapping("/pannelAdmin")
    public String pannelAdmin(){
        return "/pannelAdmin";
    }

    @GetMapping("/simpleAdmin")
    public String simpleAdmin(){
        return "/simpleAdmin";
    }

    @PostMapping("/echo/{string}")
    @ResponseBody
    public String echo(@PathVariable String string, @RequestBody String id){
        return id;
    }

}
