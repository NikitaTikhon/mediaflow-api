package com.mediaflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank
    @Email(message = "Email not valid")
    private String email;

    @NotBlank
    @Size(min = 3, max = 25, message = "Username must be from 3 to 25 characters")
    private String username;

    @NotBlank
    @Size(min = 6, max = 30, message = "Password must be from 6 to 25 characters")
    private String password;

}
