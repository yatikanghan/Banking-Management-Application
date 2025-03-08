package MyModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    int customer_id;
    @Column(nullable = false)
    String customer_firstname;
    @Column(nullable = false)
    String customer_lastname;
    @Column(nullable = false)
    String customer_emailid;
    @Column(nullable = false)
    String customer_password;
    @Column(nullable = false)
    String customer_mobile;
    @Column(nullable = false)
    String customer_dob;
    @Column(nullable = false)
    String customer_address;
    @Column(nullable = false)
    String customer_postcode;
    @Column(nullable = false)
    String customer_country;

    public Customer() {
    }

    public Customer(int customer_id, String customer_firstname, String customer_lastname, String customer_emailid, String customer_password, String customer_mobile, String customer_dob, String customer_address, String customer_postcode, String customer_country) {
        this.customer_id = customer_id;
        this.customer_firstname = customer_firstname;
        this.customer_lastname = customer_lastname;
        this.customer_emailid = customer_emailid;
        this.customer_password = customer_password;
        this.customer_mobile = customer_mobile;
        this.customer_dob = customer_dob;
        this.customer_address = customer_address;
        this.customer_postcode = customer_postcode;
        this.customer_country = customer_country;
    }

    public Customer(String customer_firstname, String customer_lastname, String customer_emailid, String customer_password, String customer_mobile, String customer_dob, String customer_address, String customer_postcode, String customer_country) {
        this.customer_firstname = customer_firstname;
        this.customer_lastname = customer_lastname;
        this.customer_emailid = customer_emailid;
        this.customer_password = customer_password;
        this.customer_mobile = customer_mobile;
        this.customer_dob = customer_dob;
        this.customer_address = customer_address;
        this.customer_postcode = customer_postcode;
        this.customer_country = customer_country;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int  customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_firstname() {
        return customer_firstname;
    }

    public void setCustomer_firstname(String customer_firstname) {
        this.customer_firstname = customer_firstname;
    }

    public String getCustomer_lastname() {
        return customer_lastname;
    }

    public void setCustomer_lastname(String customer_lastname) {
        this.customer_lastname = customer_lastname;
    }

    public String getCustomer_emailid() {
        return customer_emailid;
    }

    public void setCustomer_emailid(String customer_emailid) {
        this.customer_emailid = customer_emailid;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getCustomer_dob() {
        return customer_dob;
    }

    public void setCustomer_dob(String customer_dob) {
        this.customer_dob = customer_dob;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_postcode() {
        return customer_postcode;
    }

    public void setCustomer_postcode(String customer_postcode) {
        this.customer_postcode = customer_postcode;
    }

    public String getCustomer_country() {
        return customer_country;
    }

    public void setCustomer_country(String customer_country) {
        this.customer_country = customer_country;
    }
}
