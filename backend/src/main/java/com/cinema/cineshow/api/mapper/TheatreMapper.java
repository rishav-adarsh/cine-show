package com.cinema.cineshow.api.mapper;

import com.cinema.cineshow.api.dto.TheatreDtos;
import com.cinema.cineshow.infrastructure.entity.Theatre;
import org.springframework.stereotype.Component;

@Component
public class TheatreMapper {

    public Theatre toEntity(TheatreDtos.TheatreUpsertRequest request) {
        Theatre theatre = new Theatre();
        theatre.setTheatreName(request.getTheatreName());
        theatre.setLocation(request.getLocation());
        // csid is ignored
        return theatre;
    }

    public Theatre toEntity(TheatreDtos.TheatreResponse response) {
        Theatre theatre = new Theatre();
        theatre.setCsid(response.getTheatreId());
        theatre.setTheatreName(response.getTheatreName());
        theatre.setLocation(response.getLocation());
        return theatre;
    }

    public TheatreDtos.TheatreResponse toResponse(Theatre theatre) {
        TheatreDtos.TheatreResponse response = new TheatreDtos.TheatreResponse();
        response.setTheatreId(theatre.getCsid());
        response.setTheatreName(theatre.getTheatreName());
        response.setLocation(theatre.getLocation());
        return response;
    }
}
