package com.userdemo.UserResult.service;

import com.userdemo.UserResult.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    List<User> getAllById(Integer id);

    List<User> getAllByLevel(Integer level);
}
