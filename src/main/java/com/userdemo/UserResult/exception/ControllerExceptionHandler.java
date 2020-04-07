package com.userdemo.UserResult.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

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
