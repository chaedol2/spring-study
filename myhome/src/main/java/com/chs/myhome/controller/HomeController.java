package com.chs.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //첫 페이지 열기
    @GetMapping
    public String index(){
        return "index";
    }
}
