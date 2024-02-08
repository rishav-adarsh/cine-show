package com.cinema.cineshow.service.impl;

import com.cinema.cineshow.model.movie.Movie;
import com.cinema.cineshow.model.movie.Show;
import com.cinema.cineshow.repo.MovieRepository;
import com.cinema.cineshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        return movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> getMovie(Long movieId) {
        return movieRepository.findById(movieId);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Show> getAllShows(Long movieId) {
        return movieRepository.findById(movieId).get().getShows();
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
