package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.BookingDtos;
import com.cinema.cineshow.api.mapper.BookingMapper;
import com.cinema.cineshow.core.service.BookingService;
import com.cinema.cineshow.infrastructure.entity.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingDtos.BookingResponse>> createBooking(@RequestBody BookingDtos.BookingCreateRequest request) {
        Booking booking = bookingMapper.toEntity(request);
        Booking createdBooking = bookingService.createBooking(booking, request.getSeatIds());
        return ResponseEntity.ok(ApiResponse.success(bookingMapper.toResponse(createdBooking), "Booking created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDtos.BookingResponse>> getBooking(@PathVariable String id) {
        return bookingService.getBooking(id)
                .map(booking -> ResponseEntity.ok(ApiResponse.success(bookingMapper.toResponse(booking), "Booking fetched successfully")))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "NOT_FOUND", "Booking not found")));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookingDtos.BookingResponse>>> getBookingsByUserId(@PathVariable String userId) {
        List<BookingDtos.BookingResponse> bookings = bookingService.getBookingsByUserId(userId).stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(bookings, "User bookings fetched successfully"));
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<ApiResponse<List<BookingDtos.BookingResponse>>> getBookingsByShowId(@PathVariable String showId) {
        List<BookingDtos.BookingResponse> bookings = bookingService.getBookingsByShowId(showId).stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(bookings, "Show bookings fetched successfully"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<BookingDtos.BookingResponse>> updateStatus(@PathVariable String id, @RequestParam String status) {
        Booking updatedBooking = bookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(bookingMapper.toResponse(updatedBooking), "Booking status updated successfully"));
    }
}
