package com.mediaflow.service.impl;

import com.mediaflow.dto.response.FollowerResponse;
import com.mediaflow.dto.response.FollowsInfoResponse;
import com.mediaflow.entity.Follower;
import com.mediaflow.repository.FollowerRepository;
import com.mediaflow.repository.UserRepository;
import com.mediaflow.service.FollowerService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private FollowerRepository followerRepository;

    private UserRepository userRepository;

    @Override
    public FollowerResponse isFollower(String userReceiverId, JwtAuthenticationToken authUser) {
        String userSenderId = authUser.getToken().getSubject();
        boolean isFollower = followerRepository.existsByUserSenderIdAndUserReceiverId(userSenderId, userReceiverId);

        return FollowerResponse.builder()
                .isFollower(isFollower)
                .build();
    }

    @Override
    public FollowsInfoResponse countFollows(String username) {
        return FollowsInfoResponse.builder()
                .following(followerRepository.countByUserSenderUsername(username))
                .followers(followerRepository.countByUserReceiverUsername(username))
                .build();
    }

    @Override
    @Transactional
    public void saveFollower(String userReceiverId, JwtAuthenticationToken authUser) {
        String userSenderId = authUser.getToken().getSubject();
        Follower follower = Follower.builder()
                .userSender(userRepository.getReferenceById(userSenderId))
                .userReceiver(userRepository.getReferenceById(userReceiverId))
                .build();

        followerRepository.save(follower);
    }

    @Override
    @Transactional
    public void deleteFollower(String userReceiverId, JwtAuthenticationToken authUser) {
        String userSenderId = authUser.getToken().getSubject();
        followerRepository.deleteByUserSenderIdAndUserReceiverId(userSenderId, userReceiverId);
    }

}
