package org.example.vdtvideocall.payload.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

    private String employeeId;
}