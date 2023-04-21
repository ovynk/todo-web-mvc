package com.oleksiy.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/form-login")
    public String loginForm(){
        return "form-login";
    }

}