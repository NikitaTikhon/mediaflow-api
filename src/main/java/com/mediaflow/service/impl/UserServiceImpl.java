package com.mediaflow.service.impl;

import com.mediaflow.config.constant.UserConstant;
import com.mediaflow.dto.response.UserMinInfoResponse;
import com.mediaflow.dto.response.UserResponse;
import com.mediaflow.entity.User;
import com.mediaflow.mapper.UserMapper;
import com.mediaflow.repository.MainPhotoRepository;
import com.mediaflow.repository.UserRepository;
import com.mediaflow.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Keycloak keycloak;

    private UserRepository userRepository;

    private UserMapper userMapper;

    private MainPhotoRepository mainPhotoRepository;

    @Value("${KC_REALM}")
    private String realm;

    public UserServiceImpl(Keycloak keycloak, UserRepository userRepository, UserMapper userMapper, MainPhotoRepository mainPhotoRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mainPhotoRepository = mainPhotoRepository;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = getUserByUsernameOrElseThrowException(username);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public List<UserMinInfoResponse> getUsersByUsername(String username, Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        List<User> users = userRepository.findAllByUsernameIgnoreCaseContaining(username, pageable);

        return userMapper.usersToUserMinInfoResponses(users);
    }

    @Override
    public List<UserMinInfoResponse> findUserFollows(String username, Integer page, JwtAuthenticationToken authUser) {
        String userSenderId = authUser.getToken().getSubject();
        Pageable pageable = PageRequest.of(page, 20);
        List<User> userFollows = userRepository.findUserFollows(userSenderId, username, pageable);

        return userMapper.usersToUserMinInfoResponses(userFollows);
    }

    @Override
    public String getUserIdByUsername(String username) {
        User user = getUserByUsernameOrElseThrowException(username);
        return user.getId();
    }

    @Override
    public User getUserByUsernameOrElseThrowException(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserConstant.USER_NOT_FOUND));
    }

    @Override
    public User getRefUser(String id) {
        return userRepository.getReferenceById(id);
    }

}
