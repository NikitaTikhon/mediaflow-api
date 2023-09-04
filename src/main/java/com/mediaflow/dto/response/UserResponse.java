package com.mediaflow.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@Builder
public class UserResponse {

    private String id;

    private String email;

    private String username;

    private OffsetDateTime creationDate;

    @JsonInclude(Include.NON_NULL)
    private String firstName;

    @JsonInclude(Include.NON_NULL)
    private String lastName;

}
