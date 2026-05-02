package com.cinema.cineshow.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String csid;
    private String name;
    private LocalDate releaseDate;
    private Integer duration;
    private String image;
    private Double rating;
    private String description;
    private String genre;
    private String language;
    private boolean isDeleted = false;

    public Movie() {}

    public Movie(String csid, String name, String description, String image, Double rating, String genre, Integer duration, String language, LocalDate releaseDate) {
        this.csid = csid;
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.releaseDate = releaseDate;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public static MovieBuilder builder() {
        return new MovieBuilder();
    }

    public static class MovieBuilder {
        private String csid;
        private String name;
        private String description;
        private String image;
        private Double rating;
        private String genre;
        private Integer duration;
        private String language;
        private LocalDate releaseDate;
        private boolean isDeleted = false;

        MovieBuilder() {}

        public MovieBuilder csid(String csid) { this.csid = csid; return this; }
        public MovieBuilder name(String name) { this.name = name; return this; }
        public MovieBuilder description(String description) { this.description = description; return this; }
        public MovieBuilder image(String image) { this.image = image; return this; }
        public MovieBuilder rating(Double rating) { this.rating = rating; return this; }
        public MovieBuilder genre(String genre) { this.genre = genre; return this; }
        public MovieBuilder duration(Integer duration) { this.duration = duration; return this; }
        public MovieBuilder language(String language) { this.language = language; return this; }
        public MovieBuilder releaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; return this; }
        public MovieBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public Movie build() {
            return new Movie(csid, name, description, image, rating, genre, duration, language, releaseDate);
        }
    }
}
