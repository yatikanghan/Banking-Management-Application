package Services;

import MyModel.Account;
import MyModel.Admin;
import MyModel.Customer;
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
        String sql = "SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country from customers;";
        RowMapper<Customer> rm = new RowMapper<Customer>() {
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
        return template.query(sql, rm);
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
}
