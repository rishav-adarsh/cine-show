package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.TheatreDtos;
import com.cinema.cineshow.infrastructure.entity.Theatre;
import com.cinema.cineshow.api.mapper.TheatreMapper;
import com.cinema.cineshow.core.service.TheatreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/theatres")
public class TheatreController {
    private static final Logger log = LoggerFactory.getLogger(TheatreController.class);
    
    private final TheatreService theatreService;
    private final TheatreMapper theatreMapper;

    public TheatreController(TheatreService theatreService, TheatreMapper theatreMapper) {
        this.theatreService = theatreService;
        this.theatreMapper = theatreMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TheatreDtos.TheatreResponse>> createTheatre(@RequestBody TheatreDtos.TheatreUpsertRequest request) {
        log.info("Creating theatre: {}", request.getTheatreName());
        Theatre theatre = theatreMapper.toEntity(request);
        Theatre createdTheatre = theatreService.createTheatre(theatre);
        TheatreDtos.TheatreResponse response = theatreMapper.toResponse(createdTheatre);
        return ResponseEntity.ok(ApiResponse.success(response, "Theatre created successfully"));
    }

    @GetMapping("/{theatreId}")
    public ResponseEntity<ApiResponse<TheatreDtos.TheatreResponse>> getTheatre(@PathVariable String theatreId) {
        log.info("Fetching theatre with id: {}", theatreId);
        return theatreService.getTheatre(theatreId)
                .map(theatre -> ResponseEntity.ok(ApiResponse.success(theatreMapper.toResponse(theatre), "Theatre fetched successfully")))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "NOT_FOUND", "Theatre not found")));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TheatreDtos.TheatreResponse>>> getAllTheatres() {
        log.info("Fetching all theatres");
        List<TheatreDtos.TheatreResponse> responses = theatreService.getAllTheatres().stream()
                .map(theatreMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Theatres fetched successfully"));
    }

    @PutMapping("/{theatreId}")
    public ResponseEntity<ApiResponse<TheatreDtos.TheatreResponse>> updateTheatre(@PathVariable String theatreId, @RequestBody TheatreDtos.TheatreUpsertRequest request) {
        log.info("Updating theatre with id: {}", theatreId);
        Theatre theatre = theatreMapper.toEntity(request);
        theatre.setCsid(theatreId);
        Theatre updatedTheatre = theatreService.updateTheatre(theatre);
        TheatreDtos.TheatreResponse response = theatreMapper.toResponse(updatedTheatre);
        return ResponseEntity.ok(ApiResponse.success(response, "Theatre updated successfully"));
    }

    @DeleteMapping("/{theatreId}")
    public ResponseEntity<ApiResponse<Void>> deleteTheatre(@PathVariable String theatreId) {
        log.info("Deleting theatre with id: {}", theatreId);
        theatreService.deleteTheatre(theatreId);
        return ResponseEntity.ok(ApiResponse.success(null, "Theatre deleted successfully"));
    }
}
