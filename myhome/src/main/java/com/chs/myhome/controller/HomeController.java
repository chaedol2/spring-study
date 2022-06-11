package com.chs.myhome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    //첫 페이지 열기
    @GetMapping
    public String index(){
        LOGGER.info("index 출력!");

        return "index";
    }
}
