package com.bankingmanagement.Bankingmanagementapplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/registeraccount")
    public String registeraccount(Model model, @RequestParam String txtfname, @RequestParam String txtlname, @RequestParam String txtemail , @RequestParam String txtpassword, @RequestParam String txtmobilenumber , @RequestParam String txtaddress, @RequestParam String txtdob, @RequestParam String txtpostcode, @RequestParam String txtactype , @RequestParam String txtcountry) {
        String substring= txtfname + " : " + txtlname + " : " + txtemail + " : " + txtpassword + " : " + txtmobilenumber + " : " + txtaddress + " : " + txtdob + " : " + txtpostcode + " : " + txtactype + " : " + txtcountry;
        return "redirect:/";
    }

}
