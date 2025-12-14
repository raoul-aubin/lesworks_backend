package com.lesworks.lesworks_backend.auth;

import com.lesworks.lesworks_backend.user.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role; // JOBIST ou CLIENT
}

