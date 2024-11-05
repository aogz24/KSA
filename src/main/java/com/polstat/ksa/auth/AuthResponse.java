package com.polstat.ksa.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private String accessToken;
}

