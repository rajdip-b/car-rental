package com.app.carrental.controller.rest;

import com.app.carrental.entity.Rating;
import com.app.carrental.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping(value = "/{ratingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rating findRatingById(
            @PathVariable("ratingId") int ratingId){
        return ratingRepository.findById(ratingId).orElse(null);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> findRatingsByUser(
            @PathVariable("userId") int userId,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return ratingRepository.findRatingsByUser(
                userId,
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/car/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> findRatingsOnCar(
            @PathVariable("userId") int carId,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return ratingRepository.findRatingsOnCar(
                carId,
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/avg/car/{carId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Double findAvgCarRating(
            @PathVariable("carId") int carId){
        return ratingRepository.findAvgCarRating(carId);
    }

    @DeleteMapping(value = "/{ratingId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean deleteRating(@PathVariable int ratingId){
        try{
            ratingRepository.deleteById(ratingId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PutMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateRating(@RequestBody Rating rating){
        try{
            rating.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
            ratingRepository.updateRating(
                    rating.getId(),
                    rating.getRating(),
                    rating.getComment(),
                    rating.getDate()
            );
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PostMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addRating(@RequestBody Rating rating){
        try{
            rating.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
            ratingRepository.save(rating);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private String getSortBy(String sortBy){
        if (sortBy.equals("rating"))
            return "rating";
        return "date";
    }

    private Sort.Direction getSortOrder(String sortOrder){
        if ("DESC".equals(sortOrder))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

}
