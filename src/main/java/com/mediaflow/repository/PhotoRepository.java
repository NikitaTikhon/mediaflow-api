package com.mediaflow.repository;

import com.mediaflow.entity.Photo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long>, PagingAndSortingRepository<Photo, Long> {

    Long removeByPublicIdAndUserPhotosId(String publicId, String userId);

    List<Photo> findAllByUserPhotosId(String userId, Pageable pageable);

    Optional<Photo> findByPublicId(String publicId);

}
