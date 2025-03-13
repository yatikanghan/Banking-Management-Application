package com.bankingmanagement.Bankingmanagementapplication.Controller;

import MyModel.Customer;
import Services.Customer_Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private Customer_Service customerService;

    @GetMapping("/login")
    public String customerLoginPage() {
        return "customerlogin";
    }
    @RequestMapping("/customerloginpage")
    public String customerloginpage() {
        return "customerlogin";
    }
    @RequestMapping("/register")
    public String register() {
        return "/customerregister";
    }

    @PostMapping("/customerlogin")
    public String customerLogin(Model model, HttpSession session, @RequestParam String customeremailid, @RequestParam String customerpassword) {
        model.addAttribute("errormsg", "");
        List<Customer> customerlist = customerService.findallcustomers();
        String custid = null;
        Customer currentcustomer = null;
        for (Customer customer : customerlist) {
            if ((customer.getCustomer_emailid().equals(customeremailid)) && (customer.getCustomer_password().equals(customerpassword))) {
                custid = String.valueOf(customer.getCustomer_id());
                currentcustomer = customer;
            }
        }
        if (custid != null) {
            session.setAttribute("custid", custid);
            session.setAttribute("currentcustomer", currentcustomer);
            return "redirect:/customerdashboard";
        } else {
            model.addAttribute("errormsg", "Invalid email or password");
            return "customerlogin";
        }
    }


}
