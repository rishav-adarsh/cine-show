package com.cinema.cineshow.controller;

import com.cinema.cineshow.model.movie.Movie;
import com.cinema.cineshow.model.movie.Show;
import com.cinema.cineshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
@CrossOrigin("*")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("")
    public Movie createMovie(@RequestBody Movie movie) throws Exception {
        return  movieService.createMovie(movie);
    }

    @GetMapping("/{movieId}")
    public Optional<Movie> getMovie(@PathVariable("movieId") Long movieId) {
        return movieService.getMovie(movieId);
    }

    @GetMapping("")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{movieId}/shows")
    public List<Show> getShows(@PathVariable("movieId") Long movieId) {
        return movieService.getAllShows(movieId);
    }

    @PutMapping("")
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovie(movieId);
    }
}
