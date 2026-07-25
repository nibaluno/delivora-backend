package org.example.lr7.response;

import lombok.Data;
import org.example.lr7.model.enums.USER_ROLE;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
