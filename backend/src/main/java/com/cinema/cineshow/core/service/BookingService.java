package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking, List<String> seatIds);
    Optional<Booking> getBooking(String id);
    List<Booking> getBookingsByUserId(String userId);
    List<Booking> getBookingsByShowId(String showId);
    Booking updateBookingStatus(String id, String status);
}
