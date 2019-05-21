package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions.UserNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer.InvalidCreation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler
    public void badRequestUserCreation(HttpServletResponse response, InvalidCreation exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler
    public void userNotFound(HttpServletResponse response, UserNotFound exception) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}