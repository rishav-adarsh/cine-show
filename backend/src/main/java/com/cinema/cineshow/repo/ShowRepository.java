package com.cinema.cineshow.repo;

import com.cinema.cineshow.model.movie.Movie;
import com.cinema.cineshow.model.movie.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    public List<Show> findByMovie(Movie movie);
}
