package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.api.dto.MovieDtos;
import com.cinema.cineshow.api.dto.ShowDtos;
import com.cinema.cineshow.infrastructure.entity.Movie;
import com.cinema.cineshow.infrastructure.entity.Show;
import com.cinema.cineshow.api.mapper.MovieMapper;
import com.cinema.cineshow.api.mapper.ShowMapper;
import com.cinema.cineshow.core.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private static final Logger log = LoggerFactory.getLogger(MovieController.class);
    
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private ShowMapper showMapper;

    public MovieController() {
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieDtos.MovieResponse>> createMovie(@RequestBody MovieDtos.MovieUpsertRequest request) throws Exception {
        log.info("Creating movie: {}", request.getMovieName());
        Movie movie = movieMapper.toEntity(request);
        Movie createdMovie = movieService.createMovie(movie);
        MovieDtos.MovieResponse response = movieMapper.toResponse(createdMovie);
        return ResponseEntity.ok(ApiResponse.success(response, "Movie created successfully"));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse<MovieDtos.MovieResponse>> getMovie(@PathVariable String movieId) {
        log.info("Fetching movie with id: {}", movieId);
        return movieService.getMovie(movieId)
                .map(movie -> ResponseEntity.ok(ApiResponse.success(movieMapper.toResponse(movie), "Movie fetched successfully")))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error(404, "NOT_FOUND", "Movie not found")));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieDtos.MovieResponse>>> getAllMovies() {
        log.info("Fetching all movies");
        List<MovieDtos.MovieResponse> responses = movieService.getAllMovies().stream()
                .map(movieMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Movies fetched successfully"));
    }

    @GetMapping("/{movieId}/shows")
    public ResponseEntity<ApiResponse<List<ShowDtos.ShowResponse>>> getShows(@PathVariable String movieId) {
        log.info("Fetching all shows for movie id: {}", movieId);
        List<ShowDtos.ShowResponse> shows = movieService.getAllShows(movieId).stream()
                .map(showMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(shows, "Shows fetched successfully"));
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<ApiResponse<MovieDtos.MovieResponse>> updateMovie(@PathVariable String movieId, @RequestBody MovieDtos.MovieUpsertRequest request) {
        log.info("Updating movie with id: {}", movieId);
        Movie movie = movieMapper.toEntity(request);
        movie.setCsid(movieId);
        Movie updatedMovie = movieService.updateMovie(movie);
        MovieDtos.MovieResponse response = movieMapper.toResponse(updatedMovie);
        return ResponseEntity.ok(ApiResponse.success(response, "Movie updated successfully"));
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable String movieId) {
        log.info("Deleting movie with id: {}", movieId);
        movieService.deleteMovie(movieId);
        return ResponseEntity.ok(ApiResponse.success(null, "Movie deleted successfully"));
    }
}
