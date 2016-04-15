package com.projectb.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;
import java.util.Map;

@Controller
public class AuthController {
    @RequestMapping(value = "/signin")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/signout")
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "/signup")
    public String signup() {
        return "signup";
    }
}
