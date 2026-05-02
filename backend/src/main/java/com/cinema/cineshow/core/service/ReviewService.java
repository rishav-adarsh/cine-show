package com.cinema.cineshow.core.service;

import com.cinema.cineshow.infrastructure.nosql.entity.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getReviewsByMovieId(String movieId);
    List<Review> getReviewsByUserId(String userId);
    void deleteReview(String id);
}
