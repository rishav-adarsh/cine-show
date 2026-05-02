package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.entity.Show;

import java.util.List;
import java.util.Optional;

public interface ShowService {
    Show createShow(Show show);

    Optional<Show> getShow(String showId);

    List<Show> getShowsByMovieId(String movieId);

    List<Show> getAllShows();

    Show updateShow(Show show);

    void deleteShow(String showId);
}
