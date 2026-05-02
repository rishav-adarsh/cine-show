package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.core.service.BookingService;
import com.cinema.cineshow.infrastructure.nosql.entity.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(ApiResponse.success(createdBooking, "Booking created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> getBooking(@PathVariable String id) {
        return bookingService.getBooking(id)
                .map(booking -> ResponseEntity.ok(ApiResponse.success(booking, "Booking fetched successfully")))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "NOT_FOUND", "Booking not found")));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByUserId(@PathVariable String userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(bookings, "User bookings fetched successfully"));
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByShowId(@PathVariable String showId) {
        List<Booking> bookings = bookingService.getBookingsByShowId(showId);
        return ResponseEntity.ok(ApiResponse.success(bookings, "Show bookings fetched successfully"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Booking>> updateStatus(@PathVariable String id, @RequestParam String status) {
        Booking updatedBooking = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(updatedBooking, "Booking status updated successfully"));
    }
}
