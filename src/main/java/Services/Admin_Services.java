package Services;

import MyModel.Account;
import MyModel.Admin;
import MyModel.Customer;
import MyModel.Support;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class Admin_Services {
    @Autowired
    JdbcTemplate template;


    public Admin getAdminById(int admin_id) {
        String sql = "select * from Admin where admin_id = ?";
        return template.queryForObject(sql, new Object[]{admin_id}, new RowMapper<Admin>() {
            @Override
            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Admin admin = new Admin();
                admin.setAdmin_id(rs.getInt("admin_id"));
                admin.setAdmin_emailid(rs.getString("admin_emailid"));
                admin.setAdmin_password(rs.getString("admin_password"));
                admin.setAdmin_status(rs.getString("admin_status"));
                admin.setAdmin_role(rs.getString("admin_role"));
                admin.setAdmin_created_at(rs.getString("admin_created_at"));
                return admin;
            }
        });
    }

    public int updateadmin(String admin_id, String admin_name, String admin_role, String admin_status){
        String sql="update admin set admin_name=?, admin_role=?, admin_status=? where admin_id=?;";
        return template.update(sql, admin_name, admin_role, admin_status, admin_id);
    }



    public List<Support> findAllSupport()
    {
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

    public Support findSupportRecodebyid(int id)
    {
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


   public int deletesupportrecodebyid(int support_id){
        String sql = "delete from support where support_id=?";
        return template.update(sql,support_id);
   }

    public int solvesupportrecodebyid(int support_id, int admin_id){
        String sql = "update support set support_status=?, admin_id=? where support_id=?";
        return template.update(sql, "Solved",admin_id,support_id);
    }

    public List<Admin> findAlladmins() {
        String sql = "select admin_id, admin_emailid, admin_password, admin_name, admin_role, admin_status, admin_created_at from Admin";
        RowMapper<Admin> rm = new RowMapper<Admin>() {
            @Override
            public Admin mapRow(ResultSet resultSet, int i) throws SQLException {
                Admin admin = new Admin(resultSet.getInt("admin_id"),
                        resultSet.getString("admin_emailid"),
                        resultSet.getString("admin_password"),
                        resultSet.getString("admin_name"),
                        resultSet.getString("admin_role"),
                        resultSet.getString("admin_status"),
                        resultSet.getString("admin_created_at"));


                return admin;
            }
        };

        return template.query(sql, rm);


    }


    public int adminaddstafffunction(String emailid, String password, String name, String role, String status) {
        String sql="Insert into admin (admin_emailid,admin_password,admin_name,admin_role,admin_status) values(?,?,?,?,?)";
        return template.update(sql, emailid, password, name, role, status);
    }



}
