package com.userdemo.UserResult.repository;

import com.userdemo.UserResult.model.User;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private Set<User> concurrentHashSet = ConcurrentHashMap.newKeySet();

    @Override
    public void saveUser(User user) {
        concurrentHashSet.add(user);
    }

    @Override
    public User getUser(User u) {
        return concurrentHashSet.stream()
                .filter(user -> user.equals(u))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAllUserData(User user) {
        return concurrentHashSet.parallelStream()
                .filter(us -> us.getUser_id().equals(user.getUser_id()))
                .filter(us -> us.getLevel_id().equals(user.getLevel_id()))
                .sorted(comparing(User::getResult, reverseOrder()))
                .limit(20)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllByLevel(Integer level) {
        return concurrentHashSet
                .parallelStream()
                .sorted(comparing(User::getLevel_id, reverseOrder())

                        .thenComparing(User::getResult, reverseOrder())
                        .thenComparing(User::getUser_id, reverseOrder())
                )
                .filter(user -> user.getLevel_id().equals(level))
                .collect(toList());
    }

    @Override
    public List<User> getAllById(Integer id) {
        return concurrentHashSet
                .parallelStream()
                .sorted(comparing(User::getUser_id, reverseOrder())

                        .thenComparing(User::getResult, reverseOrder())
                        .thenComparing(User::getLevel_id, reverseOrder())
                )
                .filter(user -> user.getUser_id().equals(id))
                .collect(toList());
    }
}
