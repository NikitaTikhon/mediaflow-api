package com.mediaflow.service.impl;

import com.mediaflow.dto.response.PhotoResponse;
import com.mediaflow.entity.Photo;
import com.mediaflow.mapper.PhotoMapper;
import com.mediaflow.repository.PhotoRepository;
import com.mediaflow.service.PhotoService;
import com.mediaflow.service.UserService;
import com.mediaflow.util.CloudinaryInteraction;
import com.mediaflow.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private UserService userService;

    private PhotoRepository photoRepository;

    private PhotoMapper photoMapper;

    private CloudinaryInteraction cloudinaryInteraction;

    @Override
    @Transactional
    public PhotoResponse savePhoto(MultipartFile photo, JwtAuthenticationToken authUser) {
        String userId = authUser.getToken().getSubject();
        File file = FileUtil.multipartToFile(photo);

        Map<String, String> fileInfo = cloudinaryInteraction.savePhoto(file, userId);

        Photo photoEntity = Photo.builder()
                .imageURL(fileInfo.get("url"))
                .publicId(fileInfo.get("publicId"))
                .userPhotos(userService.getRefUser(userId))
                .build();

        photoRepository.save(photoEntity);

        return photoMapper.photoToPhotoResponse(photoEntity);
    }

    @Override
    public List<PhotoResponse> getUserPhotos(String username, Integer page) {
        String userId = userService.getUserIdByUsername(username);
        Pageable pageable = PageRequest.of(page, 8, Sort.by("creationDate").descending());

        List<Photo> photos = photoRepository.findAllByUserPhotosId(userId, pageable);

        return photoMapper.photosToPhotoResponses(photos);
    }

    @Override
    @Transactional
    public void deletePhoto(String publicId, JwtAuthenticationToken authUser) {
        String userId = authUser.getToken().getSubject();

        Long isRemoved = photoRepository.removeByPublicIdAndUserPhotosId(publicId, userId);
        if (isRemoved == 1) {
            cloudinaryInteraction.deletePhoto(publicId, userId);
        }
    }

    @Autowired
    public void setMissionService(UserService userService) {
        this.userService = userService;
    }

}
