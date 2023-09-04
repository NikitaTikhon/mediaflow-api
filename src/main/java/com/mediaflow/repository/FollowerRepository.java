package com.mediaflow.repository;

import com.mediaflow.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    Long countByUserSenderUsername(String username);

    Long countByUserReceiverUsername(String username);

    void deleteByUserSenderIdAndUserReceiverId(String userSenderId, String userReceiverId);

    boolean existsByUserSenderIdAndUserReceiverId(String userSenderId, String userReceiverId);

}
