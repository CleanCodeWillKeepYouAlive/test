package com.userdemo.UserResult.service;

import com.userdemo.UserResult.model.User;
import com.userdemo.UserResult.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = {"cache"})
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Override
    @CacheEvict(value="cache", allEntries=true)
    public synchronized void save(User user) {
        log.info("saveUser into storage {}", user);
        userRepositoryImpl.saveUser(user);
    }

    @Override
    @Cacheable(sync = true)
    public synchronized List<User> getAllByLevel(Integer level) {
        log.info("getAllByLevel from cache");
        return userRepositoryImpl.getAllByLevel(level);
    }

    @Override
    @Cacheable(sync = true)
    public synchronized List<User> getAllById(Integer id) {
        log.info("getAllById form cache");
        return userRepositoryImpl.getAllById(id);
    }
}
