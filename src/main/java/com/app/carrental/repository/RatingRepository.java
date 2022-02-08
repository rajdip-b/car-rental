package com.app.carrental.repository;

import com.app.carrental.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query(
            value = "SELECT * FROM rating as r WHERE r.user_id = :userId",
            countQuery = "SELECT COUNT(id) FROM rating as r WHERE r.user_id = :userId",
            nativeQuery = true
    )
    Page<Rating> findRatingsByUser(
            @Param("userId") int userId,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM rating as r WHERE r.car_id = :carId",
            countQuery = "SELECT COUNT(id) FROM rating as r WHERE r.car_id = :carId",
            nativeQuery = true
    )
    Page<Rating> findRatingsOnCar(
            @Param("carId") int carId,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE Rating r SET r.rating = :rating, r.comment = :comment, r.date = :date WHERE r.id = :ratingId")
    void updateRating(
            @Param("ratingId") int ratingId,
            @Param("rating") Double rating,
            @Param("comment") String comment,
            @Param("date") String date
    );

    @Modifying
    @Query(value = "DELETE FROM rating WHERE rating.user_id = :userId", nativeQuery = true)
    void deleteRatingForUser(@Param("userId") int userId);


    @Modifying
    @Query(value = "DELETE FROM rating WHERE rating.car_id = :carId", nativeQuery = true)
    void deleteRatingForCar(@Param("carId") int carId);

}
