package com.cinema.cineshow.service;

import com.cinema.cineshow.model.movie.Show;

import java.util.List;
import java.util.Optional;

public interface ShowService {
    public Show createShow(Show show);

    public Optional<Show> getShow(Long showId);

    public List<Show> getShowsByMovieId(Long movieId);

    public List<Show> getAllShows();

    public Show updateShow(Show show);

    public void deleteShow(Long showId);
}
