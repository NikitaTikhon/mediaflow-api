package com.mediaflow.service;

import com.mediaflow.dto.response.PhotoResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

public interface MainPhotoService {

    PhotoResponse savePhoto(MultipartFile photo, JwtAuthenticationToken authUser);

    PhotoResponse selectMainPhoto(String publicId, JwtAuthenticationToken authUser);
    
    PhotoResponse getUserMianPhoto(String username);

    void deleteMainPhoto(String publicId, JwtAuthenticationToken authUser);

}
