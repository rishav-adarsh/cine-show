package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.ShowSeatDtos;
import com.cinema.cineshow.api.mapper.ShowSeatMapper;
import com.cinema.cineshow.core.service.ShowSeatService;
import com.cinema.cineshow.infrastructure.entity.ShowSeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/show-seats")
public class ShowSeatController {
    private static final Logger log = LoggerFactory.getLogger(ShowSeatController.class);

    private final ShowSeatService showSeatService;
    private final ShowSeatMapper showSeatMapper;

    public ShowSeatController(ShowSeatService showSeatService, ShowSeatMapper showSeatMapper) {
        this.showSeatService = showSeatService;
        this.showSeatMapper = showSeatMapper;
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<ApiResponse<List<ShowSeatDtos.ShowSeatResponse>>> getShowSeats(@PathVariable String showId) {
        log.info("Fetching seats for show: {}", showId);
        List<ShowSeatDtos.ShowSeatResponse> responses = showSeatService.getShowSeats(showId).stream()
                .map(showSeatMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Show seats fetched successfully"));
    }

    @PostMapping("/show/{showId}/lock")
    public ResponseEntity<ApiResponse<Boolean>> lockSeats(
            @PathVariable String showId, 
            @RequestBody ShowSeatDtos.LockSeatsRequest request) {
        log.info("Locking {} seats for show: {} by user: {}", request.getSeatIds().size(), showId, request.getUserId());
        showSeatService.lockSeats(showId, request.getSeatIds(), request.getUserId());
        return ResponseEntity.ok(ApiResponse.success(true, "Seats locked successfully"));
    }
}
