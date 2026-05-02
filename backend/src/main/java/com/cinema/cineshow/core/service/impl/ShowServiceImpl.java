package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.infrastructure.entity.Movie;
import com.cinema.cineshow.infrastructure.entity.Show;
import com.cinema.cineshow.infrastructure.repository.MovieRepository;
import com.cinema.cineshow.infrastructure.repository.ShowRepository;
import com.cinema.cineshow.core.service.ShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService {
    private static final Logger log = LoggerFactory.getLogger(ShowServiceImpl.class);
    
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;

    public ShowServiceImpl(ShowRepository showRepository, MovieRepository movieRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Show createShow(Show show) {
        log.info("Creating show for movie ID: {}", show.getMovieId());
        return showRepository.save(show);
    }

    @Override
    public Optional<Show> getShow(String showId) {
        log.info("Fetching show with id: {}", showId);
        return showRepository.findById(showId);
    }

    @Override
    public List<Show> getShowsByMovieId(String movieId) {
        log.info("Fetching shows for movie id: {}", movieId);
        if (!movieRepository.findById(movieId).isPresent()) {
            throw new RuntimeException("Movie not found");
        }
        return showRepository.findByMovieMovieId(movieId);
    }

    @Override
    public List<Show> getAllShows() {
        log.info("Fetching all shows");
        return showRepository.findAll();
    }

    @Override
    public Show updateShow(Show show) {
        log.info("Updating show with id: {}", show.getCsid());
        return showRepository.save(show);
    }

    @Override
    public void deleteShow(String showId) {
        log.info("Deleting show with id: {}", showId);
        showRepository.deleteById(showId);
    }
}
