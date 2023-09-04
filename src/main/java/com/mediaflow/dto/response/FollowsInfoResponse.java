package com.mediaflow.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowsInfoResponse {

    private Long following;

    private Long followers;

}
