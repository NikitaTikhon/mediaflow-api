package com.mediaflow.controller;

import com.mediaflow.dto.response.PhotoResponse;
import com.mediaflow.service.PhotoService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PhotoController {

    private PhotoService photoService;

    @PostMapping("/photo")
    public ResponseEntity<PhotoResponse> savePhoto(@RequestParam("photo") MultipartFile photo, JwtAuthenticationToken authUser) {
        PhotoResponse photoResponse = photoService.savePhoto(photo, authUser);
        return ResponseEntity.ok(photoResponse);
    }

    @GetMapping("/photo/user/{username}")
    public ResponseEntity<List<PhotoResponse>> getUserPhotos(@PathVariable("username") String username, @RequestParam("page") Integer page) {
        List<PhotoResponse> photoResponses = photoService.getUserPhotos(username, page);
        return ResponseEntity.ok(photoResponses);
    }

    @DeleteMapping("/photo/{publicId}")
    public ResponseEntity<?> deletePhoto(@PathVariable("publicId") String publicId, JwtAuthenticationToken authUser) {
        photoService.deletePhoto(publicId, authUser);
        return ResponseEntity.ok().build();
    }

}
