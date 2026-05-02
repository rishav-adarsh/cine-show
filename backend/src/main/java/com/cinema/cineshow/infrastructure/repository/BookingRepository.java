package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.Booking;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.BookingRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BookingRowMapper bookingRowMapper = new BookingRowMapper();

    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Booking> findById(String csid) {
        String sql = "SELECT * FROM bookings WHERE csid = ? AND is_deleted = false";
        List<Booking> bookings = jdbcTemplate.query(sql, bookingRowMapper, csid);
        return bookings.stream().findFirst();
    }

    public List<Booking> findAll() {
        String sql = "SELECT * FROM bookings WHERE is_deleted = false";
        return jdbcTemplate.query(sql, bookingRowMapper);
    }

    public List<Booking> findByUserId(String userId) {
        String sql = "SELECT * FROM bookings WHERE user_id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql, bookingRowMapper, userId);
    }

    public List<Booking> findByShowId(String showId) {
        String sql = "SELECT * FROM bookings WHERE show_id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql, bookingRowMapper, showId);
    }

    public Booking save(Booking booking) {
        if (booking.getCsid() == null) {
            booking.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO bookings (csid, user_id, show_id, total_amount, status, booking_time, transaction_id, payment_method, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                booking.getCsid(),
                booking.getUserId(),
                booking.getShowId(),
                booking.getTotalAmount(),
                booking.getStatus() != null ? booking.getStatus().name() : null,
                booking.getBookedAt(),
                booking.getTransactionId(),
                booking.getPaymentMethod() != null ? booking.getPaymentMethod().name() : null,
                booking.getIsDeleted()
            );
        } else {
            String sql = "UPDATE bookings SET user_id = ?, show_id = ?, total_amount = ?, status = ?, booking_time = ?, transaction_id = ?, payment_method = ?, is_deleted = ? WHERE csid = ?";
            jdbcTemplate.update(sql,
                booking.getUserId(),
                booking.getShowId(),
                booking.getTotalAmount(),
                booking.getStatus() != null ? booking.getStatus().name() : null,
                booking.getBookedAt(),
                booking.getTransactionId(),
                booking.getPaymentMethod() != null ? booking.getPaymentMethod().name() : null,
                booking.getIsDeleted(),
                booking.getCsid()
            );
        }
        return booking;
    }

    public void deleteById(String csid) {
        String sql = "UPDATE bookings SET is_deleted = true WHERE csid = ?";
        jdbcTemplate.update(sql, csid);
    }
}

