package com.mediaflow.service;

import com.mediaflow.dto.request.FollowerRequest;
import com.mediaflow.dto.response.FollowerResponse;
import com.mediaflow.dto.response.FollowsInfoResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface FollowerService {

    FollowerResponse isFollower(String userReceiverId, JwtAuthenticationToken authUser);

    FollowsInfoResponse countFollows(String username);

    void saveFollower(String userReceiverId, JwtAuthenticationToken authUser);

    void deleteFollower(String userReceiverId, JwtAuthenticationToken authUser);

}
