package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.nosql.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking);
    Optional<Booking> getBooking(String id);
    List<Booking> getBookingsByUserId(String userId);
    List<Booking> getBookingsByShowId(String showId);
    Booking updateBookingStatus(String id, String status);
}
