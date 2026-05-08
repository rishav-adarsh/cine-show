package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.ShowSeat;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.ShowSeatRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShowSeatRepository {
    private static final Logger log = LoggerFactory.getLogger(ShowSeatRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final ShowSeatRowMapper showSeatRowMapper = new ShowSeatRowMapper();

    public ShowSeatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ShowSeat> findByShowId(String showId) {
        String sql = "SELECT * FROM show_seats WHERE show_id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql, showSeatRowMapper, showId);
    }

    public Optional<ShowSeat> findByShowIdAndSeatId(String showId, String seatId) {
        String sql = "SELECT * FROM show_seats WHERE show_id = ? AND seat_id = ? AND is_deleted = false";
        List<ShowSeat> results = jdbcTemplate.query(sql, showSeatRowMapper, showId, seatId);
        return results.stream().findFirst();
    }

    public ShowSeat save(ShowSeat showSeat) {
        if (showSeat.getCsid() == null) {
            showSeat.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO show_seats (csid, show_id, seat_id, price, status, locked_by, locked_at, booking_id, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                showSeat.getCsid(), showSeat.getShowId(), showSeat.getSeatId(), showSeat.getPrice(),
                showSeat.getStatus().name(), showSeat.getLockedBy(), showSeat.getLockedAt(),
                showSeat.getBookingId(), showSeat.getIsDeleted());
        } else {
            String sql = "UPDATE show_seats SET show_id = ?, seat_id = ?, price = ?, status = ?, locked_by = ?, locked_at = ?, booking_id = ?, is_deleted = ? WHERE csid = ?";
            jdbcTemplate.update(sql,
                showSeat.getShowId(), showSeat.getSeatId(), showSeat.getPrice(),
                showSeat.getStatus().name(), showSeat.getLockedBy(), showSeat.getLockedAt(),
                showSeat.getBookingId(), showSeat.getIsDeleted(), showSeat.getCsid());
        }
        return showSeat;
    }

    public void updateStatusBatch(List<String> showSeatIds, String status, String bookingId) {
        String sql = "UPDATE show_seats SET status = ?, booking_id = ?, locked_by = NULL, locked_at = NULL WHERE csid IN (" + 
                     String.join(",", showSeatIds.stream().map(id -> "'" + id + "'").toArray(String[]::new)) + ")";
        jdbcTemplate.update(sql, status, bookingId);
    }

    public int lockSeats(String showId, List<String> seatIds, String userId, LocalDateTime lockTime) {
        String placeholders = String.join(",", seatIds.stream().map(id -> "?").toArray(String[]::new));
        String sql = "UPDATE show_seats SET status = 'LOCKED', locked_by = ?, locked_at = ? " +
                     "WHERE show_id = ? AND seat_id IN (" + placeholders + ") " +
                     "AND (status = 'AVAILABLE' OR status IS NULL)";
        
        Object[] params = new Object[seatIds.size() + 3];
        params[0] = userId;
        params[1] = lockTime;
        params[2] = showId;
        for (int i = 0; i < seatIds.size(); i++) {
            params[i + 3] = seatIds.get(i);
        }
        
        return jdbcTemplate.update(sql, params);
    }

    public int upsertLockSeat(String showId, String seatId, Double price, String userId, LocalDateTime lockTime) {
        String sql = "INSERT INTO show_seats (csid, show_id, seat_id, price, status, locked_by, locked_at, is_deleted) " +
                     "VALUES (?, ?, ?, ?, 'LOCKED', ?, ?, false) " +
                     "ON CONFLICT (show_id, seat_id) " +
                     "DO UPDATE SET status = 'LOCKED', locked_by = EXCLUDED.locked_by, locked_at = EXCLUDED.locked_at, price = COALESCE(EXCLUDED.price, show_seats.price) " +
                     "WHERE (show_seats.status = 'AVAILABLE' OR show_seats.status IS NULL) AND show_seats.is_deleted = false";
        return jdbcTemplate.update(sql, UUID.randomUUID().toString(), showId, seatId, price, userId, lockTime);
    }

    public int[] batchUpsertLockSeats(String showId, List<String> seatIds, Double price, String userId, LocalDateTime lockTime) {
        String sql = "INSERT INTO show_seats (csid, show_id, seat_id, price, status, locked_by, locked_at, is_deleted) " +
                     "VALUES (?, ?, ?, ?, 'LOCKED', ?, ?, false) " +
                     "ON CONFLICT (show_id, seat_id) " +
                     "DO UPDATE SET status = 'LOCKED', locked_by = EXCLUDED.locked_by, locked_at = EXCLUDED.locked_at, price = COALESCE(EXCLUDED.price, show_seats.price) " +
                     "WHERE (show_seats.status = 'AVAILABLE' OR show_seats.status IS NULL) AND show_seats.is_deleted = false";
        
        return jdbcTemplate.batchUpdate(sql, new org.springframework.jdbc.core.BatchPreparedStatementSetter() {
            @Override
            public void setValues(java.sql.PreparedStatement ps, int i) throws java.sql.SQLException {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, showId);
                ps.setString(3, seatIds.get(i));
                ps.setObject(4, price);
                ps.setString(5, userId);
                ps.setObject(6, lockTime);
            }

            @Override
            public int getBatchSize() {
                return seatIds.size();
            }
        });
    }

    public int confirmBooking(String showId, String userId, String bookingId, List<String> seatIds) {
        if (CollectionUtils.isEmpty(seatIds)) {
            return 0;
        }

        String placeholders = String.join(",", seatIds.stream().map(id -> "?").toArray(String[]::new));
        String confirmSql = "UPDATE show_seats SET status = 'BOOKED', booking_id = ?, locked_by = NULL, locked_at = NULL " +
                            "WHERE show_id = ? AND locked_by = ? AND status = 'LOCKED' AND seat_id IN (" + placeholders + ")";
        
        Object[] confirmParams = new Object[seatIds.size() + 3];
        confirmParams[0] = bookingId;
        confirmParams[1] = showId;
        confirmParams[2] = userId;
        for (int i = 0; i < seatIds.size(); i++) {
            confirmParams[i + 3] = seatIds.get(i);
        }
        
        int confirmedCount = jdbcTemplate.update(confirmSql, confirmParams);

        return confirmedCount;
    }

    public int unlockExpiredSeats(LocalDateTime expiryTime) {
        String sql = "UPDATE show_seats SET status = 'AVAILABLE', locked_by = NULL, locked_at = NULL " +
                     "WHERE status = 'LOCKED' AND locked_at < ? AND is_deleted = false";
        int updatedRows = jdbcTemplate.update(sql, expiryTime);
        if (updatedRows > 0) {
            log.info("Unlocked {} expired seats locked before {}", updatedRows, expiryTime);
        }
        return updatedRows;
    }

    public int countAvailableSeats(String theatreId, String showId) {
        String sql = "SELECT COUNT(*) FROM seats s " +
                     "WHERE s.theatre_id = ? AND s.is_deleted = false " +
                     "AND s.csid NOT IN (" +
                     "    SELECT ss.seat_id FROM show_seats ss " +
                     "    WHERE ss.show_id = ? AND ss.status IN ('BOOKED', 'LOCKED') AND ss.is_deleted = false" +
                     ")";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, theatreId, showId);
        return count != null ? count : 0;
    }
}
