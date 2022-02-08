package com.app.carrental.repository;

import com.app.carrental.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Override
    @Query(value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) as bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.id = :carId", nativeQuery = true)
    Optional<Car> findById(@Param("carId") Integer carId);

    @Override
    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) as bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car",
            countQuery = "SELECT COUNT(id) FROM car WHERE car.id",
            nativeQuery = true
    )
    Page<Car> findAll(Pageable pageable);

    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) AS bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.brand LIKE %:brand%",
            countQuery = "SELECT COUNT(id) FROM car WHERE car.id WHERE car.brand = :brand",
            nativeQuery = true)
    Page<Car> findCarsWithBrand(
            @Param("brand") String brand,
            Pageable pageable
    );

    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) AS bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.model LIKE %:model% AND car.brand LIKE %:brand%",
            countQuery = "SELECT COUNT(id) FROM car WHERE car.id WHERE car.model LIKE %:model% AND car.brand LIKE %:brand%",
            nativeQuery = true)
    Page<Car> findCarsWithModelAndBrand(
            @Param("model") String model,
            @Param("brand") String brand,
            Pageable pageable
    );

    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) AS bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.price >= :minPrice AND car.price <= :maxPrice",
            countQuery = "SELECT COUNT(id) FROM car WHERE car.id WHERE car.price >= :minPrice AND car.price <= :maxPrice",
            nativeQuery = true)
    Page<Car> findCarsWithinPriceRange(
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            Pageable pageable
    );

    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) AS bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.price >= :price",
            countQuery = "SELECT COUNT(id) FROM car WHERE car.price >= :price",
            nativeQuery = true)
    Page<Car> findCarsAbovePriceRange(
            @Param("price") int price,
            Pageable pageable
    );

    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) AS bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.price <= :price",
            countQuery = "SELECT COUNT(id) FROM car WHERE car.price <= :price",
            nativeQuery = true)
    Page<Car> findCarsBelowPriceRange(
            @Param("price") int price,
            Pageable pageable
    );

    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) AS bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.brand LIKE %:brand%",
            nativeQuery = true)
    List<String> getAllBrandNames(
            @Param("brand") String brand
    );

    @Query(
            value = "SELECT *, (SELECT COUNT(*) FROM booking WHERE booking.car_id=car.id) AS bookings, (SELECT AVG(rating) FROM rating WHERE rating.car_id=car.id) AS rating FROM car WHERE car.brand LIKE %:brand% and car.model LIKE %:model%",
            nativeQuery = true)
    List<String> getAllModelNamesUnderBrand(
            @Param("brand") String brand,
            @Param("model") String model
    );

}
