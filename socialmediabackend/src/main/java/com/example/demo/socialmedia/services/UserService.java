package com.example.demo.socialmedia.services;

import com.example.demo.socialmedia.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(String  id);

    User saveUser(User user);

    List<User> findAllUsers();

    void deleteUser(User user);

    Optional<User> loginUser(String username,String password);
}
