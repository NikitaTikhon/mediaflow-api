package com.mediaflow.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSettingRequest {

    private Long id;

    @NotBlank
    @Size(min = 6, max = 30, message = "Password must be from 6 to 25 characters")
    @JsonProperty("new_password")
    private String newPassword;

}
