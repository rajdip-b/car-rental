package com.app.carrental.controller.rest;

import com.app.carrental.entity.User;
import com.app.carrental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findUserById(@PathVariable int userId){
        return userService.findUserById(userId);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> findAllUsers(
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return userService.findAllUsers(offset, sortOrder, sortBy);
    }

    @GetMapping(value = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> findUsersByName(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("offset") int offset){
        return userService.findUsersByName(firstName, lastName, offset, sortOrder, sortBy);
    }

    @DeleteMapping(value = "/{userId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public boolean deleteUser(@PathVariable int userId){
        return userService.deleteUser(userId);
    }

    @PostMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addUser(@RequestBody User user){
        return userService.addUser(user);
    }

}
