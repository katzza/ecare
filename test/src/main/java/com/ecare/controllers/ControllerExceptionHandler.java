package com.ecare.controllers;

import com.ecare.error.BaseObjectDeletionException;
import com.ecare.error.UserAlreadyExistException;
import com.ecare.error.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public String handleExceptionUserExist() {
        return "/error/userexist";
    }

    @ExceptionHandler({UserNotFoundException.class, UserPrincipalNotFoundException.class,
            UsernameNotFoundException.class})
    public String handleException() {
        return "/error/usernotfound";
    }

    @ExceptionHandler({BaseObjectDeletionException.class})
    public String handleBaseObjectException() {
        return "/error/baseobjectexception";
    }
}
