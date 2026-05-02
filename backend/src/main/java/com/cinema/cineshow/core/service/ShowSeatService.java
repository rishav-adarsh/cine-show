package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.entity.ShowSeat;
import java.util.List;

public interface ShowSeatService {
    List<ShowSeat> getShowSeats(String showId);
    void lockSeats(String showId, List<String> seatIds, String userId);
    void unlockExpiredSeats();
}
