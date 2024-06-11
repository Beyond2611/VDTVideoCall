package org.example.vdtvideocall.payload.request;

import lombok.Data;

@Data
public class EmployeeLoginRequest {
    private String username;
    private String password;
}
