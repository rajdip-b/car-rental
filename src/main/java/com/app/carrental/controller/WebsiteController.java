package com.app.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebsiteController {

    @GetMapping("/hi")
    @ResponseBody
    public String sayHi(){
        return "Hi!";
    }

}
