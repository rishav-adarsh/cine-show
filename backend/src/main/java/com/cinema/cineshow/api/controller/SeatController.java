package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.SeatDtos;
import com.cinema.cineshow.api.mapper.SeatMapper;
import com.cinema.cineshow.core.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seats")
public class SeatController {
    private static final Logger log = LoggerFactory.getLogger(SeatController.class);

    private final SeatService seatService;
    private final SeatMapper seatMapper;

    public SeatController(SeatService seatService, SeatMapper seatMapper) {
        this.seatService = seatService;
        this.seatMapper = seatMapper;
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<ApiResponse<List<SeatDtos.SeatResponse>>> getSeatsByTheatre(@PathVariable String theatreId) {
        log.info("Fetching seats for theatre: {}", theatreId);
        List<SeatDtos.SeatResponse> responses = seatService.getSeatsByTheatreId(theatreId).stream()
                .map(seatMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Seats fetched successfully"));
    }

    @PostMapping("/theatre/{theatreId}/setup")
    public ResponseEntity<ApiResponse<Void>> setupSeats(@PathVariable String theatreId, @RequestBody SeatDtos.TheatreSeatSetupRequest request) {
        log.info("Setting up {}x{} seats for theatre: {}", request.getRows(), request.getCols(), theatreId);
        seatService.createSeatsForTheatre(theatreId, request.getRows(), request.getCols());
        return ResponseEntity.ok(ApiResponse.success(null, "Seats setup successfully"));
    }
}
