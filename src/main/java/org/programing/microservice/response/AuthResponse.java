package org.programing.microservice.response;

import lombok.Data;
import org.programing.microservice.model.USER_ROLE;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
