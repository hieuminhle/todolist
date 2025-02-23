package com.application.todolist.service;


import com.application.todolist.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> getUserById(int userId);

    public User getUserByEmail(String email);

    public User getUserByEmailAndPassword(String email, String password);

    public User createUser(User user);

    public void deleteUser(int userId);

    public User updateUser(User user);

    public List<User> getOrdinaryUser();
}
