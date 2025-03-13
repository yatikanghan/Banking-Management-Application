package MyModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transaction {
    @Id
    int t_id;
    @Column(nullable = false)
    String transaction_id;
    @Column(nullable = false)
    String sender_account;
    @Column(nullable = true)
    String receiver_account;
    @Column(nullable = true)
    int transaction_amount;
    @Column(nullable = false)
    String transaction_type;
    @Column(nullable = false)
    String transaction_date;
    @Column(nullable = true)
    int admin_id;
    @Column(nullable = true)
    String transaction_remark;

    public Transaction() {
    }

    public Transaction(String sender_account, String receiver_account, int transaction_amount, String transaction_type, String transaction_remark, String transaction_id) {
        this.sender_account = sender_account;
        this.receiver_account = receiver_account;
        this.transaction_amount = transaction_amount;
        this.transaction_type = transaction_type;
        this.transaction_remark = transaction_remark;
        this.transaction_id = transaction_id;
    }

    public Transaction(int t_id, String transaction_id, String sender_account, String receiver_account, int transaction_amount, String transaction_type, String transaction_date, int admin_id, String transaction_remark) {
        this.t_id = t_id;
        this.transaction_id = transaction_id;
        this.sender_account = sender_account;
        this.receiver_account = receiver_account;
        this.transaction_amount = transaction_amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.admin_id = admin_id;
        this.transaction_remark = transaction_remark;
    }

    public Transaction(int t_id, String transaction_id, String sender_account, String receiver_account, int transaction_amount, String transaction_type, String transaction_date, String transaction_remark) {
        this.t_id = t_id;
        this.transaction_id = transaction_id;
        this.sender_account = sender_account;
        this.receiver_account = receiver_account;
        this.transaction_amount = transaction_amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.transaction_remark = transaction_remark;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getSender_account() {
        return sender_account;
    }

    public void setSender_account(String sender_account) {
        this.sender_account = sender_account;
    }

    public String getReceiver_account() {
        return receiver_account;
    }

    public void setReceiver_account(String receiver_account) {
        this.receiver_account = receiver_account;
    }

    public int getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(int transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getTransaction_remark() {
        return transaction_remark;
    }

    public void setTransaction_remark(String transaction_remark) {
        this.transaction_remark = transaction_remark;
    }
}
