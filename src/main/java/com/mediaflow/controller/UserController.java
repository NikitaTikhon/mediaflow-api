package com.mediaflow.controller;

import com.mediaflow.dto.response.UserMinInfoResponse;
import com.mediaflow.dto.response.UserResponse;
import com.mediaflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) {
        UserResponse userResponse = userService.getUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserMinInfoResponse>> getUsersByUsername(@RequestParam("username") String username, @RequestParam("page") Integer page) {
        List<UserMinInfoResponse> userMinInfoResponses = userService.getUsersByUsername(username, page);
        return ResponseEntity.ok(userMinInfoResponses);
    }

    @GetMapping("/follows")
    public ResponseEntity<List<UserMinInfoResponse>> findUserFollows(@RequestParam(value = "username", defaultValue = "") String username,
                                                                     @RequestParam("page") Integer page,
                                                                     JwtAuthenticationToken authUser) {
        List<UserMinInfoResponse> userFollows = userService.findUserFollows(username, page, authUser);
        return ResponseEntity.ok(userFollows);
    }

}
