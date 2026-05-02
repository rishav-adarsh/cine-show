package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.Seat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatRowMapper implements RowMapper<Seat> {
    @Override
    public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Seat seat = new Seat();
        seat.setCsid(rs.getString("csid"));
        seat.setTheatreId(rs.getString("theatre_id"));
        seat.setSeatNumber(rs.getString("seat_number"));
        seat.setRow(rs.getInt("row_num"));
        seat.setCol(rs.getInt("col_num"));
        seat.setDeleted(rs.getBoolean("is_deleted"));
        return seat;
    }
}
