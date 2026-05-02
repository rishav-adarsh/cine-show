package com.cinema.cineshow.infrastructure.repository;

import com.cinema.cineshow.infrastructure.entity.Movie;
import com.cinema.cineshow.infrastructure.repository.jdbc.rowmapper.MovieRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MovieRepository {

    private final JdbcTemplate jdbcTemplate;
    private final MovieRowMapper movieRowMapper = new MovieRowMapper();

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Movie> findById(String csid) {
        String sql = "SELECT * FROM movies WHERE csid = ? AND is_deleted = false";
        List<Movie> movies = jdbcTemplate.query(sql, movieRowMapper, csid);
        return movies.stream().findFirst();
    }

    public List<Movie> findAll() {
        String sql = "SELECT * FROM movies WHERE is_deleted = false";
        return jdbcTemplate.query(sql, movieRowMapper);
    }

    public Movie save(Movie movie) {
        if (movie.getCsid() == null) {
            movie.setCsid(UUID.randomUUID().toString());
            String sql = "INSERT INTO movies (csid, name, release_date, duration_minutes, poster_url, rating, description, genre, language, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                movie.getCsid(), movie.getName(), movie.getReleaseDate(), movie.getDuration(),
                movie.getImage(), movie.getRating(), movie.getDescription(), movie.getGenre(), movie.getLanguage(), movie.isDeleted());
        } else {
            String sql = "UPDATE movies SET name = ?, release_date = ?, duration_minutes = ?, poster_url = ?, rating = ?, description = ?, genre = ?, language = ?, is_deleted = ? WHERE csid = ?";
            jdbcTemplate.update(sql,
                movie.getName(), movie.getReleaseDate(), movie.getDuration(),
                movie.getImage(), movie.getRating(), movie.getDescription(), movie.getGenre(), movie.getLanguage(), movie.isDeleted(), movie.getCsid());
        }
        return movie;
    }

    public void deleteById(String csid) {
        String sql = "UPDATE movies SET is_deleted = true WHERE csid = ?";
        jdbcTemplate.update(sql, csid);
    }
}
