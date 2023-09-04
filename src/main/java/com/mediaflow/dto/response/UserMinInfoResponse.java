package com.mediaflow.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMinInfoResponse {

    private String id;

    private String username;

    private String imageURL;

}
