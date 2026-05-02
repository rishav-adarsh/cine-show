package com.cinema.cineshow.api.mapper;

import com.cinema.cineshow.api.dto.ShowSeatDtos;
import com.cinema.cineshow.infrastructure.entity.ShowSeat;
import org.springframework.stereotype.Component;

@Component
public class ShowSeatMapper {
    public ShowSeatDtos.ShowSeatResponse toResponse(ShowSeat showSeat) {
        ShowSeatDtos.ShowSeatResponse response = new ShowSeatDtos.ShowSeatResponse();
        response.setCsid(showSeat.getCsid());
        response.setShowId(showSeat.getShowId());
        response.setSeatId(showSeat.getSeatId());
        response.setPrice(showSeat.getPrice());
        response.setStatus(showSeat.getStatus());
        response.setLockedBy(showSeat.getLockedBy());
        response.setLockedAt(showSeat.getLockedAt());
        response.setBookingId(showSeat.getBookingId());
        return response;
    }
}
