package com.mediaflow.service;

import com.mediaflow.dto.response.UserMinInfoResponse;
import com.mediaflow.dto.response.UserResponse;
import com.mediaflow.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface UserService {

    List<UserMinInfoResponse> getUsersByUsername(String username, Integer page);

    UserResponse getUserByUsername(String username);

    List<UserMinInfoResponse> findUserFollows(String username, Integer page, JwtAuthenticationToken authUser);

    String getUserIdByUsername(String username);

    User getUserByUsernameOrElseThrowException(String username);

    User getRefUser(String id);

}
