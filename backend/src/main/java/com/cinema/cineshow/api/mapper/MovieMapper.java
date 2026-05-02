package com.cinema.cineshow.api.mapper;

import com.cinema.cineshow.api.dto.MovieDtos;
import com.cinema.cineshow.infrastructure.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie toEntity(MovieDtos.MovieUpsertRequest request) {
        Movie movie = new Movie();
        movie.setName(request.getMovieName());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setDuration(request.getDuration());
        movie.setImage(request.getPoster());
        movie.setRating(request.getRating());
        movie.setDescription(request.getDescription());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        // csid is ignored
        return movie;
    }

    public Movie toEntity(MovieDtos.MovieResponse response) {
        Movie movie = new Movie();
        movie.setCsid(response.getMovieId());
        movie.setName(response.getMovieName());
        movie.setReleaseDate(response.getReleaseDate());
        movie.setDuration(response.getDuration());
        movie.setImage(response.getPoster());
        movie.setRating(response.getRating());
        movie.setDescription(response.getDescription());
        movie.setGenre(response.getGenre());
        movie.setLanguage(response.getLanguage());
        return movie;
    }

    public MovieDtos.MovieResponse toResponse(Movie movie) {
        MovieDtos.MovieResponse response = new MovieDtos.MovieResponse();
        response.setMovieId(movie.getCsid());
        response.setMovieName(movie.getName());
        response.setReleaseDate(movie.getReleaseDate());
        response.setDuration(movie.getDuration());
        response.setPoster(movie.getImage());
        response.setRating(movie.getRating());
        response.setDescription(movie.getDescription());
        response.setGenre(movie.getGenre());
        response.setLanguage(movie.getLanguage());
        return response;
    }
}
