package com.userdemo.UserResult.repository;

import com.userdemo.UserResult.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    ConcurrentHashMap<User, Integer> certificationCosts = new ConcurrentHashMap<>();
    Set<User> concurrentHashSet = certificationCosts.newKeySet();

    @Override
    public void saveUser(User user) {
        concurrentHashSet.add(user);
    }

    @Override
    public List<User> getAllById(Integer id) {
        return concurrentHashSet.stream().sorted(Comparator.comparing(User::getResult).reversed())
                .filter(user -> user.getUser_id() == id)
                .limit(20)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllByLevel(Integer level) {
        return concurrentHashSet.stream().sorted(Comparator.comparing(User::getResult).reversed())
                .filter(user -> user.getLevel_id() == level)
                .limit(20)
                .collect(Collectors.toList());
    }
}
