package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.ShowDtos;
import com.cinema.cineshow.infrastructure.entity.Show;
import com.cinema.cineshow.api.mapper.ShowMapper;
import com.cinema.cineshow.core.service.ShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shows")
public class ShowController {
    private static final Logger log = LoggerFactory.getLogger(ShowController.class);
    
    private final ShowService showService;
    private final ShowMapper showMapper;

    public ShowController(ShowService showService, ShowMapper showMapper) {
        this.showService = showService;
        this.showMapper = showMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ShowDtos.ShowResponse>> createShow(@RequestBody ShowDtos.ShowUpsertRequest request) {
        log.info("Creating show");
        Show show = showMapper.toEntity(request);
        Show createdShow = showService.createShow(show);
        ShowDtos.ShowResponse response = showMapper.toResponse(createdShow);
        return ResponseEntity.ok(ApiResponse.success(response, "Show created successfully"));
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ApiResponse<ShowDtos.ShowResponse>> getShow(@PathVariable String showId) {
        log.info("Fetching show with id: {}", showId);
        return showService.getShow(showId)
                .map(show -> ResponseEntity.ok(ApiResponse.success(showMapper.toResponse(show), "Show fetched successfully")))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "NOT_FOUND", "Show not found")));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowDtos.ShowResponse>>> getAllShows() {
        log.info("Fetching all shows");
        List<ShowDtos.ShowResponse> responses = showService.getAllShows().stream()
                .map(show -> showMapper.toResponse(show))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Shows fetched successfully"));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowDtos.ShowResponse>>> getShowsByMovieId(@PathVariable String movieId) {
        log.info("Fetching shows for movie id: {}", movieId);
        List<ShowDtos.ShowResponse> responses = showService.getShowsByMovieId(movieId).stream()
                .map(show -> showMapper.toResponse(show))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Shows fetched successfully"));
    }

    @PutMapping("/{showId}")
    public ResponseEntity<ApiResponse<ShowDtos.ShowResponse>> updateShow(@PathVariable String showId, @RequestBody ShowDtos.ShowUpsertRequest request) {
        log.info("Updating show with id: {}", showId);
        Show show = showMapper.toEntity(request);
        show.setCsid(showId);
        Show updatedShow = showService.updateShow(show);
        ShowDtos.ShowResponse response = showMapper.toResponse(updatedShow);
        return ResponseEntity.ok(ApiResponse.success(response, "Show updated successfully"));
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity<ApiResponse<Void>> deleteShow(@PathVariable String showId) {
        log.info("Deleting show with id: {}", showId);
        showService.deleteShow(showId);
        return ResponseEntity.ok(ApiResponse.success(null, "Show deleted successfully"));
    }
}
