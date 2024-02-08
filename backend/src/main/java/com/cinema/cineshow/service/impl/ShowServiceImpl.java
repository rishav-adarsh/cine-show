package com.cinema.cineshow.service.impl;

import com.cinema.cineshow.model.movie.Movie;
import com.cinema.cineshow.model.movie.Show;
import com.cinema.cineshow.repo.MovieRepository;
import com.cinema.cineshow.repo.ShowRepository;
import com.cinema.cineshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Show createShow(Show show) {
        return showRepository.save(show);
    }

    @Override
    public Optional<Show> getShow(Long showId) {
        return showRepository.findById(showId);
    }

    @Override
    public List<Show> getShowsByMovieId(Long movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        return movie.getShows();
    }

    @Override
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    @Override
    public Show updateShow(Show show) {
        return showRepository.save(show);
    }

    @Override
    public void deleteShow(Long showId) {
        showRepository.deleteById(showId);
    }
}
