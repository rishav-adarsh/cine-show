package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.entity.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreService {
    Theatre createTheatre(Theatre theatre);

    Optional<Theatre> getTheatre(String theatreId);

    List<Theatre> getAllTheatres();

    Theatre updateTheatre(Theatre theatre);

    void deleteTheatre(String theatreId);
}
