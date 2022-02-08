package com.app.carrental.service.impl;

import com.app.carrental.entity.Car;
import com.app.carrental.entity.Rating;
import com.app.carrental.repository.CarRepository;
import com.app.carrental.repository.RatingRepository;
import com.app.carrental.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CarRepository carRepository;

    @Override
    public Page<Rating> findRatingsByUser(int userId, int offset) {
        Page<Rating> ratings = ratingRepository.findRatingsByUser(userId, PageRequest.of(getOffset(offset), 10, Sort.by(Sort.Direction.DESC, "date")));
        for (Rating r : ratings){
            Car car = carRepository.findById(r.getCarId()).orElse(null);
            r.setCar(car);
        }
        return ratings;
    }

    @Override
    public Page<Rating> findRatingsOnCar(int carId, int offset) {
        Page<Rating> ratings = ratingRepository.findRatingsOnCar(carId, PageRequest.of(getOffset(offset), 10, Sort.by(Sort.Direction.DESC, "date")));
        for (Rating r : ratings){
            Car car = carRepository.findById(r.getCarId()).orElse(null);
            r.setCar(car);
        }
        return ratings;
    }

    @Override
    public boolean deleteRating(int ratingId) {
        try{
            ratingRepository.deleteById(ratingId);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at RatingServiceImpl.deleteRating");
            return false;
        }
    }

    @Override
    public boolean updateRating(Rating rating) {
        Rating r = ratingRepository.findById(rating.getId()).orElse(null);
        try{
            assert r != null;
            r.setRating(rating.getRating());
            r.setComment(rating.getComment());
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at RatingServiceImpl.deleteRating");
            return false;
        }
    }

    @Override
    public boolean addRating(Rating rating) {
        try{
            ratingRepository.save(rating);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at RatingServiceImpl.addRating");
            return false;
        }
    }

    private int getOffset(int offset){
        if (offset < 0)
            offset = 0;
        return offset;
    }
}
