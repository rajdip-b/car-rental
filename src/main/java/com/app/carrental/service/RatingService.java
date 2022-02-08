package com.app.carrental.service;

import com.app.carrental.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {

    Page<Rating> findRatingsByUser(int userId, int offset);
    Page<Rating> findRatingsOnCar(int carId, int offset);
    boolean deleteRating(int ratingId);
    boolean updateRating(Rating rating);
    boolean addRating(Rating rating);

}
