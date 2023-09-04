package com.mediaflow.repository;

import com.mediaflow.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, PagingAndSortingRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("""
            SELECT NEW com.mediaflow.entity.User(f.userReceiver.id, f.userReceiver.username, m_p) FROM user u
            INNER JOIN u.followsSend f
            LEFT JOIN main_photo m_p ON m_p.userMainPhoto = f.userReceiver
            WHERE f.userSender.id = ?1 AND f.userReceiver.username LIKE %?2%
            GROUP BY f.userReceiver.id, f.userReceiver.username, m_p.id, u.username
            ORDER BY f.userReceiver.username
            """)
    List<User> findUserFollows(String userSenderId, String username, Pageable pageable);

    List<User> findAllByUsernameIgnoreCaseContaining(String username, Pageable pageable);

    boolean existsById(String id);

}
