package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;


import com.edu.unq.tpi.dapp.grupoB.Eventeando.exception.EventeandoNotFound;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer.InvalidCreation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public void badRequestUserCreation(HttpServletResponse response, InvalidCreation exception) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler
    public void resourceNotFound(HttpServletResponse response, EventeandoNotFound exception) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

}
