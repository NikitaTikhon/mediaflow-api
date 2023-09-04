package com.mediaflow.repository;

import com.mediaflow.entity.MainPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainPhotoRepository extends JpaRepository<MainPhoto, Long> {

    Optional<MainPhoto> findByUserMainPhotoId(String userId);

    Long deleteByPublicIdAndUserMainPhotoId(String publicId, String userId);

}
