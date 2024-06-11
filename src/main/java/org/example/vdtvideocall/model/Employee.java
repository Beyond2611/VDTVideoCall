package org.example.vdtvideocall.model;
import java.util.*;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Document(collection="Employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private String id;
    @NotBlank
    private String username;
    @NotNull
    private Status status;

    public enum Status {
        ON_CALL,
        AVAILABLE,
        UNAVAILABLE
    }


    // Getters and setters
}