package com.mediaflow.service.impl;

import com.mediaflow.dto.response.PhotoResponse;
import com.mediaflow.entity.MainPhoto;
import com.mediaflow.entity.Photo;
import com.mediaflow.exception.PhotoException;
import com.mediaflow.mapper.MainPhotoMapper;
import com.mediaflow.repository.MainPhotoRepository;
import com.mediaflow.repository.PhotoRepository;
import com.mediaflow.service.MainPhotoService;
import com.mediaflow.service.UserService;
import com.mediaflow.util.CloudinaryInteraction;
import com.mediaflow.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MainPhotoServiceImpl implements MainPhotoService {

    private UserService userService;

    private MainPhotoRepository mainPhotoRepository;

    private PhotoRepository photoRepository;

    private MainPhotoMapper mainPhotoMapper;

    private CloudinaryInteraction cloudinaryInteraction;

    @Override
    @Transactional
    public PhotoResponse savePhoto(MultipartFile mainPhoto, JwtAuthenticationToken authUser) {
        String userId = authUser.getToken().getSubject();
        File file = FileUtil.multipartToFile(mainPhoto);

        Map<String, String> fileInfo = cloudinaryInteraction.savePhoto(file, userId);
        Optional<MainPhoto> mainPhotoOpt = mainPhotoRepository.findByUserMainPhotoId(userId);

        MainPhoto mainPhotoEntity;
        if (mainPhotoOpt.isPresent()) {
            mainPhotoEntity = mainPhotoOpt.get();

            cloudinaryInteraction.deletePhoto(mainPhotoEntity.getPublicId(), userId);
            mainPhotoEntity.setImageURL(fileInfo.get("url"));
            mainPhotoEntity.setPublicId(fileInfo.get("publicId"));
        } else {
            mainPhotoEntity = MainPhoto.builder()
                    .imageURL(fileInfo.get("url"))
                    .publicId(fileInfo.get("publicId"))
                    .userMainPhoto(userService.getRefUser(userId))
                    .build();

            mainPhotoRepository.save(mainPhotoEntity);
        }

        return mainPhotoMapper.mainPhotoToPhotoResponse(mainPhotoEntity);
    }

    @Override
    @Transactional
    public PhotoResponse selectMainPhoto(String publicId, JwtAuthenticationToken authUser) {
        String userId = authUser.getToken().getSubject();

        Optional<MainPhoto> mainPhotoOpt = mainPhotoRepository.findByUserMainPhotoId(userId);


        Photo photo = photoRepository.findByPublicId(publicId)
                .orElseThrow(() -> new PhotoException("Not found"));

        File file = FileUtil.imageUrlToFile(photo.getImageURL());
        Map<String, String> fileInfo = cloudinaryInteraction.savePhoto(file, userId);

        MainPhoto mainPhotoEntity;
        if (mainPhotoOpt.isPresent()) {
            mainPhotoEntity = mainPhotoOpt.get();

            cloudinaryInteraction.deletePhoto(mainPhotoEntity.getPublicId(), userId);

            mainPhotoEntity.setPublicId(fileInfo.get("publicId"));
            mainPhotoEntity.setImageURL(fileInfo.get("url"));

        } else {
            mainPhotoEntity = MainPhoto.builder()
                    .userMainPhoto(userService.getRefUser(userId))
                    .publicId(fileInfo.get("publicId"))
                    .imageURL(fileInfo.get("url"))
                    .build();

            mainPhotoRepository.save(mainPhotoEntity);
        }

        return mainPhotoMapper.mainPhotoToPhotoResponse(mainPhotoEntity);
    }

    @Override
    public PhotoResponse getUserMianPhoto(String username) {
        String userId = userService.getUserIdByUsername(username);

        MainPhoto mainPhoto = mainPhotoRepository.findByUserMainPhotoId(userId).orElse(null);

        return mainPhotoMapper.mainPhotoToPhotoResponse(mainPhoto);
    }

    @Override
    @Transactional
    public void deleteMainPhoto(String publicId, JwtAuthenticationToken authUser) {
        String userId = authUser.getToken().getSubject();

        Long isRemoved = mainPhotoRepository.deleteByPublicIdAndUserMainPhotoId(publicId, userId);
        if (isRemoved == 1) {
            cloudinaryInteraction.deletePhoto(publicId, userId);
        }
    }

    @Autowired
    public void setMissionService(UserService userService) {
        this.userService = userService;
    }

}
