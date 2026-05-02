package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.entity.Movie;
import com.cinema.cineshow.infrastructure.entity.Show;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie createMovie(Movie movie) throws Exception;

    Optional<Movie> getMovie(String movieId);

    List<Movie> getAllMovies();

    List<Show> getAllShows(String movieId);

    Movie updateMovie(Movie movie);

    void deleteMovie(String movieId);
}
