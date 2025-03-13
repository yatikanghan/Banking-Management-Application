package com.bankingmanagement.Bankingmanagementapplication;

import MyModel.*;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private Customer_Service customerService;
    @Autowired
    private Admin_Services admin_services;



//
    @RequestMapping("/admindebit")
    public String admindebit(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                return "admindebit";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }





    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
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

    // admin debit
    @PostMapping("/admindebitamount")
    public String admindebit(Model model, @RequestParam String txtaccountnumber, @RequestParam String txtamount, @RequestParam String txtremark) {

        return "admindebit";
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
//    admin profile
    @RequestMapping("/adminprofile")
    public String adminprofile(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                model.addAttribute("admin", admin_services.getAdminById(Integer.parseInt(adminid)));
                return "adminprofile";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

//    admin profile save
    @PostMapping("/adminprofilesave")
    public String adminprofilesave(Model model, HttpSession session, @RequestParam String txtadminid, @RequestParam String txtadminname, @RequestParam String txtadminrole, @RequestParam String txtadminstatus ) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                admin_services.updateadmin(txtadminid, txtadminname, txtadminrole, txtadminstatus);
                return "redirect:/adminprofile";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch(NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }



    @RequestMapping("/adminnewaccount")
    public String adminnewaccount(HttpSession session, Model model) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                List<Account> allaccountlist=customerService.findallaccounts();
                List<Customer> customers=new ArrayList<>();
                for (Account account : allaccountlist) {
                    if (account.getAccount_status().equals("Pending")) {
                        customers.add(customerService.findrecodebyid(account.getCustomer_id()));
                    }
                }

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
    public String updaterecode(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Customer customer = customerService.findrecodebyid(id);
                Account account=customerService.findaccountrecodebyid(id);
                model.addAttribute("account", account);
                model.addAttribute("customer", customer);
                return "adminnewaccountdetail";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }
//
@PostMapping("/adminacconfirm")
public String adminacconfirm(
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
        @RequestParam("txtacstatus") String txtacstatus,
        Model model, HttpSession session) {

    customerService.updatecustomer(String.valueOf(txtcustomer_id), txtcustomer_firstname, txtlname, txtemail, txtpassword, txtmobilenumber, txtdob, txtaddress, txtpostcode, txtcountry);
    customerService.updateaccount(String.valueOf(txtaccountid), txtactype, String.valueOf(txtbalance), txtacstatus);

    return "admindashboard";
}


//manage admin account
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
    //    admin support
    @RequestMapping("/adminsupport")
    public String adminsupport(Model model, HttpSession session) {
        List<Support> allsuportlist=admin_services.findAllSupport();
        model.addAttribute("allsuportlist", allsuportlist);

        return "adminsupport";
    }

    @GetMapping("/adminsupport/{id}")
    public String adminsupportdetail(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Support support = admin_services.findSupportRecodebyid(id);
                model.addAttribute("support", support);
                Customer customer = customerService.findrecodebyid(support.getCustomer_id());
                model.addAttribute("customer", customer);
                Account account=customerService.findaccountrecodebycustomerid(support.getCustomer_id());
                model.addAttribute("account", account);

                return "adminsupportdetail";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }

    @GetMapping("/adminsupportdelete/{id}")
    public String adminsupportdetaildelete(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                admin_services.deletesupportrecodebyid(id);

                return "redirect:/adminsupport";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }
    @GetMapping("/adminsupportresolve/{id}")
    public String adminsupportdetailresolve(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                admin_services.solvesupportrecodebyid(id);

                return "redirect:/adminsupport";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

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

//    customer login submit
    @PostMapping("/customerlogin")
    public String customerlogin(Model model, HttpSession session, @RequestParam String customeremailid, @RequestParam String customerpassword) {
        model.addAttribute("errormsg", "");
        List<Customer> customerlist=customerService.findallcustomers();
        List<Account> accountlist=customerService.findallaccounts();
        Account currentaccount=null;
        String custid=null;
        Customer currentcustomer=null;
        for (Customer customer : customerlist) {
            if ((customer.getCustomer_emailid().equals(customeremailid)) && (customer.getCustomer_password().equals(customerpassword))) {
                custid=String.valueOf(customer.getCustomer_id());
                currentcustomer=customer;
            }
        }
        for (Account account : accountlist) {
            if (custid.equals(String.valueOf(account.getCustomer_id()))) {
                currentaccount=account;
            }
        }
        if (custid!=null) {
            if (currentaccount.getAccount_status().equals("Active")) {
                session.setAttribute("custid", custid);
                session.setAttribute("currentcustomer", currentcustomer);
                return "redirect:/customerdashboard";
            }
            else{
                return "redirect:/customerpendingpage";

            }

        }
        else {
            model.addAttribute("errormsg", "Invalid email or password");
            return "customerlogin";
        }
    }

    @RequestMapping("/customerdashboard")
    public String customerdashboard(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Customer customer= (Customer) session.getAttribute("currentcustomer");
                model.addAttribute("customer", customer);
                model.addAttribute("customername", customer.getCustomer_firstname() + " " + customer.getCustomer_lastname());
                return "customerdashboard";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }

    @RequestMapping("/customerlogout")
    public String customerlogout(HttpSession session) {
//        session.removeAttribute("custid");
//        session.removeAttribute("currentcustomer");
        session.invalidate();
        return "redirect:/customerloginpage";
    }


    @RequestMapping("/customerpendingpage")
    public String customerpendingpage(Model model, HttpSession session) {
        return "customerpendingpage";
    }


    @RequestMapping("/customersupport")
    public String customersupport(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                List<Support> allsuportlist=customerService.findAllSupport();
                List<Support> mysupportlist=new ArrayList<>();
                for (Support support : allsuportlist) {
                    if (custid.equals(String.valueOf(support.getCustomer_id()))) {
                        mysupportlist.add(support);
                    }
                }
                model.addAttribute("supportlist", allsuportlist);
                return "customersupport";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }

    @RequestMapping("/customeraddsupport")
    public String customeraddsupport(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Customer customer= (Customer) session.getAttribute("currentcustomer");
                model.addAttribute("customer", customer);
                return "customeraddsupport";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }

    @PostMapping("/customeraddsupportinsert")
    public String customeraddsupport(Model model, HttpSession session, @RequestParam String supportsubjectname, @RequestParam String supportsubjectdesc) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Customer customer= (Customer) session.getAttribute("currentcustomer");
                model.addAttribute("customer", customer);
                List<Account> accountlist=customerService.findallaccounts();
                Account currentaccount=null;
                for (Account account : accountlist) {
                    if (custid.equals(String.valueOf(account.getCustomer_id()))) {
                        currentaccount=account;
                    }
                }
                model.addAttribute("account", currentaccount);
                model.addAttribute("customer", customer);

                customerService.addcustomersupport(customer.getCustomer_id(),currentaccount.getAccount_id(), supportsubjectname, supportsubjectdesc,"Pending");


                return "customersupport";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }
    @GetMapping("/customersupport/{id}")
    public String customersupportdetail(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {

                Support support = customerService.findSupportRecodebyid(id);
                model.addAttribute("support", support);
                Customer customer = customerService.findrecodebyid(support.getCustomer_id());
                model.addAttribute("customer", customer);
                Account account=customerService.findaccountrecodebycustomerid(support.getCustomer_id());
                model.addAttribute("account", account);
                return "customersupportdetail";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }

    }



//    profile
    @RequestMapping("/profile")
    public String profile(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Customer customer = customerService.findrecodebyid(Integer.parseInt(custid));
                model.addAttribute("customer", customer);
                List<Account> accountlist=customerService.findallaccounts();
                Account currentaccount=null;
                for (Account account : accountlist) {
                    if (custid.equals(String.valueOf(account.getCustomer_id()))) {
                        currentaccount=account;
                    }
                }
                model.addAttribute("account", currentaccount);
                return "customerprofile";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }


    @PostMapping("/customerprofilesave")
    public String customerprofilesave(
            @RequestParam("txtcustomer_id") int txtcustomer_id,
            @RequestParam("txtcustomer_firstname") String txtcustomer_firstname,
            @RequestParam("txtlname") String txtlname,
            @RequestParam("txtmobilenumber") String txtmobilenumber,
            @RequestParam("txtaddress") String txtaddress,
            @RequestParam("txtpostcode") String txtpostcode,
            @RequestParam("txtcountry") String txtcountry,
            Model model, HttpSession session) {

        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                customerService.updateprofile(String.valueOf(txtcustomer_id), txtcustomer_firstname, txtlname, txtmobilenumber, txtaddress, txtpostcode, txtcountry);

                return "redirect:/customerdashboard";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }


    }




}