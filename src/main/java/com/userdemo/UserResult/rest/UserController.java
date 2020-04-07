package com.userdemo.UserResult.rest;

import com.userdemo.UserResult.model.User;
import com.userdemo.UserResult.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/setinfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody @Valid User user) {
        List<User> users = this.userServiceImpl.getAllUsers();

        if (user == null) {
            return new ResponseEntity<>("user data is empty",
                    HttpStatus.BAD_REQUEST);
        }

        if (user.equals(this.userServiceImpl.get(user))) {
            return new ResponseEntity<>("user with such result already reported",
                    HttpStatus.ALREADY_REPORTED);
        }

        final List<User> collect = users.parallelStream()
                .filter(us -> us.getUser_id().equals(user.getUser_id()))
                .filter(us -> us.getLevel_id().equals(user.getLevel_id()))
                .sorted(comparing(User::getResult, reverseOrder()))
                .collect(Collectors.toList());

        collect.parallelStream().limit(20)
                .forEach(us -> {
                    if (user.getResult() > us.getResult()) {
                        this.userServiceImpl.save(user);

                    } else {
                        new ResponseEntity<>("invalid user result, set only top results",
                                HttpStatus.EXPECTATION_FAILED);
                    }
                });

        return new ResponseEntity<>("set new result", HttpStatus.OK);
    }

    //TODO: need to find out how delete lowest result user from memory

    @RequestMapping(value = "/userinfo/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllById(@PathVariable("user_id") @Valid Integer id) {
        List<User> users = this.userServiceImpl.getAllById(id);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/levelinfo/{level_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllByLevel(@PathVariable("level_id") @Valid Integer level) {
        List<User> users = this.userServiceImpl.getAllByLevel(level);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
