package com.cinema.cineshow.infrastructure.nosql.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
public class Review {
    @Id
    private String csid;
    private String userId;
    private String username;
    private String movieId;
    private Integer rating; // 1-5
    private String comment;
    private LocalDateTime createdAt;

    public Review() {}

    public Review(String csid, String userId, String username, String movieId, Integer rating, String comment, LocalDateTime createdAt) {
        this.csid = csid;
        this.userId = userId;
        this.username = username;
        this.movieId = movieId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
