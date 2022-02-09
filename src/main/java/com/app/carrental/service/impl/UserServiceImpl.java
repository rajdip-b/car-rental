package com.app.carrental.service.impl;

import com.app.carrental.entity.User;
import com.app.carrental.repository.BookingRepository;
import com.app.carrental.repository.RatingRepository;
import com.app.carrental.repository.UserRepository;
import com.app.carrental.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private static final List<String> validSortByFields;

    static {
        validSortByFields = List.of("firstName", "lastName", "memberSince");
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public User findUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User validateLogin(String email, String password) {
        password = DigestUtils.sha256Hex(password);
        return userRepository.validateLogin(email, password).orElse(null);
    }

    @Override
    public Page<User> findAllUsers(int offset, String sortOrder, String sortBy) {
        return userRepository.findAll(PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy))));
    }

    @Override
    public Page<User> findUsersByName(String firstName, String lastName, int offset, String sortOrder, String sortBy) {
        return userRepository.findUsersByName(
                firstName,
                lastName,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @Override
    public boolean deleteUser(int userId) {
        try{
            userRepository.deleteById(userId);
            ratingRepository.deleteRatingForUser(userId);
            bookingRepository.deleteBookingForUser(userId);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at UserServiceImpl.deleteUser");
            return false;
        }
    }

    @Override
    public boolean addUser(User user) {
        try{
            userRepository.save(user);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage()+" at UserServiceImpl.deleteUser");
            return false;
        }
    }

    private String getSortBy(String sortBy){
        if (!validSortByFields.contains(sortBy))
            sortBy = "firstName";
        return sortBy;
    }

    private Sort.Direction getSortOrder(String sortOrder){
        if ("DESC".equals(sortOrder))
            return Sort.Direction.DESC;
        return Sort.Direction.ASC;
    }

    private int getOffset(int offset){
        if (offset < 0)
            offset = 0;
        return offset;
    }

}
