package com.app.carrental.repository;

import com.app.carrental.entity.Rating;
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
            nativeQuery = true
    )
    List<Rating> findRatingsByUser(
            @Param("userId") int userId,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM rating as r WHERE r.car_id = :carId",
            nativeQuery = true
    )
    List<Rating> findRatingsOnCar(
            @Param("carId") int carId,
            Pageable pageable
    );

    @Query(
            value = "SELECT AVG(rating) FROM rating WHERE car_id = :carId",
            nativeQuery = true
    )
    Double findAvgCarRating(
            @Param("carId") int carId
    );

    @Modifying
    @Query("UPDATE Rating r SET r.rating = :rating, r.comment = :comment, r.date = :date WHERE r.id = :ratingId")
    void updateRating(
            @Param("ratingId") int ratingId,
            @Param("rating") Double rating,
            @Param("comment") String comment,
            @Param("date") String date
    );

}
