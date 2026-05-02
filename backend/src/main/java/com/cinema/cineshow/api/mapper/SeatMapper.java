package com.cinema.cineshow.api.mapper;

import com.cinema.cineshow.api.dto.SeatDtos;
import com.cinema.cineshow.infrastructure.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {
    public SeatDtos.SeatResponse toResponse(Seat seat) {
        SeatDtos.SeatResponse response = new SeatDtos.SeatResponse();
        response.setCsid(seat.getCsid());
        response.setTheatreId(seat.getTheatreId());
        response.setSeatNumber(seat.getSeatNumber());
        response.setRow(seat.getRow());
        response.setCol(seat.getCol());
        return response;
    }
}
