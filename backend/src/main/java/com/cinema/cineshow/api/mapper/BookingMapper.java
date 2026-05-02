package com.cinema.cineshow.api.mapper;

import com.cinema.cineshow.api.dto.BookingDtos;
import com.cinema.cineshow.infrastructure.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toEntity(BookingDtos.BookingCreateRequest request) {
        if (request == null) return null;
        
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setShowId(request.getShowId());
        booking.setTotalAmount(request.getTotalAmount());
        booking.setPaymentMethod(request.getPaymentMethod());
        booking.setTransactionId(request.getTransactionId());
        return booking;
    }

    public BookingDtos.BookingResponse toResponse(Booking booking) {
        if (booking == null) return null;

        BookingDtos.BookingResponse response = new BookingDtos.BookingResponse();
        response.setCsid(booking.getCsid());
        response.setUserId(booking.getUserId());
        response.setShowId(booking.getShowId());
        response.setTotalAmount(booking.getTotalAmount());
        response.setStatus(booking.getStatus());
        response.setBookedAt(booking.getBookedAt());
        response.setTransactionId(booking.getTransactionId());
        response.setPaymentMethod(booking.getPaymentMethod());
        return response;
    }
}
