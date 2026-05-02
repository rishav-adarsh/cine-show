package com.cinema.cineshow.api.mapper;

import com.cinema.cineshow.api.dto.ShowDtos;
import com.cinema.cineshow.core.service.MovieService;
import com.cinema.cineshow.core.service.TheatreService;
import com.cinema.cineshow.infrastructure.entity.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowMapper {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private TheatreMapper theatreMapper;

    public Show toEntity(ShowDtos.ShowUpsertRequest request) {
        Show show = new Show();
        show.setMovieId(request.getMovieId());
        show.setTheatreId(request.getTheatreId());
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setTicketPrice(request.getTicketPrice());
        show.setBookedSeatIds(request.getBookedSeatIds());
        // csid is ignored
        return show;
    }

    public ShowDtos.ShowResponse toResponse(Show show) {
        ShowDtos.ShowResponse response = new ShowDtos.ShowResponse();
        response.setCsid(show.getCsid());
        response.setStartTime(show.getStartTime());
        response.setEndTime(show.getEndTime());
        response.setTicketPrice(show.getTicketPrice());
        response.setBookedSeatIds(show.getBookedSeatIds());
        response.setMovie(movieService.getMovie(show.getMovieId()).map(movieMapper::toResponse).orElse(null));
        response.setTheatre(theatreService.getTheatre(show.getTheatreId()).map(theatreMapper::toResponse).orElse(null));
        return response;
    }
}
