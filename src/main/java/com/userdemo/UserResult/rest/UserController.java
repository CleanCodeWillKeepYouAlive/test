package com.userdemo.UserResult.rest;

import com.userdemo.UserResult.model.User;
import com.userdemo.UserResult.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/setinfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody @Valid User user) {

        if (user == null) {
            return new ResponseEntity<>("user data is empty",
                    HttpStatus.BAD_REQUEST);
        }

        List<User> users = this.userServiceImpl.getAllUserData(user);

        if (users.contains(user)) {
            return new ResponseEntity<>("user with such result already reported",
                    HttpStatus.ALREADY_REPORTED);
        }

        if (users.isEmpty()) {
            this.userServiceImpl.save(user);
        }

        users.forEach(us -> {
            if (user.getResult() > us.getResult()) {
                this.userServiceImpl.save(user);
            }
        });

        return new ResponseEntity<>("data processing", HttpStatus.OK);
    }

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
