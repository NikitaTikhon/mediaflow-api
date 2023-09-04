package com.mediaflow.service;

import com.mediaflow.dto.response.PhotoResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    PhotoResponse savePhoto(MultipartFile photo, JwtAuthenticationToken authUser);

    List<PhotoResponse> getUserPhotos(String username, Integer page);

    void deletePhoto(String publicId, JwtAuthenticationToken authUser);

}
