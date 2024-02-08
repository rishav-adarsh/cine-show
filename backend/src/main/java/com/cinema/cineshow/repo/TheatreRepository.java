package com.cinema.cineshow.repo;

import com.cinema.cineshow.model.movie.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
}
