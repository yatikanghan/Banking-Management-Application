package Services;

import MyModel.Account;
import MyModel.Admin;
import MyModel.Customer;
import MyModel.Support;
import Repository.Account_Repository;
import Repository.Customer_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class Customer_Service {
    @Autowired
    JdbcTemplate template;


    public int customer_register(String customer_firstname, String customer_lastname, String customer_emailid, String customer_password, String customer_mobile, String customer_dob, String customer_address, String customer_postcode, String customer_country){
        String sql="INSERT INTO customers (customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country) values(?,?,?,?,?,?,?,?,?)";
        return template.update(sql, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country);
//        Customer customer=new Customer(customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country);
//        customerRepository.save(customer);
    }
    public int add_account(int customer_id, String account_number, String account_type, String account_balance, String account_status){
        String sql="INSERT INTO account (customer_id, account_number, account_type, account_balance, account_status) values(?,?,?,?,?)";
        return template.update(sql, customer_id, account_number, account_type, account_balance, account_status);
//        Account account =new Account(customer_id, account_number, account_type, account_balance, account_status);
//        accountRepository.save(account);
    }

    public List<Customer> findallcustomers() {
        String sql = "SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country from customers";
        RowMapper<Customer> thisrm = new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                Customer customers = new Customer(resultSet.getInt("customer_id"),
                        resultSet.getString("customer_firstname"),
                        resultSet.getString("customer_lastname"),
                        resultSet.getString("customer_emailid"),
                        resultSet.getString("customer_password"),
                        resultSet.getString("customer_mobile"),
                        resultSet.getString("customer_dob"),
                        resultSet.getString("customer_address"),
                        resultSet.getString("customer_postcode"),
                        resultSet.getString("customer_country"));


                return customers;
            }
        };
        return template.query(sql, thisrm);
    }


    public Customer findrecodebyid(int customer_id) {

        String sql = "SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country FROM customers WHERE customer_id = ?";

        return template.queryForObject(sql, new Object[]{customer_id}, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setCustomer_id(rs.getInt(  "customer_id"));
                customer.setCustomer_firstname(rs.getString("customer_firstname"));
                customer.setCustomer_lastname(rs.getString("customer_lastname"));
                customer.setCustomer_emailid(rs.getString("customer_emailid"));
                customer.setCustomer_password(rs.getString("customer_password"));
                customer.setCustomer_mobile(rs.getString("customer_mobile"));
                customer.setCustomer_dob(rs.getString("customer_dob"));
                customer.setCustomer_address(rs.getString("customer_address"));
                customer.setCustomer_postcode(rs.getString("customer_postcode"));
                customer.setCustomer_country(rs.getString("customer_country"));

                return customer;
            }
        });

    }


    public List<Account> findallaccounts() {
        String sql = "SELECT account_id, customer_id, account_number, account_type, account_balance, account_status, account_created_at FROM account";

        RowMapper<Account> thisrm = new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet resultSet, int i) throws SQLException {
                Account account = new Account();
                account.setAccount_id(resultSet.getInt("account_id"));
                account.setCustomer_id(resultSet.getInt("customer_id"));
                account.setAccount_number(resultSet.getString("account_number"));
                account.setAccount_type(resultSet.getString("account_type"));
                account.setAccount_balance(resultSet.getString("account_balance"));
                account.setAccount_status(resultSet.getString("account_status"));
                account.setAccount_created_at(resultSet.getString("account_created_at"));


                return account;
            }
        };
        return template.query(sql, thisrm);
    }

    public Account findaccountrecodebyid(int account_id) {

        String sql = "SELECT account_id, customer_id, account_number, account_type, account_balance, account_status, account_created_at FROM account WHERE account_id = ?";

        return template.queryForObject(sql, new Object[]{account_id}, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setCustomer_id(rs.getInt("customer_id"));
                account.setAccount_number(rs.getString("account_number"));
                account.setAccount_type(rs.getString("account_type"));
                account.setAccount_balance(rs.getString("account_balance"));
                account.setAccount_status(rs.getString("account_status"));
                account.setAccount_created_at(rs.getString("account_created_at"));


                return account;
            }
        });

    }

    public Account findaccountrecodebycustomerid(int customer_id) {

        String sql = "SELECT account_id, customer_id, account_number, account_type, account_balance, account_status, account_created_at FROM account WHERE customer_id = ?";

        return template.queryForObject(sql, new Object[]{customer_id}, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setCustomer_id(rs.getInt("customer_id"));
                account.setAccount_number(rs.getString("account_number"));
                account.setAccount_type(rs.getString("account_type"));
                account.setAccount_balance(rs.getString("account_balance"));
                account.setAccount_status(rs.getString("account_status"));
                account.setAccount_created_at(rs.getString("account_created_at"));


                return account;
            }
        });

    }


    public int addcustomersupport(int customer_id, int account_id, String support_title,String support_desc, String support_status) {
        String sql = "INSERT INTO support (customer_id,account_id,support_title,support_desc,support_status) values(?,?,?,?,?)";
        return template.update(sql, customer_id, account_id, support_title,support_desc, support_status);
    }


//    update customer
public int updatecustomer(String customer_id, String customer_firstname, String customer_lastname, String customer_emailid, String customer_password, String customer_mobile, String customer_dob, String customer_address, String customer_postcode, String customer_country) {
    String sql = "UPDATE customers SET customer_firstname=(?), customer_lastname=(?), customer_emailid=(?), customer_password=(?), customer_mobile=(?), customer_dob=(?), customer_address=(?),customer_postcode=(?),customer_country=(?) WHERE customer_id=(?)";
    return template.update(sql, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country, customer_id);
}

    public int updateprofile(String customer_id, String customer_firstname, String customer_lastname, String customer_mobile, String customer_address, String customer_postcode, String customer_country) {
        String sql = "UPDATE customers SET customer_firstname=(?), customer_lastname=(?), customer_mobile=(?), customer_address=(?),customer_postcode=(?),customer_country=(?) WHERE customer_id=(?)";
        return template.update(sql, customer_firstname, customer_lastname, customer_mobile, customer_address, customer_postcode, customer_country, customer_id);
    }

public int updateaccount(String account_id, String account_type, String account_balance, String account_status){
    String sql = "UPDATE account SET account_type=(?), account_balance=(?), account_status=(?) WHERE account_id=(?)";
    return template.update(sql, account_type, account_balance, account_status, account_id);
}


    public Customer findrecodebyemail(String customer_emailid) {

        String sql = "SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country FROM customers WHERE customer_emailid = ?";

        return template.queryForObject(sql, new Object[]{customer_emailid}, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setCustomer_id(rs.getInt(  "customer_id"));
                customer.setCustomer_firstname(rs.getString("customer_firstname"));
                customer.setCustomer_lastname(rs.getString("customer_lastname"));
                customer.setCustomer_emailid(rs.getString("customer_emailid"));
                customer.setCustomer_password(rs.getString("customer_password"));
                customer.setCustomer_mobile(rs.getString("customer_mobile"));
                customer.setCustomer_dob(rs.getString("customer_dob"));
                customer.setCustomer_address(rs.getString("customer_address"));
                customer.setCustomer_postcode(rs.getString("customer_postcode"));
                customer.setCustomer_country(rs.getString("customer_country"));

                return customer;
            }
        });

    }



//    support
public List<Support> findAllSupport() {
//        String sql = "select * from support";
    String sql = "select support_id, customer_id, account_id, support_title, support_desc, support_created_at, admin_id, support_status from support";
    RowMapper<Support> rm = new RowMapper<Support>() {
        @Override
        public Support mapRow(ResultSet resultSet, int i) throws SQLException {
            Support support = new Support(resultSet.getInt("support_id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getInt("account_id"),
                    resultSet.getString("support_title"),
                    resultSet.getString("support_desc"),
                    resultSet.getString("support_created_at"),
                    resultSet.getInt("admin_id"),
                    resultSet.getString("support_status"));


            return support;
        }
    };

    return template.query(sql, rm);
}

    public Support findSupportRecodebyid(int id) {
        String sql = "select support_id, customer_id, account_id, support_title, support_desc, support_created_at, admin_id, support_status from support where support_id=?";
        return template.queryForObject(sql, new Object[]{id}, new RowMapper<Support>() {
            @Override
            public Support mapRow(ResultSet rs, int rowNum) throws SQLException {
                Support support = new Support();
                support.setSupport_id(rs.getInt("support_id"));
                support.setCustomer_id(rs.getInt("customer_id"));
                support.setAccount_id(rs.getInt("account_id"));
                support.setSupport_title(rs.getString("support_title"));
                support.setSupport_desc(rs.getString("support_desc"));
                support.setSupport_created_at(rs.getString("support_created_at"));
                support.setAdmin_id(rs.getInt("admin_id"));
                support.setSupport_status(rs.getString("support_status"));


                return support;
            }
        });
    }
}

