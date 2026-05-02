package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.Role;
import com.cinema.cineshow.infrastructure.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setCsid(rs.getString("csid"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setProfile(rs.getString("profile_image_url"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setRoleId(rs.getString("role_id"));
        
        try {
            user.setIsDeleted(rs.getBoolean("is_deleted"));
        } catch (SQLException e) {
            user.setIsDeleted(false);
        }

        return user;
    }
}
