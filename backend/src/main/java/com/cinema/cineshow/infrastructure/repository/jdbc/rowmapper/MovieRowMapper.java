package com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper;

import com.cinema.cineshow.infrastructure.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setCsid(rs.getString("csid"));
        movie.setName(rs.getString("name"));
        movie.setReleaseDate(rs.getObject("release_date", LocalDate.class));
        movie.setDuration(rs.getInt("duration_minutes"));
        movie.setImage(rs.getString("poster_url"));
        movie.setRating(rs.getDouble("rating"));
        movie.setDescription(rs.getString("description"));
        movie.setGenre(rs.getString("genre"));
        movie.setLanguage(rs.getString("language"));
        movie.setDeleted(rs.getBoolean("is_deleted"));
        return movie;
    }
}
