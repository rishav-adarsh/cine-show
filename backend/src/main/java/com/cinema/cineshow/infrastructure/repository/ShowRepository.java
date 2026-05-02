package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.Show;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.ShowRowMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShowRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ShowRowMapper showRowMapper = new ShowRowMapper();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ShowRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Show> findById(String csid) {
        String sql = "SELECT * FROM shows WHERE csid = ? AND is_deleted = false";
        List<Show> shows = jdbcTemplate.query(sql, showRowMapper, csid);
        return shows.stream().findFirst();
    }

    public List<Show> findAll() {
        String sql = "SELECT * FROM shows WHERE is_deleted = false";
        return jdbcTemplate.query(sql, showRowMapper);
    }

    public List<Show> findByMovieMovieId(String movieId) {
        String sql = "SELECT * FROM shows WHERE movie_id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql, showRowMapper, movieId);
    }

    public Show save(Show show) {
        String bookedSeatsJson;
        try {
            bookedSeatsJson = MAPPER.writeValueAsString(show.getBookedSeatIds());
        } catch (JsonProcessingException e) {
            bookedSeatsJson = "[]";
        }

        if (show.getCsid() == null) {
            show.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO shows (csid, start_time, end_time, ticket_price, movie_id, theatre_id, seat_booked_map, status, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?::jsonb, ?, ?)";
            jdbcTemplate.update(sql,
                show.getCsid(), show.getStartTime(), show.getEndTime(), show.getTicketPrice(), 
                show.getMovieId(), show.getTheatreId(), bookedSeatsJson, show.getStatus().name(), show.getIsDeleted());
        } else {
            String sql = "UPDATE shows SET start_time = ?, end_time = ?, ticket_price = ?, movie_id = ?, theatre_id = ?, seat_booked_map = ?::jsonb, status = ?, is_deleted = ? WHERE csid = ?";
            jdbcTemplate.update(sql,
                show.getStartTime(), show.getEndTime(), show.getTicketPrice(), 
                show.getMovieId(), show.getTheatreId(), 
                bookedSeatsJson, show.getStatus().name(), show.getIsDeleted(), show.getCsid());
        }
        return show;
    }

    public void deleteById(String csid) {
        String sql = "UPDATE shows SET is_deleted = true WHERE csid = ?";
        jdbcTemplate.update(sql, csid);
    }
}
