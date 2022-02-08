package com.app.carrental.service;

import com.app.carrental.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findUserById(int userId);
    Page<User> findAllUsers(int offset, String sortOrder, String sortBy);
    Page<User> findUsersByName(String firstName, String lastName, int offset, String sortOrder, String sortBy);
    boolean deleteUser(int userId);
    boolean addUser(User user);

}
