package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.Booking;
import com.cinema.cineshow.infrastructure.entity.BookingStatus;
import com.cinema.cineshow.infrastructure.entity.PaymentMethod;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BookingRowMapper implements RowMapper<Booking> {

    @Override
    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
        Booking booking = new Booking();
        booking.setCsid(rs.getString("csid"));
        booking.setUserId(rs.getString("user_id"));
        booking.setShowId(rs.getString("show_id"));
        booking.setTotalAmount(rs.getDouble("total_amount"));
        
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            booking.setStatus(BookingStatus.valueOf(statusStr));
        }
        
        booking.setBookedAt(rs.getObject("booking_time", LocalDateTime.class));
        booking.setTransactionId(rs.getString("transaction_id"));
        String paymentMethodStr = rs.getString("payment_method");
        if (paymentMethodStr != null) {
            booking.setPaymentMethod(PaymentMethod.valueOf(paymentMethodStr));
        }
        booking.setIsDeleted(rs.getBoolean("is_deleted"));

        return booking;
    }
}

