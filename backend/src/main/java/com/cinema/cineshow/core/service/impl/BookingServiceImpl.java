package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.core.service.BookingService;
import com.cinema.cineshow.infrastructure.entity.Booking;
import com.cinema.cineshow.infrastructure.entity.BookingStatus;
import com.cinema.cineshow.infrastructure.repository.BookingRepository;
import com.cinema.cineshow.infrastructure.repository.ShowSeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    
    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ShowSeatRepository showSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Override
    @Transactional
    public Booking createBooking(Booking booking, List<String> seatIds) {
        log.info("Creating booking for user {} and show {}", booking.getUserId(), booking.getShowId());
        
        // 1. Save the booking
        booking.setBookedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED); // Directly confirmed for now as per simplified flow
        Booking savedBooking = bookingRepository.save(booking);

        // 2. Link the SPECIFIC locked seats to this booking and mark them as BOOKED
        // Any other seats locked by this user for this show will be UNLOCKED
        int updatedCount = showSeatRepository.confirmBooking(booking.getShowId(), booking.getUserId(), savedBooking.getCsid(), seatIds);

        if (updatedCount == 0) {
            throw new RuntimeException("No valid locked seats found for this booking. Please try again.");
        }

        return savedBooking;
    }

    @Override
    public Optional<Booking> getBooking(String id) {
        log.info("Getting booking with id: {}", id);
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> getBookingsByUserId(String userId) {
        log.info("Getting bookings for user: {}", userId);
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByShowId(String showId) {
        log.info("Getting bookings for show: {}", showId);
        return bookingRepository.findByShowId(showId);
    }

    @Override
    public Booking updateBookingStatus(String id, String status) {
        log.info("Updating booking status for {} to {}", id, status);
        return bookingRepository.findById(id)
                .map(booking -> {
                    booking.setStatus(BookingStatus.valueOf(status));
                    return bookingRepository.save(booking);
                }).orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}
