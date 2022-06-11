package com.chs.myhome.controller;

import com.chs.myhome.model.User;
import com.chs.myhome.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        LOGGER.info("login 호출!");
        return "account/login";
    }

    @GetMapping("/register")
    public String register(){
        LOGGER.info("register(get) 호출!");

        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user){
        LOGGER.info("register(post) 호출!");
        userService.save(user);
        return "redirect:/";
    }
}
