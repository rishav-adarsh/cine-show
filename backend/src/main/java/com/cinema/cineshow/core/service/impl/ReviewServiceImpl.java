package com.cinema.cineshow.core.service.impl;

import com.cinema.cineshow.core.service.ReviewService;
import com.cinema.cineshow.infrastructure.nosql.entity.Review;
import com.cinema.cineshow.infrastructure.nosql.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    // private final ReviewRepository reviewRepository;

    public ReviewServiceImpl() {
        // No MongoDB dependency for now
    }

    @Override
    public Review addReview(Review review) {
        log.info("MongoDB connection coming soon - Adding review for movie {} by user {}", review.getMovieId(), review.getUserId());
        review.setCreatedAt(java.time.LocalDateTime.now());
        review.setCsid("mock-review-" + System.currentTimeMillis());
        
        // Original MongoDB logic (commented out for now):
        // review.setCreatedAt(LocalDateTime.now());
        // return reviewRepository.save(review);
        
        return review;
    }

    @Override
    public List<Review> getReviewsByMovieId(String movieId) {
        log.info("MongoDB connection coming soon - Getting reviews for movie: {}", movieId);
        
        // Original MongoDB logic (commented out for now):
        // return reviewRepository.findByMovieId(movieId);
        
        return java.util.Collections.emptyList();
    }

    @Override
    public List<Review> getReviewsByUserId(String userId) {
        log.info("MongoDB connection coming soon - Getting reviews for user: {}", userId);
        
        // Original MongoDB logic (commented out for now):
        // return reviewRepository.findByUserId(userId);
        
        return java.util.Collections.emptyList();
    }

    @Override
    public void deleteReview(String id) {
        log.info("MongoDB connection coming soon - Deleting review: {}", id);
        
        // Original MongoDB logic (commented out for now):
        // reviewRepository.deleteById(id);
        
        // No action taken until MongoDB is connected
    }
}
