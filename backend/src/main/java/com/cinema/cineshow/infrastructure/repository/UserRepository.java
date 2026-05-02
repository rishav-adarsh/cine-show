package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.User;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.UserRowMapper;
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
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper = new UserRowMapper();

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, username);
        if (users.isEmpty()) {
            return null;
        }
        User user = users.get(0);
        if (user.getIsDeleted()) {
            return null; // or handle it as "not found"
        }
        return user;
    }

    public User findById(String id) {
        String sql = "SELECT * FROM users WHERE csid = ? AND is_deleted = false";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, id);
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users WHERE is_deleted = false";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public User save(User user) {
        if (user.getCsid() == null) {
            user.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO users (csid, username, password_hash, first_name, last_name, email, phone, profile_image_url, enabled, role_id, is_deleted) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                user.getCsid(), user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), 
                user.getEmail(), user.getPhone(), user.getProfile(), user.isEnabled(), user.getRoleId(), user.getIsDeleted());
        } else {
            String sql = "UPDATE users SET username = ?, password_hash = ?, first_name = ?, last_name = ?, email = ?, phone = ?, " +
                         "profile_image_url = ?, enabled = ?, role_id = ?, is_deleted = ? WHERE csid = ?";
            jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), 
                user.getEmail(), user.getPhone(), user.getProfile(), user.isEnabled(), 
                user.getRoleId(), user.getIsDeleted(), user.getCsid());
        }
        return user;
    }

    public void deleteById(String csid) {
        String sql = "UPDATE users SET is_deleted = true WHERE csid = ?";
        jdbcTemplate.update(sql, csid);
    }

    public boolean existsById(String csid) {
        String sql = "SELECT count(*) FROM users WHERE csid = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, csid);
        return count != null && count > 0;
    }
}
