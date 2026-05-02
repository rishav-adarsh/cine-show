package com.cinema.cineshow.api.controller;

import com.cinema.cineshow.api.dto.ApiResponse;
import com.cinema.cineshow.core.service.ReviewService;
import com.cinema.cineshow.infrastructure.nosql.entity.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> addReview(@RequestBody Review review) {
        Review createdReview = reviewService.addReview(review);
        return ResponseEntity.ok(ApiResponse.success(createdReview, "Review added successfully"));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByMovieId(@PathVariable String movieId) {
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(ApiResponse.success(reviews, "Movie reviews fetched successfully"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByUserId(@PathVariable String userId) {
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(reviews, "User reviews fetched successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Review deleted successfully"));
    }
}
