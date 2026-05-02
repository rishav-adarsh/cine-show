package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.Role;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.RoleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleRowMapper = new RoleRowMapper();

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Role> findById(String csid) {
        String sql = "SELECT * FROM roles WHERE csid = ?";
        List<Role> roles = jdbcTemplate.query(sql, roleRowMapper, csid);
        return roles.stream().findFirst();
    }

    public Role findByRoleName(String name) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        List<Role> roles = jdbcTemplate.query(sql, roleRowMapper, name);
        return roles.isEmpty() ? null : roles.get(0);
    }

    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        return jdbcTemplate.query(sql, roleRowMapper);
    }

    public Role save(Role role) {
        if (role.getCsid() == null) {
            role.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO roles (csid, name) VALUES (?, ?)";
            jdbcTemplate.update(sql, role.getCsid(), role.getRoleName());
        } else {
            String sql = "UPDATE roles SET name = ? WHERE csid = ?";
            jdbcTemplate.update(sql, role.getRoleName(), role.getCsid());
        }
        return role;
    }

    public void deleteById(String csid) {
        String sql = "DELETE FROM roles WHERE csid = ?";
        jdbcTemplate.update(sql, csid);
    }
}
