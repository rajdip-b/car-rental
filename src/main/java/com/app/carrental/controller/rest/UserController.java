package com.app.carrental.controller.rest;

import com.app.carrental.entity.User;
import com.app.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final List<String> validSortByFields = List.of("firstName", "lastName", "memberSince");

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findUserById(@PathVariable int userId){
        return userRepository.findById(userId).orElse(null);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> findAllUsers(
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return userRepository.findAllUsers(
                PageRequest.of(offset, 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
        );
    }

    @GetMapping(value = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> findUsersByName(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return userRepository.findUsersByName(
                firstName,
                lastName,
                PageRequest.of(getOffset(offset), 10, Sort.by(getSortOrder(sortOrder), getSortBy(sortBy)))
                );
    }

    @DeleteMapping(value = "/{userId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean deleteUser(@PathVariable int userId){
        try{
            userRepository.deleteById(userId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PostMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addUser(@RequestBody User user){
        try{
            userRepository.save(user);
            return true;
        }catch (Exception e){
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
