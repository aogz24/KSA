package com.polstat.ksa.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotNull @Email @Max(50)
    private String email;
    @NotNull @Max(16)
    private String password;
}

