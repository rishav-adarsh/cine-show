package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.Theatre;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.TheatreRowMapper;
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
public class TheatreRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TheatreRowMapper theatreRowMapper = new TheatreRowMapper();

    public TheatreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Theatre> findById(String csid) {
        String sql = "SELECT * FROM theatres WHERE csid = ? AND is_deleted = false";
        List<Theatre> theatres = jdbcTemplate.query(sql, theatreRowMapper, csid);
        return theatres.stream().findFirst();
    }

    public List<Theatre> findAll() {
        String sql = "SELECT * FROM theatres WHERE is_deleted = false";
        return jdbcTemplate.query(sql, theatreRowMapper);
    }

    public Theatre save(Theatre theatre) {
        if (theatre.getCsid() == null) {
            theatre.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO theatres (csid, name, location, is_deleted) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                theatre.getCsid(), theatre.getTheatreName(), theatre.getLocation(), theatre.getIsDeleted());
        } else {
            String sql = "UPDATE theatres SET name = ?, location = ?, is_deleted = ? WHERE csid = ?";
            jdbcTemplate.update(sql,
                theatre.getTheatreName(), theatre.getLocation(), theatre.getIsDeleted(), theatre.getCsid());
        }
        return theatre;
    }

    public void deleteById(String csid) {
        String sql = "UPDATE theatres SET is_deleted = true WHERE csid = ?";
        jdbcTemplate.update(sql, csid);
    }
}
