package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.core.service.SeatService;
import com.cinema.cineshow.infrastructure.entity.Seat;
import com.cinema.cineshow.infrastructure.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> getSeatsByTheatreId(String theatreId) {
        return seatRepository.findByTheatreId(theatreId);
    }

    @Override
    @Transactional
    public void createSeatsForTheatre(String theatreId, int rows, int cols) {
        // First, soft delete existing seats for this theatre
        seatRepository.deleteByTheatreId(theatreId);
        
        // Create new seats
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= cols; c++) {
                String seatNumber = String.format("%c%d", (char)('A' + r - 1), c);
                Seat seat = Seat.builder()
                        .theatreId(theatreId)
                        .seatNumber(seatNumber)
                        .row(r)
                        .col(c)
                        .build();
                seatRepository.save(seat);
            }
        }
    }
}
