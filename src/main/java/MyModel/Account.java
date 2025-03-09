package MyModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    int account_id;
    @Column(nullable = false)
    int customer_id;
    @Column(nullable = false)
    String account_number;
    @Column(nullable = false)
    String account_type;
    @Column(nullable = false)
    String account_balance;
    @Column(nullable = false)
    String account_status;
    @Column(nullable = false)
    String account_created_at;

    public Account() {
    }

    public Account(int account_id, int customer_id, String account_number, String account_type, String account_balance, String account_status, String account_created_at) {
        this.account_id = account_id;
        this.customer_id = customer_id;
        this.account_number = account_number;
        this.account_type = account_type;
        this.account_balance = account_balance;
        this.account_status = account_status;
        this.account_created_at = account_created_at;
    }

    public Account(int customer_id, String account_number, String account_type, String account_balance, String account_status) {
        this.customer_id = customer_id;
        this.account_number = account_number;
        this.account_type = account_type;
        this.account_balance = account_balance;
        this.account_status = account_status;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(String account_balance) {
        this.account_balance = account_balance;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getAccount_created_at() {
        return account_created_at;
    }

    public void setAccount_created_at(String account_created_at) {
        this.account_created_at = account_created_at;
    }
}
