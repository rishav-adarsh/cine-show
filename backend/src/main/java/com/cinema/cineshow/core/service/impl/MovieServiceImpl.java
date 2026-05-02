package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.infrastructure.entity.Movie;
import com.cinema.cineshow.infrastructure.entity.Show;
import com.cinema.cineshow.infrastructure.repository.MovieRepository;
import com.cinema.cineshow.core.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);
    
    private final MovieRepository movieRepository;
    private final com.cinema.cineshow.infrastructure.repository.ShowRepository showRepository;

    public MovieServiceImpl(MovieRepository movieRepository, com.cinema.cineshow.infrastructure.repository.ShowRepository showRepository) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        log.info("Creating movie: {}", movie.getName());
        return movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> getMovie(String movieId) {
        log.info("Fetching movie with id: {}", movieId);
        return movieRepository.findById(movieId);
    }

    @Override
    public List<Movie> getAllMovies() {
        log.info("Fetching all movies");
        return movieRepository.findAll();
    }

    @Override
    public List<Show> getAllShows(String movieId) {
        log.info("Fetching all shows for movie id: {}", movieId);
        if (!movieRepository.findById(movieId).isPresent()) {
            throw new RuntimeException("Movie not found");
        }
        return showRepository.findByMovieMovieId(movieId);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        log.info("Updating movie: {}", movie.getName());
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String movieId) {
        log.info("Deleting movie with id: {}", movieId);
        movieRepository.deleteById(movieId);
    }
}
