package com.mediaflow.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class PhotoResponse {

    private Long id;

    private String publicId;

    private String imageURL;

    private OffsetDateTime creationDate;

    private String userId;

}
