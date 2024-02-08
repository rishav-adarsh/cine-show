package com.cinema.cineshow.service.impl;

import com.cinema.cineshow.model.movie.Theatre;
import com.cinema.cineshow.repo.TheatreRepository;
import com.cinema.cineshow.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreServiceImpl implements TheatreService {
    @Autowired
    TheatreRepository theatreRepository;

    @Override
    public Theatre createTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    @Override
    public Optional<Theatre> getTheatre(Long theatreId) {
        return theatreRepository.findById(theatreId);
    }

    @Override
    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

    @Override
    public Theatre updateTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    @Override
    public void deleteTheatre(Long theatreId) {
        theatreRepository.deleteById(theatreId);
    }
}
