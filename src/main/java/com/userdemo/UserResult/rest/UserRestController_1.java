package com.userdemo.UserResult.rest;

import com.userdemo.UserResult.model.User;
import com.userdemo.UserResult.service.UserServiceImpl;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserRestController_1 {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/setinfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public synchronized ResponseEntity<User> saveUser(@RequestBody @Valid User user) {

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userServiceImpl.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/userinfo/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public synchronized ResponseEntity<List<User>> getAllById(@PathVariable("user_id") @Valid Integer id) {
        List<User> users = this.userServiceImpl.getAllById(id);

        if (users.isEmpty()) {
            System.out.println("User set is empty");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/levelinfo/{level_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public synchronized ResponseEntity<List<User>> getAllByLevel(@PathVariable("level_id") @Valid Integer level) {
        List<User> users = this.userServiceImpl.getAllByLevel(level);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public
    @ResponseBody
    String typeMismatchExpcetionHandler() {
        return "Parameter type mismatch";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public
    @ResponseBody
    String missingParameterExceptionHandler() {
        return "Missing parameter";
    }

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    String generalExceptionHandler() {
        return "Wrong request data";
    }
}
