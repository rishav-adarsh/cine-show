package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.Movie;
import com.cinema.cineshow.infrastructure.entity.Show;
import com.cinema.cineshow.infrastructure.entity.ShowStatus;
import com.cinema.cineshow.infrastructure.entity.Theatre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ShowRowMapper implements RowMapper<Show> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Show mapRow(ResultSet rs, int rowNum) throws SQLException {
        Show show = new Show();
        show.setCsid(rs.getString("csid"));
        show.setStartTime(rs.getObject("start_time", LocalDateTime.class));
        show.setEndTime(rs.getObject("end_time", LocalDateTime.class));
        show.setTicketPrice(rs.getDouble("ticket_price"));

        show.setMovieId(rs.getString("movie_id"));
        show.setTheatreId(rs.getString("theatre_id"));

        show.setStatus(ShowStatus.valueOf(rs.getString("status")));
        show.setIsDeleted(rs.getBoolean("is_deleted"));

        return show;
    }
}
