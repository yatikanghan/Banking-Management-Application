package Services;

import MyModel.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class Admin_Services {
    @Autowired
    JdbcTemplate template;

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



}
