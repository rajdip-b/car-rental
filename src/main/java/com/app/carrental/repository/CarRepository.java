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

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT c FROM Car c WHERE c.brand LIKE %:brand%")
    Page<Car> findCarsWithBrand(
            @Param("brand") String brand,
            Pageable pageable
    );

    @Query("SELECT c FROM Car c WHERE c.model LIKE %:model% AND c.brand LIKE %:brand%")
    Page<Car> findCarsWithModelAndBrand(
            @Param("model") String model,
            @Param("brand") String brand,
            Pageable pageable
    );

    @Query("SELECT c FROM Car c WHERE c.price >= :minPrice AND c.price <= :maxPrice")
    Page<Car> findCarsWithinPriceRange(
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            Pageable pageable
    );

    @Query("SELECT c FROM Car c WHERE c.price >= :price")
    Page<Car> findCarsAbovePriceRange(
            @Param("price") int price,
            Pageable pageable
    );

    @Query("SELECT c FROM Car c WHERE c.price <= :price")
    Page<Car> findCarsBelowPriceRange(
            @Param("price") int price,
            Pageable pageable
    );

    @Query(
            value = "SELECT DISTINCT brand FROM car as c WHERE c.brand LIKE %:brand%",
            nativeQuery = true
    )
    List<String> getAllBrandNames(
            @Param("brand") String brand
    );

    @Query(
            value = "SELECT model FROM car as c WHERE c.brand LIKE %:brand% and c.model LIKE %:model%",
            nativeQuery = true
    )
    List<String> getAllModelNamesUnderBrand(
            @Param("brand") String brand,
            @Param("model") String model
    );

    @Modifying
    @Query("UPDATE Car c SET c.model = :model, c.price = :price, c.brand = :brand WHERE c.id = :carId")
    void updateCar(
            @Param("carId") int carId,
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("price") int price
    );

}
