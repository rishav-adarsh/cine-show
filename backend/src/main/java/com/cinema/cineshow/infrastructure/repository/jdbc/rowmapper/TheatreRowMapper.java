package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.Theatre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TheatreRowMapper implements RowMapper<Theatre> {
    @Override
    public Theatre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Theatre theatre = new Theatre();
        theatre.setCsid(rs.getString("csid"));
        theatre.setTheatreName(rs.getString("name"));
        theatre.setLocation(rs.getString("location"));
        theatre.setIsDeleted(rs.getBoolean("is_deleted"));
        return theatre;
    }
}
