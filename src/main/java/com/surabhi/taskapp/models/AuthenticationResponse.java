package com.surabhi.taskapp.models;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private  String jwt;

    private  String message;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
