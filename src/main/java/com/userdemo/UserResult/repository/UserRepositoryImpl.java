package com.userdemo.UserResult.repository;

import com.userdemo.UserResult.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
    public final User getUser(User u) {
       return concurrentHashSet.stream()
               .filter(user -> user.equals(u))
               .findFirst()
               .orElse(null);
    }

    @Override
    public final List<User> getAll() {
        return concurrentHashSet.parallelStream()
                .collect(toList());
    }

    @Override
    public final List<User> getAllByLevel(Integer level) {
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
    public final List<User> getAllById(Integer id) {
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
