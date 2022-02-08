package com.app.carrental.repository;

import com.app.carrental.entity.Car;
import com.app.carrental.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    User validateLogin(String email, String password);

    @Query("SELECT u FROM User u")
    Page<User> findAllUsers(
            Pageable pageable
    );

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:firstName% OR u.lastName LIKE %:lastName%")
    Page<User> findUsersByName(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            Pageable pageable);

    @Query(
            value = "SELECT c.model, c.brand FROM car as c, user as u, granted_rent as g WHERE c.id = g.car_id AND u.id = g.user_id AND u.id = :userId",
            nativeQuery = true
    )
    Page<Car> findAllGrantedRentsToUser(
            @Param("userId") int userId,
            Pageable pageable
    );

    @Query(
            value = "SELECT c.model, c.brand FROM car as c, user as u, pending_rent as g WHERE c.id = g.car_id AND u.id = g.user_id AND u.id = :userId",
            nativeQuery = true
    )
    Page<Car> findAllPendingRentsToUser(
            @Param("userId") int userId,
            Pageable pageable
    );

}
