/*package com.bankingmanagement.Bankingmanagementapplication.Controller;

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
    @GetMapping("/customerdashboard")
    public String customerDashboard(HttpSession session) {
        try {
            String custid = session.getAttribute("custid").toString();
            if (custid != null) {
                return "customerdashboard";
            } else {
                return "redirect:/customerlogin";
            }
        } catch (NullPointerException e) {
            return "redirect:/customerlogin";
        }
    }

    @PostMapping("/register")
    public String registerAccount(Model model, @RequestParam String txtfname, @RequestParam String txtlname, @RequestParam String txtemail, @RequestParam String txtpassword, @RequestParam String txtmobilenumber, @RequestParam String txtaddress, @RequestParam String txtdob, @RequestParam String txtpostcode, @RequestParam String txtactype, @RequestParam String txtcountry) {
        customerService.customer_register(txtfname, txtlname, txtemail, txtpassword, txtmobilenumber, txtdob, txtaddress, txtpostcode, txtcountry);
        Customer customer = customerService.findrecodebyemail(txtemail);
        String account_number = String.valueOf((Integer.parseInt("1000000000") + Integer.parseInt(String.valueOf(customer.getCustomer_id()))));
        customerService.add_account(customer.getCustomer_id(), account_number, txtactype, "0", "Pending");
        return "redirect:/customerlogin";
    }

}*/
