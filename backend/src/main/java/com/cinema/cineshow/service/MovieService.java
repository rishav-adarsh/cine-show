package com.cinema.cineshow.service;

import com.cinema.cineshow.model.movie.Movie;
import com.cinema.cineshow.model.movie.Show;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    public Movie createMovie(Movie movie) throws Exception;

    public Optional<Movie> getMovie(Long movieId);

    public List<Movie> getAllMovies();

    public List<Show> getAllShows(Long movieId);

    public Movie updateMovie(Movie movie);

    public void deleteMovie(Long movieId);
}
