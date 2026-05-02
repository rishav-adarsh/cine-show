package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.SeatStatus;
import com.cinema.cineshow.infrastructure.entity.ShowSeat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ShowSeatRowMapper implements RowMapper<ShowSeat> {
    @Override
    public ShowSeat mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShowSeat showSeat = new ShowSeat();
        showSeat.setCsid(rs.getString("csid"));
        showSeat.setShowId(rs.getString("show_id"));
        showSeat.setSeatId(rs.getString("seat_id"));
        showSeat.setPrice(rs.getDouble("price"));
        showSeat.setStatus(SeatStatus.valueOf(rs.getString("status")));
        showSeat.setLockedBy(rs.getString("locked_by"));
        showSeat.setLockedAt(rs.getObject("locked_at", LocalDateTime.class));
        showSeat.setBookingId(rs.getString("booking_id"));
        showSeat.setIsDeleted(rs.getBoolean("is_deleted"));
        return showSeat;
    }
}
