package com.bankingmanagement.Bankingmanagementapplication.Controller;

import MyModel.Account;
import MyModel.AccountDetailView;
import MyModel.Admin;
import MyModel.Customer;
import Services.Admin_Services;
import Services.Customer_Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    private Customer_Service customerService;
    @Autowired
    private Admin_Services admin_services;

    @GetMapping("/adminlogin")
    public String adminLogin() {
        return "adminlogin";
    }

    @PostMapping("/adminlogin")
    public String adminLogin(@RequestParam String adminemailid, @RequestParam String adminpassword, Model model, HttpSession session) {
        model.addAttribute("error", "");
        List<Admin> adminlist = admin_services.findAlladmins();
        String adminid = null;
        Admin currentadmin = null;
        for (Admin admin : adminlist) {
            if ((admin.getAdmin_emailid().equals(adminemailid)) && (admin.getAdmin_password().equals(adminpassword))) {
                adminid = String.valueOf(admin.getAdmin_id());
                currentadmin = admin;
            }
        }
        if (adminid != null) {
            session.setAttribute("adminid", adminid);
            session.setAttribute("currentadmin", currentadmin);
            return "redirect:/admindashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "adminlogin";
        }
    }

    @GetMapping("/admindashboard")
    public String adminDashboard(HttpSession session) {
        try {
            String adminid = session.getAttribute("adminid").toString();
            if (adminid != null) {
                return "admindashboard";
            } else {
                return "redirect:/adminlogin";
            }
        } catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @GetMapping("/adminlogout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/adminlogin";
    }

    @GetMapping("/adminnewaccount")
    public String adminNewAccount(HttpSession session, Model model) {
        try {
            String adminid = session.getAttribute("adminid").toString();
            if (adminid != null) {
                List<Account> allaccountlist = customerService.findallaccounts();
                List<Customer> customers = new ArrayList<>();
                for (Account account : allaccountlist) {
                    if (account.getAccount_status().equals("Pending")) {
                        customers.add(customerService.findrecodebyid(account.getCustomer_id()));
                    }
                }
                model.addAttribute("customers", customers);
                return "adminnewaccount";
            } else {
                return "redirect:/adminlogin";
            }
        } catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @GetMapping("/adminnewaccount/{id}")
    public String updateRecord(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid = session.getAttribute("adminid").toString();
            if (adminid != null) {
                Customer customer = customerService.findrecodebyid(id);
                Account account = customerService.findaccountrecodebyid(id);
                model.addAttribute("account", account);
                model.addAttribute("customer", customer);
                return "adminnewaccountdetail";
            } else {
                return "redirect:/adminlogin";
            }
        } catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @PostMapping("/adminacconfirm")
    public String adminAcConfirm(
            @RequestParam("txtcustomer_id") int txtcustomer_id,
            @RequestParam("txtcustomer_firstname") String txtcustomer_firstname,
            @RequestParam("txtlname") String txtlname,
            @RequestParam("txtemail") String txtemail,
            @RequestParam("txtpassword") String txtpassword,
            @RequestParam("txtmobilenumber") String txtmobilenumber,
            @RequestParam("txtaddress") String txtaddress,
            @RequestParam("txtdob") String txtdob,
            @RequestParam("txtpostcode") String txtpostcode,
            @RequestParam("txtcountry") String txtcountry,
            @RequestParam("txtaccount_id") int txtaccountid,
            @RequestParam("txtactype") String txtactype,
            @RequestParam("txtbalance") double txtbalance,
            @RequestParam("txtacstatus") String txtacstatus) {

        customerService.updatecustomer(String.valueOf(txtcustomer_id), txtcustomer_firstname, txtlname, txtemail, txtpassword, txtmobilenumber, txtdob, txtaddress, txtpostcode, txtcountry);
        customerService.updateaccount(String.valueOf(txtaccountid), txtactype, String.valueOf(txtbalance), txtacstatus);

        return "admindashboard";
    }
    @RequestMapping("/adminmanageaccount")
    public String adminmanageaccount(HttpSession session, Model model) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                List<Customer> customers=customerService.findallcustomers();
                List<AccountDetailView> accountdetailviewslist=new ArrayList<AccountDetailView>();
                for (Customer customer : customers) {
                    Account account=customerService.findaccountrecodebyid(customer.getCustomer_id());
                    accountdetailviewslist.add(new AccountDetailView(customer.getCustomer_id(), customer, account.getAccount_id(), account.getAccount_number(), account.getAccount_type(), account.getAccount_balance(), account.getAccount_status(), account.getAccount_created_at()));
                }

                model.addAttribute("accountdetailviewslist", accountdetailviewslist);
                return "adminmanageaccount";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch(NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @GetMapping("/adminmanageaccount/{id}")
    public String adminmanageaccountdetail(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Customer customer = customerService.findrecodebyid(id);
                Account account=customerService.findaccountrecodebyid(id);
                model.addAttribute("account", account);
                model.addAttribute("customer", customer);
                return "adminmanageaccountdetail";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }
}
