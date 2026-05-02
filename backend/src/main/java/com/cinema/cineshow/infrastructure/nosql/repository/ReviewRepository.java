package com.cinema.cineshow.infrastructure.nosql.repository;

import com.cinema.cineshow.infrastructure.nosql.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByMovieId(String movieId);
    List<Review> findByUserId(String userId);
}
