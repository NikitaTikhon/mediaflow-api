package com.mediaflow.controller;

import com.mediaflow.dto.response.FollowerResponse;
import com.mediaflow.dto.response.FollowsInfoResponse;
import com.mediaflow.service.FollowerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class FollowerController {

    private FollowerService followerService;

    @GetMapping("/follower")
    public ResponseEntity<FollowerResponse> isFollower(@RequestParam("userReceiverId") String userReceiverId, JwtAuthenticationToken authUser) {
        FollowerResponse followerResponse = followerService.isFollower(userReceiverId, authUser);
        return ResponseEntity.ok(followerResponse);
    }

    @GetMapping("/follower/{username}/count")
    public ResponseEntity<FollowsInfoResponse> countFollowers(@PathVariable("username") String username) {
        FollowsInfoResponse followsInfoResponse = followerService.countFollows(username);
        return ResponseEntity.ok(followsInfoResponse);
    }

    @PostMapping("/follower")
    public ResponseEntity<?> saveFollower(@RequestParam("userReceiverId") String userReceiverId, JwtAuthenticationToken authUser) {
        followerService.saveFollower(userReceiverId, authUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/follower")
    public ResponseEntity<?> deleteFollower(@RequestParam("userReceiverId") String userReceiverId, JwtAuthenticationToken authUser) {
        followerService.deleteFollower(userReceiverId, authUser);
        return ResponseEntity.ok().build();
    }

}
