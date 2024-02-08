package com.cinema.cineshow.repo;

import com.cinema.cineshow.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
