package MyModel;

public class Support {
    int support_id;
    int customer_id;
    int account_id;
    String support_title;
    String support_desc;
    String support_created_at;
    int admin_id;
    String support_status;

    public Support() {
    }

    public Support(int support_id, int customer_id, int account_id, String support_title, String support_desc, String support_created_at, int admin_id, String support_status) {
        this.support_id = support_id;
        this.customer_id = customer_id;
        this.account_id = account_id;
        this.support_title = support_title;
        this.support_desc = support_desc;
        this.support_created_at = support_created_at;
        this.admin_id = admin_id;
        this.support_status = support_status;
    }

    public Support(int customer_id, int account_id, String support_title, String support_desc, String support_status) {
        this.customer_id = customer_id;
        this.account_id = account_id;
        this.support_title = support_title;
        this.support_desc = support_desc;
        this.support_status = support_status;
    }

    public int getSupport_id() {
        return support_id;
    }

    public void setSupport_id(int support_id) {
        this.support_id = support_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getSupport_title() {
        return support_title;
    }

    public void setSupport_title(String support_title) {
        this.support_title = support_title;
    }

    public String getSupport_desc() {
        return support_desc;
    }

    public void setSupport_desc(String support_desc) {
        this.support_desc = support_desc;
    }

    public String getSupport_created_at() {
        return support_created_at;
    }

    public void setSupport_created_at(String support_created_at) {
        this.support_created_at = support_created_at;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getSupport_status() {
        return support_status;
    }

    public void setSupport_status(String support_status) {
        this.support_status = support_status;
    }
}
