package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLogin {

    @JsonProperty
    private String email;

    @JsonCreator
    public UserLogin(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
