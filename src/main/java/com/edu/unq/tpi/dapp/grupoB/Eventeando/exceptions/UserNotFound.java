package com.edu.unq.tpi.dapp.grupoB.Eventeando.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String messageUserNotFound) {
        super(messageUserNotFound);
    }
}
