package MyModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Admin {
    @Id
    String admin_id;
    @Column(nullable = false)
    String admin_emailid;
    @Column(nullable = false)
    String admin_password;
    @Column(nullable = false)
    String admin_name;
    @Column(nullable = false)
    String admin_role;
    @Column(nullable = false)
    String admin_status;
    @Column(nullable = false)
    String admin_created_at;

    public Admin() {
    }

    public Admin(String admin_id, String admin_emailid, String admin_password, String admin_name, String admin_role, String admin_status, String admin_created_at) {
        this.admin_id = admin_id;
        this.admin_emailid = admin_emailid;
        this.admin_password = admin_password;
        this.admin_name = admin_name;
        this.admin_role = admin_role;
        this.admin_status = admin_status;
        this.admin_created_at = admin_created_at;
    }

    public Admin(String admin_emailid, String admin_password, String admin_name, String admin_role, String admin_status, String admin_created_at) {
        this.admin_emailid = admin_emailid;
        this.admin_password = admin_password;
        this.admin_name = admin_name;
        this.admin_role = admin_role;
        this.admin_status = admin_status;
        this.admin_created_at = admin_created_at;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_emailid() {
        return admin_emailid;
    }

    public void setAdmin_emailid(String admin_emailid) {
        this.admin_emailid = admin_emailid;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_role() {
        return admin_role;
    }

    public void setAdmin_role(String admin_role) {
        this.admin_role = admin_role;
    }

    public String getAdmin_status() {
        return admin_status;
    }

    public void setAdmin_status(String admin_status) {
        this.admin_status = admin_status;
    }

    public String getAdmin_created_at() {
        return admin_created_at;
    }

    public void setAdmin_created_at(String admin_created_at) {
        this.admin_created_at = admin_created_at;
    }
}
