package com.bankingmanagement.Bankingmanagementapplication;

import MyModel.*;
import Repository.Customer_Repository;
import Repository.TodoRepository;
import Services.Admin_Services;
import Services.Customer_Service;
import Services.Todo_Services;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private Customer_Service customerService;
    @Autowired
    private Admin_Services admin_services;

    @Autowired
    private Todo_Services todoServices;
    private TodoRepository todoRepository;


    //
    @RequestMapping("/admindebit")
    public String admindebit(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if ((currentadmin.getAdmin_role().equals("Admin")) || (currentadmin.getAdmin_role().equals("Casher")) ) {

                    return "admindebit";
                }
                else {
                    return "redirect:/accessdeniedpage";
                }

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
        return "adminlogin";
    }


//    admin todo

    @RequestMapping("/admintodolist")
    public String tolist(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                List<Todo> alltodos=todoServices.getAllTodos();
                List<Todo> mytodos=new ArrayList<>();
                for(Todo thistodo : alltodos){
                    if (thistodo.getTodo_admin_id().equals(String.valueOf(currentadmin.getAdmin_id()))){
                        mytodos.add(thistodo);
                    }
                }
                model.addAttribute("mytodos", mytodos);

                return "admintodolist";

            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @RequestMapping("/addtask")
    public String addtask(HttpSession session, Model model) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                return "addtodotask";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }


    @PostMapping("/addtodobtn")
    public String addtodobtn(@RequestParam String txttodotitle, @RequestParam String txttododesc, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                String strtodoid=UUID.randomUUID().toString();
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateTime = dateFormat.format(currentDate);

                Todo newtodo=new Todo(strtodoid, txttodotitle,txttododesc,currentDateTime, String.valueOf(currentadmin.getAdmin_id()));
                todoServices.createTodo(newtodo);
                return "redirect:/admintodolist";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @GetMapping("/deletetodo/{id}")
    public String deletetodo(@PathVariable String id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                todoServices.deleteTodo(id);
                return "redirect:/admintodolist";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }

    @GetMapping("/updatetodo/{id}")
    public String updatetodo(@PathVariable String id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Todo thistodo=todoServices.getTodoById(id);
                model.addAttribute("thistodo",thistodo);
                return "updatetodo";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }
    @PostMapping("/updatetodobtn")
    public String updatetodobtn(@RequestParam String txttodoid, @RequestParam String txttodotitle, @RequestParam String txttododesc, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Todo thistodo=todoServices.getTodoById(txttodoid);
                thistodo.setTodo_title(txttodotitle);
                thistodo.setTodo_desc(txttododesc);
                todoServices.updateTodo(txttodoid, thistodo);
                return "redirect:/admintodolist";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }


    //    admin login
    @PostMapping("/adminlogin")
    public String adminlogin(@RequestParam String adminemailid, @RequestParam String adminpassword, Model model, HttpSession session) {
        model.addAttribute("error", "");
        List<Admin> adminlist=admin_services.findAlladmins();
        String adminid=null;
        Admin currentadmin=null;
        String adminstatus=null;
        for (Admin admin : adminlist) {
            if ((admin.getAdmin_emailid().equals(adminemailid)) && (admin.getAdmin_password().equals(adminpassword))) {
                adminid=String.valueOf(admin.getAdmin_id());
                currentadmin=admin;
                if (admin.getAdmin_status().equals("Active")) {

                    adminstatus="Active";
                }
                else {
                    adminstatus="Deactive";
                }
            }
        }
        if (adminid!=null) {
            if (adminstatus.equals("Active")) {

                session.setAttribute("adminid", adminid);
                session.setAttribute("currentadmin", currentadmin);
                return "redirect:/admindashboard";
            }
            else {
                return "redirect:/deactiveadmin";
            }

        }
        else {
            model.addAttribute("error", "Invalid email or password");
            return "adminlogin";
        }

    }

    @RequestMapping("/deactiveadmin")
    public String deactiveadmin() {
        return "deactiveadmin";
    }

    // admin debit
    @PostMapping("/admindebitamount")
    public String admindebitamount(Model model, HttpSession session, @RequestParam String txtaccountnumber, @RequestParam String txtamount, @RequestParam String txtremark) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if ((currentadmin.getAdmin_role().equals("Admin")) || (currentadmin.getAdmin_role().equals("Casher")) ) {
                    try {
                        Account raccount=customerService.findaccountrecodebyacnumber(txtaccountnumber);
                        if (raccount!=null) {
                            model.addAttribute("acnotfound", "");
                            customerService.admindepositmoney(raccount, String.valueOf(raccount.getAccount_id()), Double.parseDouble(txtamount), "Deposit", txtremark);
                            return "redirect:/admindashboard";
                        }
                        else {

                            model.addAttribute("acnotfound", "Account not found");
                            return "admindebit";
                        }

                    }
                    catch (Exception e) {
                        return "redirect:/customerloginpage";
                    }
                                  }
                else {
                    return "redirect:/accessdeniedpage";
                }



            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

//admin withdraw
    @RequestMapping("/adminwithdraw")
    public String adminwithdraw(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if ((currentadmin.getAdmin_role().equals("Admin")) || (currentadmin.getAdmin_role().equals("Casher")) ) {

                    return "adminwithdraw";                }
                else {
                    return "redirect:/accessdeniedpage";
                }
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }



    // admin debit
    @PostMapping("/adminwithdrawamount")
    public String adminwithdrawamount(Model model, HttpSession session, @RequestParam String txtaccountnumber, @RequestParam String txtamount, @RequestParam String txtremark) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                try {
                    Account raccount=customerService.findaccountrecodebyacnumber(txtaccountnumber);
                    if (raccount!=null) {
                        model.addAttribute("acnotfound", "");
                        customerService.adminwithdrawmoney(raccount, String.valueOf(raccount.getAccount_id()), Double.parseDouble(txtamount), "Withdraw", txtremark);
                    }
                    else {

                        model.addAttribute("acnotfound", "Account not found");
                        return "admindebit";
                    }

                }
                catch (Exception e) {
                    return "redirect:/customerloginpage";
                }
                return "redirect:/admindashboard";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @RequestMapping("/admintransfer")
    public String admintransfer(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if ((currentadmin.getAdmin_role().equals("Admin")) || (currentadmin.getAdmin_role().equals("Casher")) ) {

                    return "admintransfer";
                }
                else {
                    return "redirect:/accessdeniedpage";
                }
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

//    admin transfer money
    @PostMapping("/admintrasfermoney")
    public String admintrasfermoney(Model model, HttpSession session, @RequestParam String txtsenderaccount, @RequestParam String txtreceiveraccount, @RequestParam String txtamount, @RequestParam String txtremark) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                try {
                    Account senderaccount=customerService.findaccountrecodebyacnumber(txtsenderaccount);
                    Account receiveraccount=customerService.findaccountrecodebyacnumber(txtreceiveraccount);
                    if (senderaccount!=null) {
                        if (receiveraccount!=null) {
                            model.addAttribute("acnotfound", "");
                            int st=customerService.admintransfermoney(senderaccount, receiveraccount, String.valueOf(senderaccount.getAccount_id()),String.valueOf(receiveraccount.getAccount_id()), Double.parseDouble(txtamount), "Transfer", txtremark);
                            if (st==0) {
                                model.addAttribute("balanceerr", "Insufficient Balance in sender account");
                                return "admintransfer";
                            }
                        }
                        else {

                            model.addAttribute("receiveracnotfound", "Account not found");
                            return "admintransfer";
                        }
                    }
                    else {

                        model.addAttribute("senderacnotfound", "Account not found");
                        return "admintransfer";
                    }

                }
                catch (Exception e) {
                    return "redirect:/customerloginpage";
                }
                return "redirect:/admindashboard";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @RequestMapping("/admindashboard")
    public String admindashboard(HttpSession session, Model model) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                List<Account> allaccounts=customerService.findallaccounts();
                List<Customer> allcustomer=customerService.findallcustomers();
                List<Admin> alladmin=admin_services.findAlladmins();
                List<Support> allsupport=customerService.findPendingSupport();
                List<FixedDeposit> allfixeddeposit=customerService.findallfixeddeposit();
                List<FixedDeposit> activefd=new ArrayList<>();
                for(FixedDeposit fd:allfixeddeposit) {
                    if (fd.getStatus().equals("Active")){
                        activefd.add(fd);
                    }
                }
                model.addAttribute("activefd",activefd);
                model.addAttribute("allaccounts", allaccounts);
                model.addAttribute("allcustomer", allcustomer);
                model.addAttribute("alladmin", alladmin);
                model.addAttribute("allsupport", allsupport);



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

    @PostMapping("/admindetailsave")
    public String admindetailsave(Model model, HttpSession session, @RequestParam String txtadminid, @RequestParam String txtadminname, @RequestParam String txtadminrole, @RequestParam String txtadminstatus ) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                admin_services.updateadmin(txtadminid, txtadminname, txtadminrole, txtadminstatus);
                return "redirect:/staff";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch(NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }


    @PostMapping("/adminnewadminsave")
    public String adminnewadminsave(Model model, HttpSession session,@RequestParam String txtadminemail,@RequestParam String txtadminpassword, @RequestParam String txtadminname, @RequestParam String txtadminrole, @RequestParam String txtadminstatus ) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                admin_services.adminaddstafffunction(txtadminemail,txtadminpassword, txtadminname, txtadminrole, txtadminstatus);
                return "redirect:/staff";
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

                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if (currentadmin.getAdmin_role().equals("Admin")) {
                    List<Account> allaccountlist=customerService.findallaccounts();
                    List<Customer> customers=new ArrayList<>();
                    for (Account account : allaccountlist) {
                        if (account.getAccount_status().equals("Pending")) {
                            customers.add(customerService.findrecodebyid(account.getCustomer_id()));
                        }
                    }

                    model.addAttribute("customers", customers);
                    return "adminnewaccount";
                }
                else {
                    return "redirect:/accessdeniedpage";
                }


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

    return "redirect:/adminmanageaccount";
}

    @PostMapping("/adminnewacconfirm")
    public String adminnewacconfirm(
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

        return "/adminnewaccount";
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
                    if (!(account.getAccount_status().equals("Pending"))) {

                        accountdetailviewslist.add(new AccountDetailView(customer.getCustomer_id(), customer, account.getAccount_id(), account.getAccount_number(), account.getAccount_type(), account.getAccount_balance(), account.getAccount_status(), account.getAccount_created_at()));
                    }
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
    public String adminsupport(HttpSession session, Model model) {
        try{
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin= admin_services.getAdminById(Integer.parseInt(adminid));
                if((currentadmin.getAdmin_role().equals("admin"))||(currentadmin.getAdmin_role().equals("support")))
                {
                    List<Support> allsupportlist=admin_services.findAllSupport();
                    model.addAttribute("supportlist", allsupportlist);

                    return "adminsupport";
                }
                else {
                    return "redirect:/acessdeniedpage";
                }

            }
            else{
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
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if ((currentadmin.getAdmin_role().equals("Admin")) || currentadmin.getAdmin_role().equals("Support")) {
                    admin_services.deletesupportrecodebyid(id);

                    return "redirect:/adminsupport";
                }
                else {
                    return "redirect:/accessdeniedpage";
                }
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
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if ((currentadmin.getAdmin_role().equals("Admin")) || currentadmin.getAdmin_role().equals("Support")) {
                    admin_services.solvesupportrecodebyid(id, Integer.parseInt(adminid));

                    return "redirect:/adminsupport";
                }
                else {
                    return "redirect:/accessdeniedpage";
                }


            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }
    /*@RequestMapping("/admindebit")
    public String admindebit(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if ((currentadmin.getAdmin_role().equals("Admin")) || (currentadmin.getAdmin_role().equals("Casher")) ) {

                    return "admindebit";
                }
                else {
                    return "redirect:/accessdeniedpage";
                }

            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }*/


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

        if (custid!=null) {
            for (Account account : accountlist) {
                if (custid.equals(String.valueOf(account.getCustomer_id()))) {
                    currentaccount=account;
                }
            }
            if (currentaccount.getAccount_status().equals("Active")) {
                session.setAttribute("custid", custid);
                session.setAttribute("currentcustomer", currentcustomer);
                return "redirect:/customerdashboard";
            } else if (currentaccount.getAccount_status().equals("Pending")) {

                return "redirect:/customerpendingpage";
            } else if (currentaccount.getAccount_status().equals("Closed")) {

                return "redirect:/customerclosedpage";
            } else{

                return "redirect:/customerdeactivepage";
            }

        }
        else {
            model.addAttribute("errormsg", "Invalid email or password");
            return "customerlogin";
        }
    }

    @RequestMapping("/customerclosedpage")
    public String customerclosedpage(Model model, HttpSession session) {
        return "customerclosedpage";
    }

    @RequestMapping("/customerdeactivepage")
    public String customerdeactivepage(Model model, HttpSession session) {
        return "customerdeactivepage";
    }



    @RequestMapping("/customerdashboard")
    public String customerdashboard(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Customer customer= customerService.findrecodebyid(Integer.parseInt(custid));
                Account account= customerService.findaccountrecodebycustomerid(Integer.parseInt(custid));
                model.addAttribute("account", account);
                model.addAttribute("customer", customer);
                model.addAttribute("acbalance", "€ "+account.getAccount_balance());
                model.addAttribute("customername", customer.getCustomer_firstname() + " " + customer.getCustomer_lastname());

                List<FixedDeposit> allfds=customerService.findallfixeddeposit();
                List<FixedDeposit> myfds=new ArrayList<>();
                for (FixedDeposit fds : allfds) {
                    if (fds.getAccountId()==account.getAccount_id()) {
                        if (fds.getStatus().equals("Active")){
                            myfds.add(fds);
                        }
                    }
                }
                model.addAttribute("myfds",myfds);
                List<Transaction> alltransaction=customerService.findallTransaction();
                List<Transaction> mytransaction= new ArrayList<>();
                for (Transaction transaction : alltransaction) {
                    if ((transaction.getReceiver_account().equals(String.valueOf(account.getAccount_id()))) || transaction.getSender_account().equals(String.valueOf(account.getAccount_id()))) {
                        if (!transaction.getSender_account().equals("NA")){
                            Account acc=customerService.findaccountrecodebyid(Integer.parseInt(transaction.getSender_account()));
                            transaction.setSender_account(acc.getAccount_number());
                        }
                        if (!transaction.getReceiver_account().equals("NA")){
                            Account acc=customerService.findaccountrecodebyid(Integer.parseInt(transaction.getReceiver_account()));
                            transaction.setReceiver_account(acc.getAccount_number());
                        }
                        mytransaction.add(transaction);
                    }
                }
                model.addAttribute("mytransaction", mytransaction);


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
                model.addAttribute("supportlist", mysupportlist);
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


                return "redirect:/customersupport";
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

//    customer transfer

    @RequestMapping("/customertrasfer")
    public String customertrasfer(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                return "customertransfer";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }

    //    admin transfer money
    @PostMapping("/customertransfermoney")
    public String customertransfermoney(Model model, HttpSession session, @RequestParam String txtreceiveraccount, @RequestParam String txtamount, @RequestParam String txtremark) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                try {
                    Account senderaccount=customerService.findaccountrecodebycustomerid(Integer.parseInt(custid));
                    Account receiveraccount=customerService.findaccountrecodebyacnumber(txtreceiveraccount);
                    if (receiveraccount!=null) {
                        model.addAttribute("acnotfound", "");
                        int st=customerService.admintransfermoney(senderaccount, receiveraccount, String.valueOf(senderaccount.getAccount_id()),String.valueOf(receiveraccount.getAccount_id()), Double.parseDouble(txtamount), "Transfer", txtremark);
                        if (st==0) {
                            model.addAttribute("balanceerr", "Insufficient Balance in sender account");
                            return "customertransfer";
                        }
                        else {
                            return "redirect:/customerdashboard";
                        }
                    }
                    else {

                        model.addAttribute("receiveracnotfound", "Account not found");
                        return "admintransfer";
                    }

                }
                catch (Exception e) {
                    return "redirect:/customerloginpage";
                }
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }



//    staff

    @RequestMapping("/staff")
    public String staff(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                Admin currentadmin=admin_services.getAdminById(Integer.parseInt(adminid));
                if (currentadmin.getAdmin_role().equals("Admin")) {
                    List<Admin> adminlist=admin_services.findAlladmins();
                    model.addAttribute("adminlist", adminlist);
                    return "staff";
                }
                else {
                    return "redirect:/accessdeniedpage";
                }
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }


    @RequestMapping("/addstaff")
    public String addstaff(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {

                return "addstaff";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }

    @GetMapping("/staff/{id}")
    public String staffdetail(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                model.addAttribute("admin", admin_services.getAdminById(Integer.parseInt(adminid)));
//                return "adminprofile";
                Admin admin=admin_services.getAdminById(id);
                model.addAttribute("admin", admin);
                return "staffdetail";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }



    @RequestMapping("/registeraccount")
    public class CustomerRegistrationController {

        @PostMapping
        public String registerCustomer(
                @Valid @ModelAttribute("customer") Customer customerDto,
                BindingResult result,
                Model model) {

            if (result.hasErrors()) {
                model.addAttribute("errors", result.getAllErrors());
                model.addAttribute("errors", result.getAllErrors());
                return "customerregister"; // Return back to form with errors
            }

            // Proceed with saving the customer (service layer logic)
            model.addAttribute("message", "Registration successful!");
            return "success";
        }
    }


//    transaction history
    @RequestMapping("/customertransactionhistory")
    public String customertransactionhistory(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Customer customer= customerService.findrecodebyid(Integer.parseInt(custid));
                Account account= customerService.findaccountrecodebycustomerid(Integer.parseInt(custid));
                model.addAttribute("account", account);
                model.addAttribute("customer", customer);
                model.addAttribute("acbalance", "€ "+account.getAccount_balance());
                model.addAttribute("customername", customer.getCustomer_firstname() + " " + customer.getCustomer_lastname());

                List<Transaction> alltransaction=customerService.findallTransaction();
                List<Transaction> mytransaction= new ArrayList<>();
                for (Transaction transaction : alltransaction) {
                    if ((transaction.getReceiver_account().equals(String.valueOf(account.getAccount_id()))) || transaction.getSender_account().equals(String.valueOf(account.getAccount_id()))) {

                        if (!transaction.getSender_account().equals("NA")){
                            Account acc=customerService.findaccountrecodebyid(Integer.parseInt(transaction.getSender_account()));
                            transaction.setSender_account(acc.getAccount_number());
                        }
                        if (!transaction.getReceiver_account().equals("NA")){
                            Account acc=customerService.findaccountrecodebyid(Integer.parseInt(transaction.getReceiver_account()));
                            transaction.setReceiver_account(acc.getAccount_number());
                        }
                        mytransaction.add(transaction);
                    }
                }
                model.addAttribute("mytransaction", mytransaction);


                return "customertransactionhistory";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }

    }

//    customer fd

    @RequestMapping("/customerfixeddeposit")
    public String customerfixeddeposit(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Account myaccount=customerService.findaccountrecodebycustomerid(Integer.parseInt(custid));
                List<FixedDeposit> allfds=customerService.findallfixeddeposit();
                List<FixedDeposit> myfds= new ArrayList<>();
                for (FixedDeposit fd : allfds){
                    if (myaccount.getAccount_id()==fd.getAccountId()){
                        myfds.add(fd);
                    }
                }
                model.addAttribute("myfds",myfds);
                return "customerfixeddeposit";

            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }

    @RequestMapping("/adminfixdeposit")
    public String adminfixeddeposit(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                List<FixedDeposit> allfds=customerService.findallfixeddeposit();
                model.addAttribute("allfds",allfds);
                return "adminfixdeposit";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }
    @GetMapping("/adminfixdeposit/{id}")
    public String adminfixdepositdetail(@PathVariable Integer id, Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                FixedDeposit fd=customerService.findfixeddepositbyid(id);
                Account thisaccount=customerService.findaccountrecodebyid(fd.getAccountId());
                model.addAttribute("thisaccount", thisaccount);
                model.addAttribute("fd", fd);
                return "/adminfddetail";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }

    }

    @PostMapping("/adminfdclosed")
    public String customertransfermoney(Model model, @RequestParam String txtfdid, HttpSession session, @RequestParam String txtpamount) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                FixedDeposit fd=customerService.findfixeddepositbyid(Integer.parseInt(txtfdid));
                Account thisaccount=customerService.findaccountrecodebyid(fd.getAccountId());
                customerService.closedfixeddeposit(fd, thisaccount);
                return "redirect:/adminfixdeposit";

            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }


    @RequestMapping("/accessdeniedpage")
    public String accessdeniedpage(Model model, HttpSession session) {
        try {
            String adminid=session.getAttribute("adminid").toString();
            if (adminid!=null) {
                return "accessdenied";
            }else {
                return "redirect:/adminlogin";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/adminlogin";
        }
    }


//    fd
    @RequestMapping("/createnewfd")
    public String createnewfd(Model model, HttpSession session) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {

                return "customercreatenewfd";

            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }


    @PostMapping("/createnewfdbtn")
    public String createnewfdbtn(Model model, HttpSession session, @RequestParam String txtfdamount, @RequestParam String txttenure) {
        try {
            String custid=session.getAttribute("custid").toString();
            if (custid!=null) {
                Customer mycustomer=customerService.findrecodebyid(Integer.parseInt(custid));
                Account myaccount=customerService.findaccountrecodebycustomerid(Integer.parseInt(custid));
                if (Double.parseDouble(myaccount.getAccount_balance()) < Double.parseDouble(txtfdamount)) {
                    model.addAttribute("balanceerror", "Insufficient balance");
                    return "customercreatenewfd";
                }
                customerService.adminwithdrawmoney(myaccount, String.valueOf(myaccount.getAccount_id()), Double.parseDouble(txtfdamount), "Withdraw", "Create New Fixed Deposit");
                customerService.addnewfixeddeposit(myaccount.getAccount_id(), Double.parseDouble(txtfdamount),Double.parseDouble("6"), Integer.parseInt(txttenure),"Active");
                return "redirect:/customerfixeddeposit";
            }else {
                return "redirect:/customerloginpage";
            }
        }
        catch (NullPointerException e) {
            return "redirect:/customerloginpage";
        }
    }

}