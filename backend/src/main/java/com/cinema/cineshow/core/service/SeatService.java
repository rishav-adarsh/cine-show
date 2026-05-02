package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.entity.Seat;
import java.util.List;

public interface SeatService {
    List<Seat> getSeatsByTheatreId(String theatreId);
    void createSeatsForTheatre(String theatreId, int rows, int cols);
}
