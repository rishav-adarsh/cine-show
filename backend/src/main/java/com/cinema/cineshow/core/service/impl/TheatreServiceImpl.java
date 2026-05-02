package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.infrastructure.entity.Theatre;
import com.cinema.cineshow.infrastructure.repository.TheatreRepository;
import com.cinema.cineshow.core.service.TheatreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreServiceImpl implements TheatreService {
    private static final Logger log = LoggerFactory.getLogger(TheatreServiceImpl.class);
    
    private final TheatreRepository theatreRepository;

    public TheatreServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public Theatre createTheatre(Theatre theatre) {
        log.info("Creating theatre: {}", theatre.getTheatreName());
        return theatreRepository.save(theatre);
    }

    @Override
    public Optional<Theatre> getTheatre(String theatreId) {
        log.info("Fetching theatre with id: {}", theatreId);
        return theatreRepository.findById(theatreId);
    }

    @Override
    public List<Theatre> getAllTheatres() {
        log.info("Fetching all theatres");
        return theatreRepository.findAll();
    }

    @Override
    public Theatre updateTheatre(Theatre theatre) {
        log.info("Updating theatre: {}", theatre.getTheatreName());
        return theatreRepository.save(theatre);
    }

    @Override
    public void deleteTheatre(String theatreId) {
        log.info("Deleting theatre with id: {}", theatreId);
        theatreRepository.deleteById(theatreId);
    }
}
