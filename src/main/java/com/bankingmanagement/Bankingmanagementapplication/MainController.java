package com.bankingmanagement.Bankingmanagementapplication;

import MyModel.Account;
import MyModel.Admin;
import MyModel.Customer;
import Repository.Customer_Repository;
import Services.Admin_Services;
import Services.Customer_Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String adminlogin(@RequestParam String adminemailid, @RequestParam String adminpassword, Model model, HttpSession session) {
        model.addAttribute("error", "");
        List<Admin> adminlist=admin_services.findAlladmins();
        String adminid=null;
        Admin currentadmin=null;
        for (Admin admin : adminlist) {
            if ((admin.getAdmin_emailid().equals(adminemailid)) && (admin.getAdmin_password().equals(adminpassword))) {
                adminid=String.valueOf(admin.getAdmin_id());
                currentadmin=admin;
            }
        }
        if (adminid!=null) {
            session.setAttribute("adminid", adminid);
            session.setAttribute("currentadmin", currentadmin);
            return "redirect:/admindashboard";

        }
        else {
            model.addAttribute("error", "Invalid email or password");
            return "admin_login";
        }

    }
    @RequestMapping("/admindashboard")
    public String admindashboard(HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                return "admindashboard";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }

    @RequestMapping("/adminlogout")
    public String adminlogout(HttpSession session) {
        session.invalidate();
        return "redirect:/adminlogin";
    }

    @RequestMapping("/adminnewaccount")
    public String adminnewaccount(HttpSession session, Model model) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                List<Customer> customers=customerService.findallcustomers();
                model.addAttribute("customers", customers);
                return "adminnewaccount";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch(NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @GetMapping("/adminnewaccount/{id}")
    public String updaterecode(@PathVariable Integer id, Model model) {
        Customer customer = customerService.findrecodebyid(id);
        Account account=customerService.findaccountrecodebyid(id);
        model.addAttribute("account", account);
        model.addAttribute("customer", customer);
        return "adminnewaccountdetail";
    }




//    customer register submit
    @PostMapping("/registeraccount")
    public String registeraccount(Model model, @RequestParam String txtfname, @RequestParam String txtlname, @RequestParam String txtemail , @RequestParam String txtpassword, @RequestParam String txtmobilenumber , @RequestParam String txtaddress, @RequestParam String txtdob, @RequestParam String txtpostcode, @RequestParam String txtactype , @RequestParam String txtcountry) {

        customerService.customer_register(txtfname, txtlname,txtemail, txtpassword, txtmobilenumber, txtdob, txtaddress, txtpostcode, txtcountry);
        Customer customer = new Customer();
        customer = customerService.findrecodebyemail(txtemail);
        String account_number= String.valueOf((Integer.parseInt("1000000000") + Integer.parseInt(String.valueOf(customer.getCustomer_id()))));
        customerService.add_account(customer.getCustomer_id(), account_number, txtactype, "0", "Pending");
        return "redirect:/login";
    }

}
