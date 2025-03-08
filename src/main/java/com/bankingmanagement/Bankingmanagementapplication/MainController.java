package com.bankingmanagement.Bankingmanagementapplication;

import MyModel.Admin;
import MyModel.Customer;
import Repository.Customer_Repository;
import Services.Admin_Services;
import Services.Customer_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private Customer_Service customerService;
    @Autowired
    private Admin_Services admin_services;

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

    @RequestMapping("/adminlogin")
    public String admin() {
        return "admin_login";
    }

//    admin login
    @PostMapping("/adminlogin")
    public String adminlogin(@RequestParam String adminemailid, @RequestParam String adminpassword, Model model) {
        model.addAttribute("error", "");
        List<Admin> adminlist=admin_services.findAlladmins();
        String adminid=null;
        for (Admin admin : adminlist) {
            if ((admin.getAdmin_emailid().equals(adminemailid)) && (admin.getAdmin_password().equals(adminpassword))) {
                adminid=String.valueOf(admin.getAdmin_id());
            }
        }
        if (adminid!=null) {

            return "redirect:/admindashboard";

        }
        else {
            model.addAttribute("error", "Invalid email or password");
            return "admin_login";
        }

    }
    @RequestMapping("/admindashboard")
    public String admindashboard() {

        return "admindashboard";
    }



//    customer register submit
    @PostMapping("/registeraccount")
    public String registeraccount(Model model, @RequestParam String txtfname, @RequestParam String txtlname, @RequestParam String txtemail , @RequestParam String txtpassword, @RequestParam String txtmobilenumber , @RequestParam String txtaddress, @RequestParam String txtdob, @RequestParam String txtpostcode, @RequestParam String txtactype , @RequestParam String txtcountry) {

        customerService.customer_register(txtfname, txtlname,txtemail, txtpassword, txtmobilenumber, txtaddress, txtdob, txtpostcode, txtcountry);
        Customer customer = new Customer();
        customer = customerService.findrecodebyemail(txtemail);
        String account_number= String.valueOf((Integer.parseInt("1000000000") + Integer.parseInt(String.valueOf(customer.getCustomer_id()))));
        customerService.add_account(customer.getCustomer_id(), account_number, txtactype, "0", "Pending");
        return "redirect:/login";
    }

}
