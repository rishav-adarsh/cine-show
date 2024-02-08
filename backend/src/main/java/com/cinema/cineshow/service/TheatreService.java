package com.cinema.cineshow.service;

import com.cinema.cineshow.model.movie.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreService {
    public Theatre createTheatre(Theatre theatre);

    public Optional<Theatre> getTheatre(Long theatreId);

    public List<Theatre> getAllTheatres();

    public Theatre updateTheatre(Theatre theatre);

    public void deleteTheatre(Long theatreId);
}
