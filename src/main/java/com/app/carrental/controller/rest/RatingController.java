package com.app.carrental.controller.rest;

import com.app.carrental.entity.Rating;
import com.app.carrental.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Rating> findRatingsByUser(
            @PathVariable("userId") int userId,
            @RequestParam("offset") int offset){
        return ratingService.findRatingsByUser(userId, offset);
    }

    @GetMapping(value = "/car/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Rating> findRatingsOnCar(
            @PathVariable("carId") int carId,
            @RequestParam("offset") int offset){
        return ratingService.findRatingsOnCar(carId, offset);
    }

    @DeleteMapping(value = "/{ratingId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean deleteRating(@PathVariable int ratingId){
        return ratingService.deleteRating(ratingId);
    }

    @PutMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateRating(@RequestBody Rating rating){
        return ratingService.updateRating(rating);
    }

    @PostMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addRating(@RequestBody Rating rating){
        return ratingService.addRating(rating);
    }

}
