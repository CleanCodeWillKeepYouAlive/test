package com.userdemo.UserResult.repository;

import com.userdemo.UserResult.model.User;

import java.util.List;

public interface UserRepository {

    User getUser(User user);

    void saveUser(User user);

    List<User> getAllById(Integer id);

    List<User> getAllByLevel(Integer level);

    List<User> getAll();
}
