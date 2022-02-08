package com.app.carrental.repository;

import com.app.carrental.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "SELECT * FROM booking as b WHERE b.user_id = :userId", nativeQuery = true)
    Page<Booking> findBookingsByUser(
            @Param("userId") int userId,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM booking as b WHERE b.car_id = :carId", nativeQuery = true)
    Page<Booking> findBookingsByCar(
            @Param("carId") int carId,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM booking as b WHERE b.status_id = :statusId", nativeQuery = true)
    Page<Booking> findBookingsByStatus(
            @Param("statusId") int statusId,
            Pageable pageable
    );

}
