package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.core.service.ShowSeatService;
import com.cinema.cineshow.infrastructure.entity.ShowSeat;
import com.cinema.cineshow.infrastructure.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowSeatServiceImpl implements ShowSeatService {
    private static final Logger log = LoggerFactory.getLogger(ShowSeatServiceImpl.class);
    private static final String LOG_IDENTIFIER = "[ShowSeatServiceImpl]";

    private final ShowSeatRepository showSeatRepository;

    // Seat lock timeout in minutes
    private static final int LOCK_TIMEOUT_MINUTES = 10;
    private static final int UNLOCK_SEATS_INTERVAL_MILLIS = 20 * 60 * 1000;
    
    @Override
    public List<ShowSeat> getShowSeats(String showId) {
        return showSeatRepository.findByShowId(showId);
    }

    @Override
    @Transactional
    public void lockSeats(String showId, List<String> seatIds, String userId) {
        // Ensure unique seat IDs
        List<String> uniqueSeatIds = seatIds.stream().distinct().toList();
        LocalDateTime lockTime = LocalDateTime.now();

        int[] results = showSeatRepository.batchUpsertLockSeats(showId, uniqueSeatIds, null, userId, lockTime);
        int lockedCount = java.util.Arrays.stream(results).sum();

        // If the number of locked seats doesn't match the requested count, it means
        // at least one seat was not available. The @Transactional annotation ensures
        // that the entire operation is rolled back, leaving no seats locked.
        if (lockedCount != uniqueSeatIds.size()) {
            throw new RuntimeException("One or more seats are not available for locking.");
        }
    }

    @Override
    @Scheduled(fixedRate = UNLOCK_SEATS_INTERVAL_MILLIS)
    public void unlockExpiredSeats() {
        log.info("{}: Running scheduled task to unlock expired seats", LOG_IDENTIFIER);
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(LOCK_TIMEOUT_MINUTES);
        showSeatRepository.unlockExpiredSeats(expiryTime);
    }
}
