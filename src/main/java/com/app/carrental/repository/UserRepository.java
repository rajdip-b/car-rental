package com.app.carrental.repository;

import com.app.carrental.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:firstName% OR u.lastName LIKE %:lastName%")
    Page<User> findUsersByName(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    Optional<User> validateLogin(
            @Param("email") String email,
            @Param("password") String password);

}