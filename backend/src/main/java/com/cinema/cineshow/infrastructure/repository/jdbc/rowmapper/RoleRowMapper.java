package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setCsid(rs.getString("csid"));
        role.setRoleName(rs.getString("name"));
        return role;
    }
}
