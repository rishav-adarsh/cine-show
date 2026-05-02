package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.core.service.BookingService;
import com.cinema.cineshow.infrastructure.nosql.entity.Booking;
import com.cinema.cineshow.infrastructure.nosql.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    
    // private final BookingRepository bookingRepository;

    public BookingServiceImpl() {
        // No MongoDB dependency for now
    }

    @Override
    public Booking createBooking(Booking booking) {
        log.info("MongoDB connection coming soon - Creating booking for user {} and show {}", booking.getUserId(), booking.getShowId());
        booking.setBookingTime(java.time.LocalDateTime.now());
        booking.setStatus("PENDING");
        booking.setCsid("mock-booking-" + System.currentTimeMillis());
        
        // Original MongoDB logic (commented out for now):
        // booking.setBookingTime(LocalDateTime.now());
        // booking.setStatus("PENDING");
        // return bookingRepository.save(booking);
        
        return booking;
    }

    @Override
    public java.util.Optional<Booking> getBooking(String id) {
        log.info("MongoDB connection coming soon - Getting booking with id: {}", id);
        
        // Original MongoDB logic (commented out for now):
        // return bookingRepository.findById(id);
        
        return java.util.Optional.empty();
    }

    @Override
    public List<Booking> getBookingsByUserId(String userId) {
        log.info("MongoDB connection coming soon - Getting bookings for user: {}", userId);
        
        // Original MongoDB logic (commented out for now):
        // return bookingRepository.findByUserId(userId);
        
        return java.util.Collections.emptyList();
    }

    @Override
    public List<Booking> getBookingsByShowId(String showId) {
        log.info("MongoDB connection coming soon - Getting bookings for show: {}", showId);
        
        // Original MongoDB logic (commented out for now):
        // return bookingRepository.findByShowId(showId);
        
        return java.util.Collections.emptyList();
    }

    @Override
    public Booking updateBookingStatus(String id, String status) {
        log.info("MongoDB connection coming soon - Updating booking status for {} to {}", id, status);
        
        // Original MongoDB logic (commented out for now):
        // return bookingRepository.findById(id)
        //         .map(booking -> {
        //             booking.setStatus(status);
        //             return bookingRepository.save(booking);
        //         }).orElseThrow(() -> new RuntimeException("Booking not found"));
        
        throw new RuntimeException("MongoDB connection coming soon - Booking not found");
    }
}
