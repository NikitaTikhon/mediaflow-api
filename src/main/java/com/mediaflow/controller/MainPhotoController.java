package com.mediaflow.controller;

import com.mediaflow.dto.response.PhotoResponse;
import com.mediaflow.service.MainPhotoService;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MainPhotoController {

    private MainPhotoService mainPhotoService;

    @PostMapping("/main/photo")
    public ResponseEntity<PhotoResponse> saveMainPhoto(@RequestParam("photo") MultipartFile photo, JwtAuthenticationToken authUser) {
        PhotoResponse photoResponse = mainPhotoService.savePhoto(photo, authUser);
        return ResponseEntity.ok(photoResponse);
    }

    @PostMapping("/main/photo/{publicId}")
    public ResponseEntity<PhotoResponse> selectMainPhoto(@PathVariable("publicId") String publicId, JwtAuthenticationToken authUser) {
        PhotoResponse photoResponse = mainPhotoService.selectMainPhoto(publicId, authUser);
        return ResponseEntity.ok(photoResponse);
    }

    @GetMapping("/main/photo/user/{username}")
    public ResponseEntity<PhotoResponse> getUserMainPhoto(@PathVariable("username") String username) {
        PhotoResponse photoResponse = mainPhotoService.getUserMianPhoto(username);
        return ResponseEntity.ok(photoResponse);
    }

    @DeleteMapping("/main/photo/{publicId}")
    public ResponseEntity<?> deleteUserMainPhoto(@PathVariable("publicId") String publicId, JwtAuthenticationToken authUser) {
        mainPhotoService.deleteMainPhoto(publicId, authUser);
        return ResponseEntity.ok().build();
    }

}
