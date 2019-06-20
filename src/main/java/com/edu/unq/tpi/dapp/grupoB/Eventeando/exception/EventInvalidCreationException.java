package com.edu.unq.tpi.dapp.grupoB.Eventeando.exception;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.serializer.InvalidCreation;

public class EventInvalidCreationException extends InvalidCreation {
    public EventInvalidCreationException(String message) {
        super(message);
    }
}
