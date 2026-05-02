package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.Seat;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.SeatRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SeatRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SeatRowMapper seatRowMapper = new SeatRowMapper();

    public SeatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Seat> findById(String csid) {
        String sql = "SELECT * FROM seats WHERE csid = ? AND is_deleted = false";
        List<Seat> seats = jdbcTemplate.query(sql, seatRowMapper, csid);
        return seats.stream().findFirst();
    }

    public List<Seat> findByTheatreId(String theatreId) {
        String sql = "SELECT * FROM seats WHERE theatre_id = ? AND is_deleted = false ORDER BY row_num, col_num";
        return jdbcTemplate.query(sql, seatRowMapper, theatreId);
    }

    public Seat save(Seat seat) {
        if (seat.getCsid() == null) {
            seat.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO seats (csid, theatre_id, seat_number, row_num, col_num, is_deleted) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                seat.getCsid(), seat.getTheatreId(), seat.getSeatNumber(), seat.getRow(), seat.getCol(), seat.isDeleted());
        } else {
            String sql = "UPDATE seats SET theatre_id = ?, seat_number = ?, row_num = ?, col_num = ?, is_deleted = ? WHERE csid = ?";
            jdbcTemplate.update(sql,
                seat.getTheatreId(), seat.getSeatNumber(), seat.getRow(), seat.getCol(), seat.isDeleted(), seat.getCsid());
        }
        return seat;
    }

    public void deleteByTheatreId(String theatreId) {
        String sql = "UPDATE seats SET is_deleted = true WHERE theatre_id = ?";
        jdbcTemplate.update(sql, theatreId);
    }
}
