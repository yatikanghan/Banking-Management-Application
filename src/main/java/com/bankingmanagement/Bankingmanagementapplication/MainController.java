package com.bankingmanagement.Bankingmanagementapplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "/customerlogin";
    }
    @RequestMapping("/register")
    public String register() {
        return "/customerregister";
    }
}
